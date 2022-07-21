package com.atguigu.gulimall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.member.dao.MemberDao;
import com.atguigu.gulimall.member.dao.MemberLevelDao;
import com.atguigu.gulimall.member.entity.MemberEntity;
import com.atguigu.gulimall.member.entity.MemberLevelEntity;
import com.atguigu.gulimall.member.exception.PhoneExistException;
import com.atguigu.gulimall.member.exception.UsernameExistException;
import com.atguigu.gulimall.member.service.MemberService;
import com.atguigu.gulimall.member.vo.MemberLoginVo;
import com.atguigu.gulimall.member.vo.MemberRegistVo;
import com.atguigu.gulimall.member.vo.SocialUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = new MemberEntity();
//        设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(levelEntity.getId());
//        检查用户名和手机号是否唯一.为了让controller能感知异常,异常机制
        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUserName());
        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUserName());
        entity.setNickname(vo.getUserName());
//        密码要进行加密存储。加盐：$1$+8位字符
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        entity.setPassword(encode);
//        其他的默认信息
//        保存
        memberDao.insert(entity);
    }

    @Override
    public void checkPhoneUnique(String phone) {
        MemberDao memberDao = this.baseMapper;
        Integer mobile = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) {
        MemberDao memberDao = this.baseMapper;
        Integer count = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count > 0) {
            throw new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();
//        1、去数据库查询
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct).or().eq("mobile", loginacct));
        if (entity == null) {
//            登录失败
            return null;
        } else {
//            1、获取到数据库的password
            String passwordDB = entity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            2、密码匹配
            boolean matches = passwordEncoder.matches(password, passwordDB);
            if (matches) {
                return entity;
            } else {
                return null;
            }
        }
    }

    @Override
    public MemberEntity login(SocialUser socialUser) {
//        登录和注册合并逻辑
        String uid = socialUser.getUid();
//        判断当前社交用户是否已经登录过系统
        MemberDao memberDao = this.baseMapper;
        MemberEntity memberEntity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("github_uid", uid));
        if (memberEntity != null) {
//            这个用户已经注册过
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
            update.setGithubAccessToken(socialUser.getAccess_token());
            memberDao.updateById(update);
            memberEntity.setGithubAccessToken(socialUser.getAccess_token());
            return memberEntity;
        } else {
//            没有查到当前社交用户对应的记录需要注册一个
            MemberEntity regist = new MemberEntity();
//            查询当前用户的社交信息
            try {
                RestTemplate restTemplate = new RestTemplate();
                String authentication = ":" + socialUser.getAccess_token();
                HttpHeaders headers = new HttpHeaders();
                headers.set("authorization", "Basic " + Base64.getEncoder().encodeToString(authentication.getBytes()));
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
                restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                //设置编码格式
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            headers.setContentType(type);
                headers.set("Accept", "application/vnd.github.v3+json");
                ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, new HttpEntity<byte[]>(headers), String.class);
                if (response.getStatusCodeValue() == 200) {
//                查询成功
                    String s = JSON.toJSONString(response.getBody()).replace("\\","");
                    s=s.substring(1,s.length()-1);
                    JSONObject jsonObject = JSON.parseObject(s);
                    String id = jsonObject.getString("id");
                    String login = jsonObject.getString("login");
                    regist.setGithubUid(id);
                    regist.setNickname(login);
                }
            } catch (Exception e) {

            }
            regist.setGithubAccessToken(socialUser.getAccess_token());
            memberDao.insert(regist);
            return regist;
        }
    }

}
