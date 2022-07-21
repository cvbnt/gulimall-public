package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    RedissonClient redisson;
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
        //        利用视图解析器进行拼串
//        classpath:/templates/返回值.html
        return "index";
    }

    //      /index/catalog.json
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
//        1、获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redisson.getLock("my-lock");
//        2、加锁
        lock.lock(10, TimeUnit.SECONDS); //10秒后自动解锁，自动解锁时间一定要大于业务执行时间
//        问题：在锁时间到了以后，不会自动续期
//        1)、如果我们传递了锁的超时时间，就发送给redis执行脚本，进行占锁，默认超时就是我们指定的时间
//        2)、如果我们未指定锁的超时时间，就使用30*1000【LockWatchdogTimeout看门狗的默认时间】
//        只要占锁成功，就会启动一个定时任务【重新给锁设置过期时间,新的过期时间就是看门狗的默认时间】,每隔10s都会再次续期,续成满30S
//        internalLockLeaseTime[看门狗时间]/3,10s
//        最佳实践
//        1)、lock.lock(10,TimeUnit.SECONDS); 省掉了续期操作，手动解锁，10S可替换为大时间

        try {
            System.out.println("加锁成功,执行业务...."+Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (Exception e) {

        } finally {
//            解锁
            System.out.println("释放锁....."+Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }
//    保证一定能读到最新数据，修改期间，写锁是一个排他锁（互斥锁），读锁是一个共享锁
//    写锁没释放，读就必须等待
    @GetMapping("/write")
    @ResponseBody
    public String writeValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s="";
        RLock rLock = lock.writeLock();
        try {
//            1、改数据加写锁，读数据加读锁
            rLock.lock();
            s = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("writeValue",s);
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }
    @GetMapping("/read")
    @ResponseBody
    public String readValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s="";
//        加读锁
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            s = redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    /**
     * 车库停车，
     * 3个车位
     * 信号量也可以用作分布式限流，
     * @return
     */
    @GetMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
//        获取一个信号，获取一个值，占一个车位
        boolean b = park.tryAcquire();
        return "b";
    }
    @GetMapping("/go")
    @ResponseBody
    public String go(){
        RSemaphore park = redisson.getSemaphore("park");
//        释放一个车位
        park.release();
        return "ok";
    }
    /**
     * 放假,锁门
     * 5个班全部走完才可以锁门
     */
    @ResponseBody
    @GetMapping("/lockDoor")
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        door.await(); //等待闭锁都完成
        return "放假了";
    }

    @GetMapping("/gogogo/{id}")
    public String gogogo(@PathVariable("id") Long id){
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();//计数减一
        return id+"班的人都走了";
    }
}
