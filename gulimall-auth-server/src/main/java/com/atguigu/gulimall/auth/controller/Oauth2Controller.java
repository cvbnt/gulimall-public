package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.auth.feign.MemberFeignService;
import com.atguigu.gulimall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Base64;

/*
 * 处理社交登录请求
 * */
@Slf4j
@Controller
public class Oauth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 社交登录成功回调
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/oauth2.0/github/success")
    public String github(@RequestParam("code") String code, HttpSession session, HttpServletResponse servletResponse) throws Exception {
//        1、根据code换取accesstoken;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept","application/json");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("code",code);
        map.add("client_id","client_id");
        map.add("client_secret","client_secret");
        map.add("redirect_uri","http://auth.gulimall.com/oauth2.0/github/success");
        HttpEntity<MultiValueMap<String, Object>> values = new HttpEntity<>(map, headers);
        ResponseEntity<com.atguigu.gulimall.auth.vo.SocialUser> response = restTemplate.postForEntity("https://github.com/login/oauth/access_token", values, com.atguigu.gulimall.auth.vo.SocialUser.class);
        if (response.getStatusCodeValue()==200){
//            获取到Access Token
            SocialUser socialUser = response.getBody();
            RestTemplate restTemplate1 = new RestTemplate();
//            知道当前是哪个社交用户
//            1)、当前用户如果是第一次进网站，自动注册进来
//            登录或者注册这个社交用户
            String authentication = ":" + socialUser.getAccess_token();
            headers.set("authorization", "Basic " + Base64.getEncoder().encodeToString(authentication.getBytes()));
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate1.getMessageConverters().add(mappingJackson2HttpMessageConverter);
            //设置编码格式
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            headers.setContentType(type);
            headers.set("Accept", "application/vnd.github.v3+json");
            ResponseEntity<String> data = restTemplate1.exchange("https://api.github.com/user", HttpMethod.GET, new HttpEntity<byte[]>(headers), String.class);
            if (data.getStatusCodeValue() == 200) {
//                查询成功
                String s = JSON.toJSONString(data.getBody()).replace("\\","");
                s=s.substring(1,s.length()-1);
                JSONObject jsonObject = JSONObject.parseObject(s);
                String id = jsonObject.getString("id");
                socialUser.setUid(id);
            }
            R oauthlogin = memberFeignService.oauthlogin(socialUser);
            if (oauthlogin.getCode()==0){
                MemberRespVo data1 = oauthlogin.getData("data", new TypeReference<MemberRespVo>() {
                });
                log.info("登录成功：用户:{}"+data1.toString());
//                第一次使用session;命令浏览器保存卡号，JSESSIONID这个cookie，
//                以后浏览器访问哪个网站，就会带上这个网站的cookie
//                子域之间：gulimall.com，auth.gulimall.com order.gulimall.com
//                发卡的时候(指定域名为父域名)，即使是子域系统发的卡，也能让父域也能直接使用，
                session.setAttribute("loginUser",data1);
//                new Cookie("JSESSIONID","dadaa").setDomain();
//                servletResponse.addCookie();
//                TODO 1、默认发的令牌。作用域：当前域；（解决子域session共享问题）
//                TODO 2、使用JSON的序列化方式来序列化对象到redis中
                return "redirect:http://gulimall.com";
            }else {
                return "redirect:http://auth.gulimall.com/login.html";
            }
        }else {
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
