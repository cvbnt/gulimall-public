package com.atguigu.gulimall.order.controller;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class RabbitController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @GetMapping("/sendMq")
    public String sendMq(@RequestParam(value = "num",defaultValue = "10") Integer num){
        for (int i = 0; i < num; i++) {
            if (i%2==0){
                OrderReturnReasonEntity returnEntity = new OrderReturnReasonEntity();
                returnEntity.setId(1L);
                returnEntity.setCreateTime(new Date());
                returnEntity.setName("哈哈-"+i);
                rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",returnEntity,new CorrelationData(UUID.randomUUID().toString()));
            }else {
                OrderEntity entity = new OrderEntity();
                entity.setOrderSn(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("hello-java-exchange","hello22.java",entity,new CorrelationData(UUID.randomUUID().toString()));
            }
        }
        return "ok";
    }
}
