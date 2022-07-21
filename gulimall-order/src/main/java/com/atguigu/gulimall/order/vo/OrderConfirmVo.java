package com.atguigu.gulimall.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单确认页需要用的数据
 */
@Data
public class OrderConfirmVo {
    //    收货地址，ums_member_receive_address表
    @Getter
    @Setter
    List<MemberAddressVo> address;
    //    所有选中的购物项
    @Getter
    @Setter
    List<OrderItemVo> items;
    //    发票记录...
//    优惠券信息...
    private Integer integration;

    @Getter
    @Setter
    Map<Long,Boolean> stocks;
    //    防重令牌
    @Getter
    @Setter
    String orderToken;

    public Integer getCount() {
        Integer i = 0;
        if (items != null) {
            for (OrderItemVo item : items) {
                i += item.getCount();
            }
        }
        return i;
    }
//    订单总额
//    BigDecimal total;
//    应付价格
//    BigDecimal payPrice;

    public BigDecimal getTotal() {
        BigDecimal sum = new BigDecimal("0");
        if (items != null) {
            for (OrderItemVo item : items) {
                BigDecimal multiply = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                sum = sum.add(multiply);
            }
        }
        return sum;
    }

    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
