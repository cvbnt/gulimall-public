package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author cvbnt
 * @email 5465497@gmail.com
 * @date 2021-05-28 14:31:09
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
