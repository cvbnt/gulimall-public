package com.atguigu.gulimall.order.web;

import com.alipay.api.AlipayApiException;
import com.atguigu.gulimall.order.config.AlipayTemplate;
import com.atguigu.gulimall.order.service.OrderService;
import com.atguigu.gulimall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayWebController {
    @Autowired
    AlipayTemplate alipayTemplate;
    @Autowired
    OrderService orderService;

    /**
     * 1、将支付页让浏览器展示。
     * 2、支付成功以后，我们要跳到用户的订单列表页
     * @param orderSn
     * @return
     * @throws AlipayApiException
     */
    @ResponseBody
    @GetMapping(value = "/payOrder", produces = "text/html")
    public String pay(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
//        PayVo payVo = new PayVo();
////        订单备注
//        payVo.setBody("");
////        订单号
//        payVo.setOut_trade_no();
////        订单的主题
//        payVo.setSubject();
////        订单的金额
//        payVo.setTotal_amount();
        PayVo payVo = orderService.getOrderPay(orderSn);
//        返回的是一个页面。将此页面直接交给浏览器就行
        String pay = alipayTemplate.pay(payVo);
        System.out.println(pay);
        return pay;
    }
}
