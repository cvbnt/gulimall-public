SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_NAME`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `BLOB_DATA`     blob                                                    NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    INDEX `SCHED_NAME` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `CALENDAR`      blob                                                    NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`
(
    `SCHED_NAME`      varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_NAME`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP`   varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TIME_ZONE_ID`    varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `ENTRY_ID`          varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `TRIGGER_NAME`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `INSTANCE_NAME`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `FIRED_TIME`        bigint                                                  NOT NULL,
    `SCHED_TIME`        bigint                                                  NOT NULL,
    `PRIORITY`          int                                                     NOT NULL,
    `STATE`             varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `JOB_NAME`          varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `JOB_GROUP`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `IS_NONCONCURRENT`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
    INDEX `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
    INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
    INDEX `IDX_QRTZ_FT_J_G` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_FT_JG` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_FT_T_G` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_FT_TG` (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `JOB_NAME`          varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `JOB_GROUP`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `DESCRIPTION`       varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `JOB_CLASS_NAME`    varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `IS_DURABLE`        varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `IS_NONCONCURRENT`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `IS_UPDATE_DATA`    varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `JOB_DATA`          blob                                                    NULL,
    PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
    INDEX `IDX_QRTZ_J_GRP` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`
(
    `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `LOCK_NAME`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `INSTANCE_NAME`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `LAST_CHECKIN_TIME` bigint                                                  NOT NULL,
    `CHECKIN_INTERVAL`  bigint                                                  NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`
(
    `SCHED_NAME`      varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_NAME`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP`   varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `REPEAT_COUNT`    bigint                                                  NOT NULL,
    `REPEAT_INTERVAL` bigint                                                  NOT NULL,
    `TIMES_TRIGGERED` bigint                                                  NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_NAME`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `STR_PROP_1`    varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `STR_PROP_2`    varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `STR_PROP_3`    varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `INT_PROP_1`    int                                                     NULL DEFAULT NULL,
    `INT_PROP_2`    int                                                     NULL DEFAULT NULL,
    `LONG_PROP_1`   bigint                                                  NULL DEFAULT NULL,
    `LONG_PROP_2`   bigint                                                  NULL DEFAULT NULL,
    `DEC_PROP_1`    decimal(13, 4)                                          NULL DEFAULT NULL,
    `DEC_PROP_2`    decimal(13, 4)                                          NULL DEFAULT NULL,
    `BOOL_PROP_1`   varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL,
    `BOOL_PROP_2`   varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`
(
    `SCHED_NAME`     varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_NAME`   varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `TRIGGER_GROUP`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `JOB_NAME`       varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `JOB_GROUP`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `DESCRIPTION`    varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint                                                  NULL DEFAULT NULL,
    `PREV_FIRE_TIME` bigint                                                  NULL DEFAULT NULL,
    `PRIORITY`       int                                                     NULL DEFAULT NULL,
    `TRIGGER_STATE`  varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `TRIGGER_TYPE`   varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `START_TIME`     bigint                                                  NOT NULL,
    `END_TIME`       bigint                                                  NULL DEFAULT NULL,
    `CALENDAR_NAME`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `MISFIRE_INSTR`  smallint                                                NULL DEFAULT NULL,
    `JOB_DATA`       blob                                                    NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_J` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_JG` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_C` (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
    INDEX `IDX_QRTZ_T_G` (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_STATE` (`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`,
                                           `TRIGGER_STATE`) USING BTREE,
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`
(
    `job_id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `bean_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT 'spring bean名称',
    `params`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数',
    `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT 'cron表达式',
    `status`          tinyint                                                        NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
    `remark`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '备注',
    `create_time`     datetime                                                       NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`
(
    `log_id`      bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
    `job_id`      bigint                                                         NOT NULL COMMENT '任务id',
    `bean_name`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT 'spring bean名称',
    `params`      varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数',
    `status`      tinyint                                                        NOT NULL COMMENT '任务状态    0：成功    1：失败',
    `error`       varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '失败信息',
    `times`       int                                                            NOT NULL COMMENT '耗时(单位：毫秒)',
    `create_time` datetime                                                       NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`log_id`) USING BTREE,
    INDEX `job_id` (`job_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 791
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`
(
    `uuid`        char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT 'uuid',
    `code`        varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '验证码',
    `expire_time` datetime                                                    NULL DEFAULT NULL COMMENT '过期时间',
    PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统验证码'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT,
    `param_key`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT 'key',
    `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'value',
    `status`      tinyint                                                        NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `param_key` (`param_key`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '用户名',
    `operation`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '用户操作',
    `method`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '请求方法',
    `params`      varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
    `time`        bigint                                                         NOT NULL COMMENT '执行时长(毫秒)',
    `ip`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT 'IP地址',
    `create_date` datetime                                                       NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`   bigint                                                        NOT NULL AUTO_INCREMENT,
    `parent_id` bigint                                                        NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
    `name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '菜单名称',
    `url`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单URL',
    `perms`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
    `type`      int                                                           NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
    `icon`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '菜单图标',
    `order_num` int                                                           NULL DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 77
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单管理'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `url`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'URL地址',
    `create_date` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件上传'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`        bigint                                                        NOT NULL AUTO_INCREMENT,
    `role_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
    `remark`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `create_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '创建者ID',
    `create_time`    datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
    `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色与菜单对应关系'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`        bigint                                                        NOT NULL AUTO_INCREMENT,
    `username`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户名',
    `password`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
    `salt`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '盐',
    `email`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
    `mobile`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
    `status`         tinyint                                                       NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
    `create_user_id` bigint                                                        NULL DEFAULT NULL COMMENT '创建者ID',
    `create_time`    datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
    `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与角色对应关系'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`
(
    `user_id`     bigint                                                        NOT NULL,
    `token`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'token',
    `expire_time` datetime                                                      NULL DEFAULT NULL COMMENT '过期时间',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `token` (`token`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户Token'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`
(
    `user_id`     bigint                                                       NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `mobile`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
    `password`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
    `create_time` datetime                                                     NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户'
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
