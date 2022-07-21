package com.atguigu.gulimall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/*
 * 整个购物车
 * 需要计算的属性，必须重写他的get方法，保证每次获取属性都会进行计算
 * */
public class Cart {
    List<CartItem> items;
    //    商品数量
    private Integer countNum;
    //    商品类型数量
    private Integer countType;
    //    商品总价
    private BigDecimal totalAmount;
    //    减免价格
    private BigDecimal reduce = new BigDecimal("0");

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        return items.size();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
//        1、计算购物项总价
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                if (item.getCheck()) {
                    BigDecimal totalPrice = item.getTotalPrice();
                    amount = amount.add(totalPrice);
                }
            }
        }
//        2、减去优惠总价
        return amount.subtract(getReduce());
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
