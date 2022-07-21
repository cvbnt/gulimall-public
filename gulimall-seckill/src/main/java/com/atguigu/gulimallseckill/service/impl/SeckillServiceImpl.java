package com.atguigu.gulimallseckill.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.to.mq.SeckillOrderTo;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimallseckill.feign.CouponFeignService;
import com.atguigu.gulimallseckill.feign.ProductFeignService;
import com.atguigu.gulimallseckill.interceptor.LoginUserInterceptor;
import com.atguigu.gulimallseckill.service.SeckillService;
import com.atguigu.gulimallseckill.to.SeckillSkuRedisTo;
import com.atguigu.gulimallseckill.vo.SeckillSessionsWithSkus;
import com.atguigu.gulimallseckill.vo.SkuInfoVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedissonClient redissonClient;

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus";
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";//+商品随机码

    @Override
    public void uploadSeckillSkuLatest3Days() {
//        1、扫描最近三天需要参与秒杀的活动
        R session = couponFeignService.getLatest3DaySession();
        if (session.getCode() == 0) {
//            上架商品
            List<SeckillSessionsWithSkus> sessionData = session.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
//            缓存到redis
//            1、缓存活动信息
            saveSessionInfos(sessionData);
//            2、缓存活动的关联商品信息
            saveSessionSkuInfos(sessionData);
        }
    }

    public List<SeckillSkuRedisTo> blockHandler(BlockException e) {
        log.error("getCurrentSeckillSkusResource被限流了..");
        return null;
    }

    /**
     * blockHandler 会在原方法被限流/降级/系统保护的时候调用，而fallback函数会针对所有类型的异常。
     * @return 返回当前时间可以参与的秒杀商品信息
     */
    @SentinelResource(value = "getCurrentSeckillSkusResource", blockHandler = "blockHandler")
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
//        1、确定当前时间属于哪个秒杀场次。
//        1970 -
        long time = new Date().getTime();
        try (Entry entry = SphU.entry("seckillSkus")) {
            Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
            for (String key : keys) {
                String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
                String[] s = replace.split("_");
                Long start = Long.parseLong(s[0]);
                Long end = Long.parseLong(s[1]);
                if (time >= start && time <= end) {
//                获取这个秒杀场次需要的所有商品信息
                    List<String> range = redisTemplate.opsForList().range(key, 0, -1);
                    BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                    List<String> list = hashOps.multiGet(range);
                    if (list != null) {
                        List<SeckillSkuRedisTo> collect = list.stream().map(
                                item -> {
                                    SeckillSkuRedisTo redis = JSON.parseObject((String) item, SeckillSkuRedisTo.class);
//                                redis.setRandomCode(null);当前秒杀开始就需要随机码
                                    return redis;
                                }
                        ).collect(Collectors.toList());
                        return collect;
                    }
                    break;
                }
            }
        } catch (BlockException e) {
            log.error("资源被限流,{}", e.getMessage());
        }

        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {
//        1、找到所有需要参与秒杀的商品的key
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                if (Pattern.matches(regx, key)) {
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo skuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);
//                    随机码
                    long current = new Date().getTime();
                    if (current >= skuRedisTo.getStartTime() && current <= skuRedisTo.getEndTime()) {

                    } else {
                        skuRedisTo.setRandomCode(null);
                    }
                    return skuRedisTo;
                }
            }
        }
        return null;
    }

    //    TODO 上架秒杀商品的时候，每一个数据都有过期时间。
//    TODO 秒杀后续的流程，简化了收货地址等信息。
    @Override
    public String kill(String killId, String key, Integer num) {
        long s1 = System.currentTimeMillis();
        MemberRespVo respVo = LoginUserInterceptor.loginUser.get();
//        1、获取当前秒杀商品的详细信息
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        String json = hashOps.get(killId);
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            SeckillSkuRedisTo redis = JSON.parseObject(json, SeckillSkuRedisTo.class);
//            校验合法性
            Long startTime = redis.getStartTime();
            Long endTime = redis.getEndTime();
            long time = new Date().getTime();
            long ttl = endTime - time;
//            1、校验时间合法性
            if (time >= startTime && time <= endTime) {
//                2、校验随机码和商品id
                String randomCode = redis.getRandomCode();
                String skuId = redis.getPromotionSessionId() + "_" + redis.getSkuId();
                if (randomCode.equals(key) && killId.equals(skuId)) {
//                    3、验证购物数量是否合理
                    if (num <= redis.getSeckillLimit()) {
//                        4、验证这个人是否已经购买过。幂等性；如果只要秒杀成功，就去占位。 userId_SessionId_skuId
//                        SETNX
                        String redisKey = respVo.getId() + "_" + skuId;
//                        自动过期
                        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                        if (aBoolean) {
//                            占位成功说明从来没买过
                            RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                            boolean b = semaphore.tryAcquire(num);
                            if (b) {
//                                秒杀成功;
//                                快速下单。发送MQ消息 10ms
                                String timeId = IdWorker.getTimeId();
                                SeckillOrderTo orderTo = new SeckillOrderTo();
                                orderTo.setOrderSn(timeId);
                                orderTo.setMemberId(respVo.getId());
                                orderTo.setNum(num);
                                orderTo.setPromotionSessionId(redis.getPromotionSessionId());
                                orderTo.setSkuId(redis.getSkuId());
                                orderTo.setSeckillPrice(redis.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange",
                                        "order.seckill.order",
                                        orderTo);
                                long s2 = System.currentTimeMillis();
                                log.info("耗时...{}", (s2 - s1));
                                return timeId;
                            }
                            return null;
                        } else {
//                            说明已经买过了
                            return null;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions) {
        if (sessions != null) {
            sessions.stream().forEach(session -> {
                Long startTime = session.getStartTime().getTime();
                Long endTime = session.getEndTime().getTime();
                String key = SESSIONS_CACHE_PREFIX + startTime + "_" + endTime;
                Boolean hasKey = redisTemplate.hasKey(key);
                if (!hasKey) {
                    List<String> collect = session.getRelationSkus().stream().map(item -> item.getPromotionSessionId().toString() + "_" + item.getSkuId().toString()).collect(Collectors.toList());
//            缓存活动信息
                    redisTemplate.opsForList().leftPushAll(key, collect);
                }
            });
        }
    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions) {
        if (sessions != null) {
            sessions.stream().forEach(session -> {
//            准备hash操作
                BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                    String token = UUID.randomUUID().toString().replace("-", "");
                    if (!ops.hasKey(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString())) {
//                缓存商品
                        SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
//                1、sku的基本数据
                        R skuInfo = productFeignService.getSkuInfo(seckillSkuVo.getSkuId());
                        if (skuInfo.getCode() == 0) {
                            SkuInfoVo info = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                            });
                            redisTo.setSkuInfo(info);
                        }
//                2、sku的秒杀信息
                        BeanUtils.copyProperties(seckillSkuVo, redisTo);
//                3、设置上当前商品的秒杀时间信息
                        redisTo.setStartTime(session.getStartTime().getTime());
                        redisTo.setEndTime(session.getEndTime().getTime());
//                4、随机码？ seckill?skuId=1&key=dadlajldj;

                        redisTo.setRandomCode(token);

                        String jsonString = JSON.toJSONString(redisTo);
                        ops.put(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString(), jsonString);
//                    如果当前这个场次的商品的库存信息已经上架就不需要上架

//                5、使用库存作为分布式的信号量
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
//                商品可以秒杀的数量作为信号量  限流;
                        semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                    }
                });
            });
        }
    }

}
