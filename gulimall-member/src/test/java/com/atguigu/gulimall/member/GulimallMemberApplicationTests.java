package com.atguigu.gulimall.member;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
public class GulimallMemberApplicationTests {

    @Test
    public void contextLoads() {
//        抗修改性：彩虹表
//        String s = DigestUtils.md5Hex("123456");
//        MD5不能直接进行密码的加密存储
//        盐值加密：随机值
//        验证：123456进行盐值加密
//        String s1 = Md5Crypt.md5Crypt("123456".getBytes(),"$1$qqqqqqqq");
//        System.out.println(s1);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        boolean matches = passwordEncoder.matches("123456", encode);
        System.out.println(matches);
    }

}
