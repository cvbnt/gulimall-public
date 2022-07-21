package com.atguigu.gulimall.gulimallsearch.vo;

import lombok.Data;

import java.util.List;

/**
 * 封装页面所有可能传递过来的查询条件
 * catalog3Id=225&keyword=小米&sort=saleCount_asc&hasStock=0/1&brandId=1&brandId=2&attrs=1_其他:安卓&attrs=2_5存:6寸
 */
@Data
public class SearchParam {
    private String keyword; //页面传递过来的全文匹配关键字
    private Long catalog3Id; //三级分类id
    /*
    * sort=saleCount_asc/desc 销量
    * sort=skuPrice_asc/desc 价格
    * sprt=hotScore_asc/desc 热度评分
    * */
    private String sort; //排序条件
    /*
    * 好多过滤条件
    * hasStock(是否有货),skuPrice区间,brandId,catalog3Id,attrs
    * hasStock=0/1
    * skuPrice=1-500/_500/500_
    * brandId=1
    * attrs
    * */
    private Integer hasStock;//是否只显示有货 0：无库存，1：有库存
    private String skuPrice;//价格区间
    private List<Long> brandId;//按照多个品牌id进行多选
    private List<String> attrs;//按照属性进行筛选
    private Integer pageNum=1;//页码

    private String _queryString;//原生的所有查询条件
}
