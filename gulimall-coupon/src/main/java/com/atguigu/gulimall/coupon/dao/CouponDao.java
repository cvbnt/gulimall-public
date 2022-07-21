package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author cvbnt
 * @email 5465497@gmail.com
 * @date 2021-05-28 11:38:49
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
