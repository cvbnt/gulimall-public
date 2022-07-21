package com.atguigu.gulimall.auth.vo;

import lombok.Data;

@Data
public class SocialUser {
    private String access_token;
    private String scope;
    private String token_type;
    private String uid;
}
