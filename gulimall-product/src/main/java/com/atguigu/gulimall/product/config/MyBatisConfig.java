package com.atguigu.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.atguigu.gulimall.product.dao")
public class MyBatisConfig {
    //    引入分页
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInnerInterceptor = new PaginationInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setLimit(1000);
        return paginationInnerInterceptor;
    }
}
