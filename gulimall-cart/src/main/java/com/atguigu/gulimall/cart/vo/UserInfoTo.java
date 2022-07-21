package com.atguigu.gulimall.cart.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoTo {
    private Long userId;
    private String userKey;//一定封装
    private boolean tempUser=false;
}
