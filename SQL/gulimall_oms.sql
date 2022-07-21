SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
DROP TABLE IF EXISTS `branch_table`;
CREATE TABLE `branch_table`
(
    `branch_id`         bigint                                                         NOT NULL,
    `xid`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `transaction_id`    bigint                                                         NULL DEFAULT NULL,
    `resource_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL,
    `resource_id`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `lock_key`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `branch_type`       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci    NULL DEFAULT NULL,
    `status`            tinyint                                                        NULL DEFAULT NULL,
    `client_id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL,
    `application_data`  varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `gmt_create`        datetime                                                       NULL DEFAULT NULL,
    `gmt_modified`      datetime                                                       NULL DEFAULT NULL,
    PRIMARY KEY (`branch_id`) USING BTREE,
    INDEX `idx_xid` (`xid`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
DROP TABLE IF EXISTS `global_table`;
CREATE TABLE `global_table`
(
    `xid`                       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `transaction_id`            bigint                                                         NULL DEFAULT NULL,
    `status`                    tinyint                                                        NOT NULL,
    `application_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL,
    `transaction_service_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL,
    `transaction_name`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `timeout`                   int                                                            NULL DEFAULT NULL,
    `begin_time`                bigint                                                         NULL DEFAULT NULL,
    `application_data`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `gmt_create`                datetime                                                       NULL DEFAULT NULL,
    `gmt_modified`              datetime                                                       NULL DEFAULT NULL,
    PRIMARY KEY (`xid`) USING BTREE,
    INDEX `idx_gmt_modified_status` (`gmt_modified`, `status`) USING BTREE,
    INDEX `idx_transaction_id` (`transaction_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
DROP TABLE IF EXISTS `lock_table`;
CREATE TABLE `lock_table`
(
    `row_key`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `xid`            varchar(96) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `transaction_id` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL,
    `branch_id`      mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL,
    `resource_id`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `table_name`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `pk`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `gmt_create`     datetime                                                      NULL DEFAULT NULL,
    `gmt_modified`   datetime                                                      NULL DEFAULT NULL,
    PRIMARY KEY (`row_key`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`
(
    `message_id`     char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NOT NULL,
    `content`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL,
    `to_exchange`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `routing_key`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `class_type`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `message_status` int                                                           NULL DEFAULT 0 COMMENT '0-新建 1-已发送 2-错误抵达 3-已抵达',
    `create_time`    datetime                                                      NULL DEFAULT NULL,
    `update_time`    datetime                                                      NULL DEFAULT NULL,
    PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`
(
    `id`                      bigint                                                        NOT NULL AUTO_INCREMENT,
    `member_id`               bigint                                                        NULL DEFAULT NULL,
    `order_sn`                char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL DEFAULT NULL,
    `coupon_id`               bigint                                                        NULL DEFAULT NULL,
    `create_time`             datetime                                                      NULL DEFAULT NULL,
    `member_username`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `total_amount`            decimal(18, 4)                                                NULL DEFAULT NULL,
    `pay_amount`              decimal(18, 4)                                                NULL DEFAULT NULL,
    `freight_amount`          decimal(18, 4)                                                NULL DEFAULT NULL,
    `promotion_amount`        decimal(18, 4)                                                NULL DEFAULT NULL,
    `integration_amount`      decimal(18, 4)                                                NULL DEFAULT NULL,
    `coupon_amount`           decimal(18, 4)                                                NULL DEFAULT NULL,
    `discount_amount`         decimal(18, 4)                                                NULL DEFAULT NULL,
    `pay_type`                tinyint                                                       NULL DEFAULT NULL,
    `source_type`             tinyint                                                       NULL DEFAULT NULL,
    `status`                  tinyint                                                       NULL DEFAULT NULL COMMENT '0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    `delivery_company`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `delivery_sn`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `auto_confirm_day`        int                                                           NULL DEFAULT NULL,
    `integration`             int                                                           NULL DEFAULT NULL,
    `growth`                  int                                                           NULL DEFAULT NULL,
    `bill_type`               tinyint                                                       NULL DEFAULT NULL,
    `bill_header`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `bill_content`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `bill_receiver_phone`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `bill_receiver_email`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `receiver_name`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `receiver_phone`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `receiver_post_code`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `receiver_province`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `receiver_city`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `receiver_region`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `receiver_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `note`                    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `confirm_status`          tinyint                                                       NULL DEFAULT NULL,
    `delete_status`           tinyint                                                       NULL DEFAULT NULL,
    `use_integration`         int                                                           NULL DEFAULT NULL,
    `payment_time`            datetime                                                      NULL DEFAULT NULL,
    `delivery_time`           datetime                                                      NULL DEFAULT NULL,
    `receive_time`            datetime                                                      NULL DEFAULT NULL,
    `comment_time`            datetime                                                      NULL DEFAULT NULL,
    `modify_time`             datetime                                                      NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`
(
    `id`                 bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `order_id`           bigint                                                        NULL DEFAULT NULL COMMENT 'order_id',
    `order_sn`           char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL DEFAULT NULL COMMENT 'order_sn',
    `spu_id`             bigint                                                        NULL DEFAULT NULL COMMENT 'spu_id',
    `spu_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'spu_name',
    `spu_pic`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'spu_pic',
    `spu_brand`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌',
    `category_id`        bigint                                                        NULL DEFAULT NULL COMMENT '商品分类id',
    `sku_id`             bigint                                                        NULL DEFAULT NULL COMMENT '商品sku编号',
    `sku_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品sku名字',
    `sku_pic`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品sku图片',
    `sku_price`          decimal(18, 4)                                                NULL DEFAULT NULL COMMENT '商品sku价格',
    `sku_quantity`       int                                                           NULL DEFAULT NULL COMMENT '商品购买的数量',
    `sku_attrs_vals`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
    `promotion_amount`   decimal(18, 4)                                                NULL DEFAULT NULL COMMENT '商品促销分解金额',
    `coupon_amount`      decimal(18, 4)                                                NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
    `integration_amount` decimal(18, 4)                                                NULL DEFAULT NULL COMMENT '积分优惠分解金额',
    `real_amount`        decimal(18, 4)                                                NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
    `gift_integration`   int                                                           NULL DEFAULT NULL COMMENT '赠送积分',
    `gift_growth`        int                                                           NULL DEFAULT NULL COMMENT '赠送成长值',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 61
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单项信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `order_id`     bigint                                                        NULL DEFAULT NULL COMMENT '订单id',
    `operate_man`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
    `create_time`  datetime                                                      NULL DEFAULT NULL COMMENT '操作时间',
    `order_status` tinyint                                                       NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
    `note`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`
(
    `id`              bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `order_id`        bigint                                                         NULL DEFAULT NULL COMMENT 'order_id',
    `sku_id`          bigint                                                         NULL DEFAULT NULL COMMENT '退货商品id',
    `order_sn`        char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      NULL DEFAULT NULL COMMENT '订单编号',
    `create_time`     datetime                                                       NULL DEFAULT NULL COMMENT '申请时间',
    `member_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '会员用户名',
    `return_amount`   decimal(18, 4)                                                 NULL DEFAULT NULL COMMENT '退款金额',
    `return_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '退货人姓名',
    `return_phone`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '退货人电话',
    `status`          tinyint(1)                                                     NULL DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
    `handle_time`     datetime                                                       NULL DEFAULT NULL COMMENT '处理时间',
    `sku_img`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '商品图片',
    `sku_name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '商品名称',
    `sku_brand`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '商品品牌',
    `sku_attrs_vals`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '商品销售属性(JSON)',
    `sku_count`       int                                                            NULL DEFAULT NULL COMMENT '退货数量',
    `sku_price`       decimal(18, 4)                                                 NULL DEFAULT NULL COMMENT '商品单价',
    `sku_real_price`  decimal(18, 4)                                                 NULL DEFAULT NULL COMMENT '商品实际支付单价',
    `reason`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '原因',
    `description述`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '描述',
    `desc_pics`       varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
    `handle_note`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '处理备注',
    `handle_man`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '处理人员',
    `receive_man`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '收货人',
    `receive_time`    datetime                                                       NULL DEFAULT NULL COMMENT '收货时间',
    `receive_note`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '收货备注',
    `receive_phone`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '收货电话',
    `company_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '公司收货地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单退货申请'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '退货原因名',
    `sort`        int                                                           NULL DEFAULT NULL COMMENT '排序',
    `status`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '启用状态',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT 'create_time',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '退货原因'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`
(
    `id`                    bigint  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `flash_order_overtime`  int     NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
    `normal_order_overtime` int     NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
    `confirm_overtime`      int     NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
    `finish_overtime`       int     NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
    `comment_overtime`      int     NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
    `member_level`          tinyint NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单配置信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info`
(
    `id`               bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `order_sn`         char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      NULL DEFAULT NULL COMMENT '订单号（对外业务号）',
    `order_id`         bigint                                                         NULL DEFAULT NULL COMMENT '订单id',
    `alipay_trade_no`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '支付宝交易流水号',
    `total_amount`     decimal(18, 4)                                                 NULL DEFAULT NULL COMMENT '支付总金额',
    `subject`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '交易内容',
    `payment_status`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '支付状态',
    `create_time`      datetime                                                       NULL DEFAULT NULL COMMENT '创建时间',
    `confirm_time`     datetime                                                       NULL DEFAULT NULL COMMENT '确认时间',
    `callback_content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回调内容',
    `callback_time`    datetime                                                       NULL DEFAULT NULL COMMENT '回调时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info`
(
    `id`              bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `order_return_id` bigint                                                         NULL DEFAULT NULL COMMENT '退款的订单',
    `refund`          decimal(18, 4)                                                 NULL DEFAULT NULL COMMENT '退款金额',
    `refund_sn`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '退款交易流水号',
    `refund_status`   tinyint(1)                                                     NULL DEFAULT NULL COMMENT '退款状态',
    `refund_channel`  tinyint                                                        NULL DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
    `refund_content`  varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '退款信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint                                                  NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint                                                  NOT NULL,
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `rollback_info` longblob                                                NOT NULL,
    `log_status`    int                                                     NOT NULL,
    `log_created`   datetime                                                NOT NULL,
    `log_modified`  datetime                                                NOT NULL,
    `ext`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ux_undo_log` (`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
