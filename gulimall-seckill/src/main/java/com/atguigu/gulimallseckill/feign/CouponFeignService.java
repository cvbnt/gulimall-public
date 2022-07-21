package com.atguigu.gulimallseckill.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @GetMapping("/coupon/seckillsession/latest3DaySession")
    R getLatest3DaySession();
}
