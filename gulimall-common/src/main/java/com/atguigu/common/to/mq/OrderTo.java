package com.atguigu.common.to.mq;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderTo {
    private Long id;
    /**
     *
     */
    private Long memberId;
    /**
     *
     */
    private String orderSn;
    /**
     *
     */
    private Long couponId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String memberUsername;
    /**
     *
     */
    private BigDecimal totalAmount;
    /**
     *
     */
    private BigDecimal payAmount;
    /**
     *
     */
    private BigDecimal freightAmount;
    /**
     *
     */
    private BigDecimal promotionAmount;
    /**
     *
     */
    private BigDecimal integrationAmount;
    /**
     *
     */
    private BigDecimal couponAmount;
    /**
     *
     */
    private BigDecimal discountAmount;
    /**
     *
     */
    private Integer payType;
    /**
     *
     */
    private Integer sourceType;
    /**
     * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
     */
    private Integer status;
    /**
     * 物流公司(配送方式)
     */
    private String deliveryCompany;
    /**
     * 物流单号
     */
    private String deliverySn;
    /**
     * 自动确认时间（天）
     */
    private Integer autoConfirmDay;
    /**
     * 可以获得的积分
     */
    private Integer integration;
    /**
     * 可以获得的成长值
     */
    private Integer growth;
    /**
     * 发票类型【0->不开发票；1->电子发票；2->纸质发票】
     */
    private Integer billType;
    /**
     * 发票抬头
     */
    private String billHeader;
    /**
     * 发票内容
     */
    private String billContent;
    /**
     * 收票人电话
     */
    private String billReceiverPhone;
    /**
     * 收票人邮箱
     */
    private String billReceiverEmail;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货人邮编
     */
    private String receiverPostCode;
    /**
     * 省份/直辖市
     */
    private String receiverProvince;
    /**
     * 城市
     */
    private String receiverCity;
    /**
     * 区
     */
    private String receiverRegion;
    /**
     * 详细地址
     */
    private String receiverDetailAddress;
    /**
     * 订单备注
     */
    private String note;
    /**
     * 确认收货状态【0->未确认；1->已确认】
     */
    private Integer confirmStatus;
    /**
     * 删除状态【0->未删除；1->已删除】
     */
    private Integer deleteStatus;
    /**
     *
     */
    private Integer useIntegration;
    /**
     *
     */
    private Date paymentTime;
    /**
     *
     */
    private Date deliveryTime;
    /**
     *
     */
    private Date receiveTime;
    /**
     *
     */
    private Date commentTime;
    /**
     *
     */
    private Date modifyTime;
}
