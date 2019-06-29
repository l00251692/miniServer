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
