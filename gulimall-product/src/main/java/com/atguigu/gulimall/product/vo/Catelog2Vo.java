package com.atguigu.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//    二级分类VO
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo {
    private String catelog1Id;          //一级父分类
    List<Catelog2Vo.Catelog3Vo> catelog3List;   //三级子分类
    private String id;
    private String name;
//    三级分类VO
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Catelog3Vo{
        private String catelog2Id;    //父分类，二级分类id
        private String id;
        private String name;
    }
}
