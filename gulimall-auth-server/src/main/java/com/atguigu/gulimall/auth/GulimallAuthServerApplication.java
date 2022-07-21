package com.atguigu.gulimall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/*
* 核心原理
* @EnableRedisHttpSession
*   1、给容器中添加了一个组件
*       SessionRepository=>>>[RedisOperationsSessionRepository]：redis操作session，session的增删改查封装类
*   2、SessionRepositoryFilter==》Filter：session存储过滤器;每个请求过来都必须经过filter
*       1、创建的时候，就自动从容器中获取到了SessionRepository;
*       2、原生的request，response都被包装。SessionRepositoryRequestWrapper，
*       3、以后获取session。request.getSession();
*       //SessionRepositoryRequestWrapper
*       4、wrapperRequest.getSession();===> SessionRepository中获取到的。
* 装饰者模式：
*   自动延期：redis中的数据也是有过期时间的
* */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableRedisHttpSession //整合redis作为session存储
public class GulimallAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallAuthServerApplication.class, args);
    }
}
