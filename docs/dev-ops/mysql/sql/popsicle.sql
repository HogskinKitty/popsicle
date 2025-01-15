SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE database if NOT EXISTS `popsicle` default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use `popsicle`;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `menu_id`        bigint       NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`      varchar(100) NOT NULL COMMENT '菜单名称',
    `parent_id`      bigint       DEFAULT NULL COMMENT '父级菜单ID',
    `menu_type`      int          DEFAULT NULL COMMENT '菜单类型：1目录，2菜单，3按钮',
    `icon`           varchar(100) DEFAULT NULL COMMENT '图标',
    `sort`           int          DEFAULT NULL COMMENT '菜单排序',
    `route_name`     varchar(30)  DEFAULT NULL COMMENT '路由名称',
    `route_path`     varchar(64)  DEFAULT NULL COMMENT '路由地址',
    `component_path` varchar(64)  DEFAULT NULL COMMENT '组件地址',
    `permission`     varchar(100) DEFAULT NULL COMMENT '权限标识',
    `remark`         varchar(200) DEFAULT NULL COMMENT '备注',
    `frame_status`   tinyint      DEFAULT NULL COMMENT '外链状态：0否，1是',
    `frame_url`      varchar(100) DEFAULT NULL COMMENT '外链地址',
    `status`         tinyint      DEFAULT NULL COMMENT '禁用状态：0正常，1禁用',
    `delete_status`  tinyint      DEFAULT '0' COMMENT '删除状态：0否，1是',
    `create_time`    datetime     DEFAULT (now()) COMMENT '创建时间',
    `update_time`    datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='菜单表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `role_id`     bigint       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(100) NOT NULL COMMENT '名称',
    `role_code`   varchar(100) DEFAULT NULL COMMENT '角色编码',
    `role_desc`   varchar(100) DEFAULT NULL COMMENT '描述',
    `sort`        int          DEFAULT '0' COMMENT '排序',
    `create_time` datetime     DEFAULT (now()) COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色表';

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单ID',
    `role_id`     bigint   DEFAULT NULL COMMENT '角色ID',
    `menu_id`     bigint   DEFAULT NULL COMMENT '菜单ID',
    `create_time` datetime DEFAULT (now()) COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`       bigint       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`      varchar(100) NOT NULL COMMENT '用户名',
    `password`      varchar(100) NOT NULL COMMENT '密码',
    `real_name`     varchar(50)  DEFAULT NULL COMMENT '真实姓名',
    `gender`        int          DEFAULT '3' COMMENT '性别：1男，2女，3未知',
    `avatar`        varchar(100) DEFAULT NULL COMMENT '头像',
    `email`         varchar(50)  DEFAULT NULL COMMENT '邮箱',
    `phone_number`  varchar(11)  DEFAULT NULL COMMENT '手机号码',
    `status`        tinyint COMMENT '禁用状态：0正常，1禁用',
    `delete_status` tinyint COMMENT '删除状态：0否，1是',
    `remark`        varchar(200) DEFAULT NULL COMMENT '备注',
    `create_time`   datetime     DEFAULT (now()) COMMENT '创建时间',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='后台用户表';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
    `user_id`     bigint   DEFAULT NULL COMMENT '用户ID',
    `role_id`     bigint   DEFAULT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT (now()) COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色表';

SET FOREIGN_KEY_CHECKS = 1;