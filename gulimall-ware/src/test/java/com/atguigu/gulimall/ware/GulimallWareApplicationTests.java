package com.atguigu.gulimall.ware;

import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GulimallWareApplicationTests {
    @Autowired
    PurchaseDetailService detailService;
    @Test
    public void contextLoads() {
        PurchaseDetailEntity entity = detailService.getById(3);
        System.out.println(entity);
    }

}
