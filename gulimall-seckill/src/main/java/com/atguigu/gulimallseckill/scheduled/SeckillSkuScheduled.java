package com.atguigu.gulimallseckill.scheduled;

import com.atguigu.gulimallseckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 秒杀商品的定时上架
 *      每天晚上3点；上架最近三天需要秒杀的商品。
 *      当天00：00：00 - 23：59：59
 *      明天00：00：00 - 23：59：59
 *      后天00：00：00 - 23：59：59
 */
@Slf4j
@Service
public class SeckillSkuScheduled {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    SeckillService seckillService;
    private final String upload_lock = "seckill:upload:lock";

    //    TODO 幂等性处理
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateSeckillSkuLatest3Days() {
//        1、重复上架无需处理
        log.info("上架秒杀商品信息");
//        分布式锁。锁的业务执行完成，状态已经更新完成。释放锁以后。其他人获取到就会拿到最新的状态。
        RLock lock = redissonClient.getLock(upload_lock);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.uploadSeckillSkuLatest3Days();
        } finally {
            lock.unlock();
        }
    }
}
