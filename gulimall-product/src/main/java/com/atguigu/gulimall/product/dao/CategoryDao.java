package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author cvbnt
 * @email 5465497@gmail.com
 * @date 2021-05-24 10:44:02
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
