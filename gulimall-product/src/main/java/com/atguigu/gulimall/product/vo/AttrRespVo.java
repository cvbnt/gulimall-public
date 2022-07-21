package com.atguigu.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{
//    分类名字
    private String catelogName;
//    所属分组
    private String groupName;

    private Long[] catelogPath;

    private Integer valueType;
}
