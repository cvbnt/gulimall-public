package com.atguigu.gulimall.order;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GulimallOrderApplicationTests {
    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessageTest() {
//        发送消息，如果发送的消息是对象，我们会使用序列化机制，将对象写出去。对象必须实现Serializable
        String msg = "Hello World!";
//        发送的对象类型的消息，可以是一个JSON
        for (int i = 0; i < 10; i++) {
            if (i%2==0){
                OrderReturnReasonEntity returnEntity = new OrderReturnReasonEntity();
                returnEntity.setId(1L);
                returnEntity.setCreateTime(new Date());
                returnEntity.setName("哈哈-"+i);
                rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",returnEntity);
            }else {
                OrderEntity entity = new OrderEntity();
                entity.setOrderSn(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",entity);
            }
            log.info("消息发送完成{}");
        }
    }
    /*
    * 1、如何创建Exchange、Queue、Binding
    *   1)、使用AmqpAdmin进行创建
    * 2、如何收发消息
    * */
    @Test
    public void createExchange() {
//        amqpAdmin
        /*
        * public DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
        * */
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功","hello-java-exchange");
    }

    @Test
    public void createQueue() {
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功","hello-java-queue");
    }
    /*
    * 目的地，目的地类型，交换机，路由键，自定义参数
    * public Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey, Map<String, Object> arguments)
    * 将exchange指定的交换机和destination目的地就行绑定，使用routingkey作为路由键
    * */
    @Test
    public void ceeateBinding() {
        Binding binding = new Binding("hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null);
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功","hello-java-binding");
    }
}
