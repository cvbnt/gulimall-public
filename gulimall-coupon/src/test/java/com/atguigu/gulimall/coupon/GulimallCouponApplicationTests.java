package com.atguigu.gulimall.coupon;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//@SpringBootTest
public class GulimallCouponApplicationTests {

    @Test
    public void contextLoads() {
//        LocalDate now = LocalDate.now();
//        LocalDate plus = now.plusDays(1);
//        LocalDate plus2 = now.plusDays(2);
//        System.out.println(now);
//        System.out.println(plus);
//        System.out.println(plus2);
//
//        LocalTime min = LocalTime.MIN;
//        LocalTime max = LocalTime.MAX;
//        System.out.println(min);
//        System.out.println(max);
//
//        LocalDateTime start = LocalDateTime.of(now, min);
//        LocalDateTime end = LocalDateTime.of(plus2, max);
//        System.out.println(start);
//        System.out.println(end);
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(2);
        LocalDateTime of = LocalDateTime.of(localDate, LocalTime.MAX);
        String format = of.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);
    }

}
