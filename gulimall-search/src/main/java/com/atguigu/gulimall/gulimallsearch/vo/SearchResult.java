package com.atguigu.gulimall.gulimallsearch.vo;

import com.atguigu.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SearchResult {
//    查询到的所有商品信息
    private List<SkuEsModel> products;
    /*以下是分页信息*/
//    当前页码
    private Integer pageNum;
//    总记录数
    private Long total;
//    总页码
    private Integer totalPages;
    private List<Integer> pageNavs;
//    当前查询到的结果,所有涉及到的品牌
    private List<BrandVo> brands;
//    当前查询到的结果,涉及到的所有分类
    private List<CatalogVo> catalogs;
//    当前查询到的结果,涉及到的所有属性
    private List<AttrVo> attrs;
//    ==========以上是返回给页面的所有信息==========

//    面包屑导航数据
    private List<NavVo> navs =new ArrayList<>();
    private List<Long> attrIds = new ArrayList<>();
    @Data
    public static class NavVo{
        private String navName;
        private String navValue;
        private String link;
    }

    /**
     * 涉及品牌
     */
    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    /**
     * 涉及分类
     */
    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;
        private String brandImg;
    }
    /**
     * 涉及属性
     */
    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }
}
