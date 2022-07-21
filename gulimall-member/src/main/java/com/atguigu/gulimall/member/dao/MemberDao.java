package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author cvbnt
 * @email 5465497@gmail.com
 * @date 2021-05-28 14:15:07
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
