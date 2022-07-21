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

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`
(
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT,
    `assignee_id`   bigint                                                        NULL DEFAULT NULL,
    `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `phone`         char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL DEFAULT NULL,
    `priority`      int                                                           NULL DEFAULT NULL,
    `status`        int                                                           NULL DEFAULT NULL,
    `ware_id`       bigint                                                        NULL DEFAULT NULL,
    `amount`        decimal(18, 4)                                                NULL DEFAULT NULL,
    `create_time`   datetime                                                      NULL DEFAULT NULL,
    `update_time`   datetime                                                      NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '采购信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`
(
    `id`          bigint         NOT NULL AUTO_INCREMENT,
    `purchase_id` bigint         NULL DEFAULT NULL COMMENT '采购单id',
    `sku_id`      bigint         NULL DEFAULT NULL COMMENT '采购商品id',
    `sku_num`     int            NULL DEFAULT NULL COMMENT '采购数量',
    `sku_price`   decimal(18, 4) NULL DEFAULT NULL COMMENT '采购金额',
    `ware_id`     bigint         NULL DEFAULT NULL COMMENT '仓库id',
    `status`      int            NULL DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`
(
    `id`       bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库名',
    `address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库地址',
    `areacode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '区域编码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '仓库信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `order_id`         bigint                                                        NULL DEFAULT NULL COMMENT 'order_id',
    `order_sn`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'order_sn',
    `consignee`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人',
    `consignee_tel`    char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL DEFAULT NULL COMMENT '收货人电话',
    `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配送地址',
    `order_comment`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单备注',
    `payment_way`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
    `task_status`      tinyint                                                       NULL DEFAULT NULL COMMENT '任务状态',
    `order_body`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单描述',
    `tracking_no`      char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL DEFAULT NULL COMMENT '物流单号',
    `create_time`      datetime                                                      NULL DEFAULT NULL COMMENT 'create_time',
    `task_comment`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作单备注',
    `ware_id`          bigint                                                        NULL DEFAULT NULL COMMENT '仓库id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存工作单'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sku_id`      bigint                                                        NULL DEFAULT NULL COMMENT 'sku_id',
    `sku_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku_name',
    `sku_num`     int                                                           NULL DEFAULT NULL COMMENT '购买个数',
    `task_id`     bigint                                                        NULL DEFAULT NULL COMMENT '工作单id',
    `ware_id`     bigint                                                        NULL DEFAULT NULL COMMENT '仓库id',
    `lock_status` int                                                           NULL DEFAULT NULL COMMENT '1-已锁定 2-已解锁 3-扣减',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 57
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存工作单'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sku_id`       bigint                                                        NULL DEFAULT NULL COMMENT 'sku_id',
    `ware_id`      bigint                                                        NULL DEFAULT NULL COMMENT '仓库id',
    `stock`        int                                                           NULL DEFAULT NULL COMMENT '库存数',
    `sku_name`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku_name',
    `stock_locked` int                                                           NULL DEFAULT 0 COMMENT '锁定库存',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
