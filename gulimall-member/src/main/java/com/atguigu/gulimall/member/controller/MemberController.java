package com.atguigu.gulimall.member.controller;

import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.member.entity.MemberEntity;
import com.atguigu.gulimall.member.exception.PhoneExistException;
import com.atguigu.gulimall.member.exception.UsernameExistException;
import com.atguigu.gulimall.member.feign.CouponFeignService;
import com.atguigu.gulimall.member.service.MemberService;
import com.atguigu.gulimall.member.vo.MemberLoginVo;
import com.atguigu.gulimall.member.vo.MemberRegistVo;
import com.atguigu.gulimall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员
 *
 * @author cvbnt
 * @email 5465497@gmail.com
 * @date 2021-05-28 14:15:07
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/oauth2/login")
    public R oauthlogin(@RequestBody SocialUser socialUser) throws Exception{
        MemberEntity entity=memberService.login(socialUser);
        if (entity!=null){
            return R.ok().setData(entity);
        }else {
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
    }

    /**
     * 列表
     */
    @Autowired
    CouponFeignService couponFeignService;
    @RequestMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R membercoupons = couponFeignService.membercoupons();
        membercoupons.get("coupons");
        return R.ok().put("member",memberEntity).put("coupons",membercoupons.get("coupons"));
    }
    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo){
        MemberEntity entity=memberService.login(vo);
        if (entity!=null){
            return R.ok().setData(entity);
        }else {
            return R.error(BizCodeEnume.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getCode(), BizCodeEnume.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
    }
    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegistVo vo){
        try {
            memberService.regist(vo);
        }catch (PhoneExistException e){
            return R.error(BizCodeEnume.PHONE_EXiST_EXCEPTION.getCode(),BizCodeEnume.PHONE_EXiST_EXCEPTION.getMsg());
        }catch (UsernameExistException e){
            return R.error(BizCodeEnume.PHONE_EXiST_EXCEPTION.getCode(),BizCodeEnume.PHONE_EXiST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
