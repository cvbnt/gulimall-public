package com.atguigu.gulimall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class GuliFeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return template -> {
            //                1、RequestContextHolder拿到刚进来的请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                System.out.println("RequestInterceptor线程...." + Thread.currentThread().getId());
                HttpServletRequest request = attributes.getRequest();
                if (request != null) {
//                同步请求头数据，Cookie
                    String cookie = request.getHeader("Cookie");
//                给新请求同步了老请求的Cookie
                    template.header("Cookie", cookie);
                }
            }
        };
    }
}