/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : wx7b7aabca0fc1737d

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-06-29 22:21:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wx_uac_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_uac_user`;
CREATE TABLE `wx_uac_user` (
  `user_id` varchar(64) NOT NULL,
  `create_time` date NOT NULL COMMENT '注册时间',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `gender` smallint(1) DEFAULT NULL,
  `country` varchar(64) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `last_login_time` date DEFAULT NULL,
  `last_login_location` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for pc_omc_cart
-- ----------------------------
DROP TABLE IF EXISTS `pc_omc_cart`;
CREATE TABLE `pc_omc_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ----------------------------
-- Records of pc_omc_cart
-- ----------------------------

-- ----------------------------
-- Table structure for pc_omc_order
-- ----------------------------
DROP TABLE IF EXISTS `pc_omc_order`;
CREATE TABLE `pc_omc_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `order_no` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '订单号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `shipping_id` bigint(20) DEFAULT NULL COMMENT '收货人ID',
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) NOT NULL DEFAULT '1' COMMENT '支付类型,1-在线支付',
  `postage` int(10) DEFAULT '0' COMMENT '运费,单位是元',
  `status` int(10) DEFAULT '10' COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `creator` varchar(20) CHARACTER SET utf32 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Records of pc_omc_order
-- ----------------------------
INSERT INTO `pc_omc_order` VALUES ('1', '0', '1', '1', null, null, '1', '0', '10', null, null, null, null, '', null, '2019-07-07 18:52:08', '', null, '2019-07-07 18:52:08');
INSERT INTO `pc_omc_order` VALUES ('2', '0', '2', null, null, null, '1', '0', '20', null, null, null, null, '', null, '2019-07-07 18:52:08', '', null, '2019-07-07 18:52:08');
INSERT INTO `pc_omc_order` VALUES ('3', '0', '3', null, null, null, '1', '0', '30', null, null, null, null, '', null, '2019-07-07 18:52:08', '', null, '2019-07-07 18:52:08');
INSERT INTO `pc_omc_order` VALUES ('4', '0', '4', null, null, null, '1', '0', '40', null, null, null, null, '', null, '2019-07-07 18:52:08', '', null, '2019-07-07 18:52:08');
INSERT INTO `pc_omc_order` VALUES ('5', '0', '5', null, null, null, '1', '0', '10', null, null, null, null, '', null, '2019-07-07 18:52:08', '', null, '2019-07-07 18:52:08');

-- ----------------------------
-- Table structure for pc_omc_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `pc_omc_order_detail`;
CREATE TABLE `pc_omc_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `detail_no` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '订单明细序列号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `order_no` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '订单号',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `product_name` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '商品名称',
  `product_image` varchar(500) CHARACTER SET utf8 DEFAULT '' COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- ----------------------------
-- Records of pc_omc_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for pc_omc_shipping
-- ----------------------------
DROP TABLE IF EXISTS `pc_omc_shipping`;
CREATE TABLE `pc_omc_shipping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '收货姓名',
  `receiver_phone_no` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '收货固定电话',
  `receiver_mobile_no` varchar(11) CHARACTER SET utf8 DEFAULT '' COMMENT '收货移动电话',
  `province_id` bigint(32) DEFAULT NULL COMMENT '收货人省ID',
  `province_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '省份',
  `city_id` bigint(20) DEFAULT NULL COMMENT '收货人城市ID',
  `city_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '收货人城市名称',
  `district_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '区/县',
  `district_id` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '区/县 编码',
  `street_id` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '街道ID',
  `street_name` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '接到名称',
  `detail_address` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '详细地址',
  `receiver_zip_code` varchar(6) CHARACTER SET utf8 DEFAULT '' COMMENT '邮编',
  `default_address` int(1) DEFAULT '0' COMMENT '默认地址',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='收货人信息表';

-- ----------------------------
-- Records of pc_omc_shipping
-- ----------------------------
INSERT INTO `pc_omc_shipping` VALUES ('4', '0', '1', 'jack', '13800138000', '18688888888', null, '北京', null, '北京市', '海淀区', '', '', '西二街', '中关村', '100000', '0', 'admin', '1', '2017-07-12 14:01:35', 'admin', '1', '2017-07-12 14:01:35');
INSERT INTO `pc_omc_shipping` VALUES ('7', '0', '1', 'Rosen', '13800138000', '13800138000', null, '北京', null, '北京', null, '', '', '东二街', '中关村', '100000', '1', 'admin', '1', '2017-07-12 14:01:35', 'admin', '1', '2017-07-12 14:01:35');
INSERT INTO `pc_omc_shipping` VALUES ('29', '0', '1', '吉利', '13800138000', '13800138000', null, '北京', null, '北京', '海淀区', '', '', '背二街', '海淀区中关村', '100000', '1', 'admin', '1', '2017-07-12 14:01:35', 'admin', '1', '2017-07-12 14:01:35');

-- ----------------------------
-- Table structure for pc_ptc_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `pc_ptc_pay_info`;
CREATE TABLE `pc_ptc_pay_info` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `order_no` varchar(50) DEFAULT '' COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) DEFAULT '' COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT '' COMMENT '支付宝支付状态',
  `creator` varchar(20) DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(32) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付表';
