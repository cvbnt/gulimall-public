package com.atguigu.gulimall.auth;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class GulimallAuthServerApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(this.isValid("{[]}"));
    }

    public boolean isValid(String s) {
        int length = s.length();
        ArrayList<Object> list1 = new ArrayList<>();
        for (int j = 0; j < length; j = j + 2) {
            if (j + 2 < length) {
                list1.add(s.substring(j, j + 2));
            } else {
                list1.add(s.substring(length - 2, length));
            }
        }
        for (Object o : list1) {
            if (!o.equals("()") && !o.equals("[]") && !o.equals("{}")) {
                return false;
            }
        }
        return true;
    }
}
