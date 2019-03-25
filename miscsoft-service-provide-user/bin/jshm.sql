/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : jshm

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-01-26 18:36:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_categray
-- ----------------------------
DROP TABLE IF EXISTS `ss_categray`;
CREATE TABLE `ss_categray` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ss_categray
-- ----------------------------
INSERT INTO `ss_categray` VALUES ('190125AZ6DTHNPDP', '2222', '2019-01-25 15:22:27.844000', '1');
INSERT INTO `ss_categray` VALUES ('190125AZ6XW0ZSCH', '32333', '2019-01-25 15:22:30.000000', '0');
INSERT INTO `ss_categray` VALUES ('190125B54G74CRWH', '终于是成功了', '2019-01-25 15:40:16.000000', '0');
INSERT INTO `ss_categray` VALUES ('190125B5GCA9HBC0', '啊哇大啊', '2019-01-25 15:41:26.000000', '0');
INSERT INTO `ss_categray` VALUES ('190125B8HXCKNYNC', '爱的哇的', '2019-01-25 15:50:36.201000', '0');
INSERT INTO `ss_categray` VALUES ('190125B8W9HMKFCH', '啊啊啊啊啊', '2019-01-25 15:51:24.029000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG4KB09MA8', 'a a ', '2019-01-25 21:46:34.996000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG4WFPD30H', 'w dwad ', '2019-01-25 21:46:36.635000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG57TXMWM8', 'dw ada d', '2019-01-25 21:46:39.238000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG5W4KFG54', '啊阿文的', '2019-01-25 21:46:42.986000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG66Y9BGHH', '达瓦分发vv', '2019-01-25 21:46:45.462000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG6SNCFXYW', '二分额分我w', '2019-01-25 21:46:49.082000', '0');

-- ----------------------------
-- Table structure for ss_note
-- ----------------------------
DROP TABLE IF EXISTS `ss_note`;
CREATE TABLE `ss_note` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `categray_id` char(16) COLLATE utf8_bin NOT NULL,
  `name` varchar(10) COLLATE utf8_bin NOT NULL,
  `publish_time` datetime(6) DEFAULT NULL,
  `shutdown` tinyint(1) NOT NULL DEFAULT '0',
  `like` int(5) NOT NULL DEFAULT '1',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ss_note
-- ----------------------------
INSERT INTO `ss_note` VALUES ('1', '190125AZ6DTHNPDP', '1ad ad', '2019-01-09 18:01:23.000000', '0', '1', '0');

-- ----------------------------
-- Table structure for ss_note_detail
-- ----------------------------
DROP TABLE IF EXISTS `ss_note_detail`;
CREATE TABLE `ss_note_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `note_id` char(16) COLLATE utf8_bin NOT NULL,
  `content` varchar(20) COLLATE utf8_bin NOT NULL,
  `sort` int(5) DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ss_note_detail
-- ----------------------------

-- ----------------------------
-- Table structure for sys_access
-- ----------------------------
DROP TABLE IF EXISTS `sys_access`;
CREATE TABLE `sys_access` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `url` varchar(10) COLLATE utf8_bin NOT NULL,
  `title` varchar(10) COLLATE utf8_bin NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `type` char(4) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_access
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` char(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rolename` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `descprit` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'manager', 'rolename4个字节不够用');

-- ----------------------------
-- Table structure for sys_role_access
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_access`;
CREATE TABLE `sys_role_access` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `role_id` char(16) COLLATE utf8_bin NOT NULL,
  `access_id` char(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_role_access
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `age` int(5) DEFAULT NULL,
  `balance` double(10,0) DEFAULT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `descript` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '6512bd43d9caa6e02c990b0a82652dca', null, '1', null, null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `user_id` char(16) COLLATE utf8_bin NOT NULL,
  `role_id` char(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
