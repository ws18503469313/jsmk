/*
Navicat MySQL Data Transfer

Source Server         : myserver
Source Server Version : 50726
Source Host           : 123.206.14.25:3306
Source Database       : jshm

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-12-03 19:39:52
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='帖子分类';

-- ----------------------------
-- Records of ss_categray
-- ----------------------------
INSERT INTO `ss_categray` VALUES ('190125AZ6DTHNPDP', '2222', '2019-01-25 15:22:27.844000', '1');
INSERT INTO `ss_categray` VALUES ('190125GG4WFPD30H', 'w dwad ', '2019-01-25 21:46:36.635000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG57TXMWM8', 'dw ada d', '2019-01-25 21:46:39.238000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG5W4KFG54', '啊阿文的', '2019-01-25 21:46:42.986000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG66Y9BGHH', '达瓦分发vv', '2019-01-25 21:46:45.462000', '0');
INSERT INTO `ss_categray` VALUES ('190125GG6SNCFXYW', '二分额分我w', '2019-01-25 21:46:49.082000', '0');
INSERT INTO `ss_categray` VALUES ('19081585G1KC88DP', 'vue添加的categray', '2019-08-15 11:29:11.239000', '0');
INSERT INTO `ss_categray` VALUES ('19081585NB9H1G2W', 'vue', '2019-08-15 11:29:39.207000', '0');
INSERT INTO `ss_categray` VALUES ('19081585WWYFWY3C', 'vvv', '2019-08-15 11:30:14.504000', '0');
INSERT INTO `ss_categray` VALUES ('1908159YAZ192A80', '123', '2019-08-15 13:55:52.263000', '0');
INSERT INTO `ss_categray` VALUES ('191127BMCNXGPH94', '天天2', '2019-11-27 16:20:10.693000', '0');

-- ----------------------------
-- Table structure for ss_collection
-- ----------------------------
DROP TABLE IF EXISTS `ss_collection`;
CREATE TABLE `ss_collection` (
  `id` char(16) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `user_id` char(16) COLLATE utf8_bin NOT NULL COMMENT '用户Id',
  `note_id` char(16) COLLATE utf8_bin NOT NULL COMMENT '帖子id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户收藏帖子表';

-- ----------------------------
-- Records of ss_collection
-- ----------------------------
INSERT INTO `ss_collection` VALUES ('1', '1', '1');

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
  `visit_num` bigint(20) NOT NULL DEFAULT '1' COMMENT '访问量',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='帖子';

-- ----------------------------
-- Records of ss_note
-- ----------------------------
INSERT INTO `ss_note` VALUES ('1', '190125AZ6DTHNPDP', '1ad ad', '2019-01-09 18:01:23.000000', '0', '1', '1200', '0');
INSERT INTO `ss_note` VALUES ('2', '190125AZ6DTHNPDP', '1ad ad', '2019-01-09 18:01:23.000000', '0', '1', '2200', '0');

-- ----------------------------
-- Table structure for ss_note_detail
-- ----------------------------
DROP TABLE IF EXISTS `ss_note_detail`;
CREATE TABLE `ss_note_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `note_id` char(16) COLLATE utf8_bin NOT NULL,
  `content` varchar(255) COLLATE utf8_bin NOT NULL,
  `sort` int(5) DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='帖子详情';

-- ----------------------------
-- Records of ss_note_detail
-- ----------------------------
INSERT INTO `ss_note_detail` VALUES ('1', '1', '这是手动再数据库添加的第一条noteDetail', '1', '0');
INSERT INTO `ss_note_detail` VALUES ('2', '1', 'http://127.0.0.1/index/39271029-bd90-424e-861e-ce30c991d148.png', '2', '1');
INSERT INTO `ss_note_detail` VALUES ('3', '1', 'http://127.0.0.1/video/8b40143e3d3c594dd7c01eeef42082df.mp4', '3', '2');

-- ----------------------------
-- Table structure for ss_wxresult
-- ----------------------------
DROP TABLE IF EXISTS `ss_wxresult`;
CREATE TABLE `ss_wxresult` (
  `user_id` char(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'sys_user的id,和用户微信绑定的id',
  `session_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '从微信服务器获取的session_key',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '从微信服务器获取的openId',
  `third_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '保存在客户端的登陆凭证',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='系统用户和微信用户关系表';

-- ----------------------------
-- Records of ss_wxresult
-- ----------------------------
INSERT INTO `ss_wxresult` VALUES ('1', '6mEnwx4lDE+UZUj8XqMeRQ==', 'oYqzm5TQOoLAOMO0miYzVQ3MwqyA', '3a3c6ca24f6e565c26100b01b5c3fc91');

-- ----------------------------
-- Table structure for sys_access
-- ----------------------------
DROP TABLE IF EXISTS `sys_access`;
CREATE TABLE `sys_access` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `url` varchar(50) COLLATE utf8_bin NOT NULL,
  `title` varchar(50) COLLATE utf8_bin NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `parent` char(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限';

-- ----------------------------
-- Records of sys_access
-- ----------------------------
INSERT INTO `sys_access` VALUES ('19013190FC7RAFW0', '#', '修CESHI_forth_sssssssssssssssssss', '2019-01-31 12:38:10.704000', '#');
INSERT INTO `sys_access` VALUES ('19013190M7FA8568', '/www/adw', '1111/12dd', '2019-01-31 12:38:35.345000', '19013190HCB8SC6W');
INSERT INTO `sys_access` VALUES ('190131A82GT5HWDP', 'test', '测试', '2019-01-31 14:24:59.714000', '19013190FC7RAFW0');
INSERT INTO `sys_access` VALUES ('190131A9DNSMKBTC', '#', 'yyyyyjjjjj', '2019-01-31 14:29:11.553000', '#');
INSERT INTO `sys_access` VALUES ('190131A9X2B6CARP', '22222222222222222222', '22222222222222', '2019-01-31 14:30:24.759000', '190131A9DNSMKBTC');
INSERT INTO `sys_access` VALUES ('190131B25B3MFMRP', 'aaaaaaaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaaaaa', '2019-01-31 15:31:21.239000', '190131A9DNSMKBTC');
INSERT INTO `sys_access` VALUES ('190212AHGNG2SWM8', '#', '以及惨淡', '2019-02-12 14:50:25.409000', '#');
INSERT INTO `sys_access` VALUES ('190212AHMP04K39P', '一级菜单的额日记擦你我', '二级菜单', '2019-02-12 14:50:44.817000', '190212AHGNG2SWM8');
INSERT INTO `sys_access` VALUES ('190219B2WD02S51P', '#', '帖子管理', '2019-02-19 15:33:23.913000', '#');
INSERT INTO `sys_access` VALUES ('190219B310X7D0PH', 'html/main.html', '测试1', '2019-02-19 15:33:53.301000', '190219B2WD02S51P');
INSERT INTO `sys_access` VALUES ('190219B37RFXA70H', 'html/categray/categray.html', '菜单管理', '2019-02-19 15:34:36.645000', '190219B2WD02S51P');
INSERT INTO `sys_access` VALUES ('190219B3A9SMB25P', 'html/note/note.html', '发帖', '2019-02-19 15:34:53.253000', '190219B2WD02S51P');
INSERT INTO `sys_access` VALUES ('190219B3F0AKPS5P', 'main/index', '超链接', '2019-02-19 15:35:16.822000', '190219B2WD02S51P');
INSERT INTO `sys_access` VALUES ('190219B3GZ9FRP00', '#', '系统管理', '2019-02-19 15:35:29.451000', '#');
INSERT INTO `sys_access` VALUES ('190219B3N4KDGGXP', 'html/sys/access.html', '系统权限', '2019-02-19 15:35:49.967000', '190219B3GZ9FRP00');
INSERT INTO `sys_access` VALUES ('190219B3T8HB9YFW', 'html/sys/role.html', '角色管理', '2019-02-19 15:36:16.613000', '190219B3GZ9FRP00');
INSERT INTO `sys_access` VALUES ('1902257SS10DDD40', 'html/sys/user.html', '用户管理', '2019-02-25 10:56:54.488000', '190219B3GZ9FRP00');
INSERT INTO `sys_access` VALUES ('1903017SHA9SHH94', '123/123', 'test_4', '2019-03-01 10:56:18.028000', '190225BAMW2KCYRP');
INSERT INTO `sys_access` VALUES ('190531BYWPS6Z25P', '#', '靳慧同学专属', '2019-05-31 16:45:29.303000', '#');
INSERT INTO `sys_access` VALUES ('190531C4A33PTHX4', 'html/sys/bloodFetch.html', '采血点', '2019-05-31 17:01:56.070000', '190531BYWPS6Z25P');
INSERT INTO `sys_access` VALUES ('190731BZT8WDB168', '#', '用户管理', '2019-07-31 16:48:20.281000', '#');
INSERT INTO `sys_access` VALUES ('190731C0XSYGWXS8', 'user.add', '添加用户', '2019-07-31 16:51:36.533000', '1902257SS10DDD40');
INSERT INTO `sys_access` VALUES ('191127BMKX48WG0H', '#1', '2', '2019-11-27 16:20:44.068000', '1902257SS10DDD40');

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` char(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'name',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限代码',
  `module` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块名',
  `seq` int(10) DEFAULT NULL COMMENT '顺序',
  `access_id` char(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '对应的父菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('1', '用户添加', 'user.add', '用户', '1', '1902257SS10DDD40');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` char(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `rolename` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `descprit` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'manager', 'rolename4个字节不够用');
INSERT INTO `sys_role` VALUES ('190218AM4GPKA70H', 'admin', 'aaaa');
INSERT INTO `sys_role` VALUES ('190218AMCK2T5ZF8', 'user_update', 'user_update_desc');
INSERT INTO `sys_role` VALUES ('190218AMZM2DX8X4', 'company', 'company_Desc');
INSERT INTO `sys_role` VALUES ('190218B97DYK4Y3C', 'test', 'test_desc');
INSERT INTO `sys_role` VALUES ('1902256X2MD4FPPH', 'model_manager', 'model_manager_desc');
INSERT INTO `sys_role` VALUES ('190225709P6N3BMW', 'access_manager', 'access_manager_desc');
INSERT INTO `sys_role` VALUES ('19022570GZ09DWX4', 'role_manager', 'role_manager_desc');
INSERT INTO `sys_role` VALUES ('1909029FF12XF4PH', 'ttt', '123');
INSERT INTO `sys_role` VALUES ('1909029G8TWWPC28', 'ttt', '123');

-- ----------------------------
-- Table structure for sys_role_access
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_access`;
CREATE TABLE `sys_role_access` (
  `id` char(16) COLLATE utf8_bin NOT NULL,
  `role_id` char(16) COLLATE utf8_bin NOT NULL,
  `access_id` char(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色--权限';

-- ----------------------------
-- Records of sys_role_access
-- ----------------------------
INSERT INTO `sys_role_access` VALUES ('1902199RM5A9AZXP', '190218AM4GPKA70H', '19013190FC7RAFW0');
INSERT INTO `sys_role_access` VALUES ('1902199RM5B8N7C0', '190218AM4GPKA70H', '190131A82GT5HWDP');
INSERT INTO `sys_role_access` VALUES ('1902199RM5BGF98H', '190218AM4GPKA70H', '190131A9DNSMKBTC');
INSERT INTO `sys_role_access` VALUES ('1902199RM5BS9B54', '190218AM4GPKA70H', '190131A9X2B6CARP');
INSERT INTO `sys_role_access` VALUES ('1902199RM5CFSHSW', '190218AM4GPKA70H', '190212AHGNG2SWM8');
INSERT INTO `sys_role_access` VALUES ('1902199RM5CX15KP', '190218AM4GPKA70H', '190212AHMP04K39P');
INSERT INTO `sys_role_access` VALUES ('190219B3X1XZK2A8', '1', '190219B3GZ9FRP00');
INSERT INTO `sys_role_access` VALUES ('190219B3X1Y6C46W', '1', '190219B3N4KDGGXP');
INSERT INTO `sys_role_access` VALUES ('190219B3X1Y9TN54', '1', '190219B3T8HB9YFW');
INSERT INTO `sys_role_access` VALUES ('190219B3Y893A2CH', '190218AM4GPKA70H', '190219B2WD02S51P');
INSERT INTO `sys_role_access` VALUES ('190219B3Y89Z6T0H', '190218AM4GPKA70H', '190219B310X7D0PH');
INSERT INTO `sys_role_access` VALUES ('190219B3Y8A9DBTC', '190218AM4GPKA70H', '190219B37RFXA70H');
INSERT INTO `sys_role_access` VALUES ('190219B3Y8ACWYRP', '190218AM4GPKA70H', '190219B3A9SMB25P');
INSERT INTO `sys_role_access` VALUES ('190219B3Y8ANP0M8', '190218AM4GPKA70H', '190219B3F0AKPS5P');
INSERT INTO `sys_role_access` VALUES ('190219B3Y8AYG2FW', '190218AM4GPKA70H', '190219B3GZ9FRP00');
INSERT INTO `sys_role_access` VALUES ('190219B3Y8B5A4BC', '190218AM4GPKA70H', '190219B3N4KDGGXP');
INSERT INTO `sys_role_access` VALUES ('190219B3Y8BC5680', '190218AM4GPKA70H', '190219B3T8HB9YFW');
INSERT INTO `sys_role_access` VALUES ('1902257SW30S7B0H', '1', '1902257SS10DDD40');
INSERT INTO `sys_role_access` VALUES ('1902289XA8Y869KP', '190218AM4GPKA70H', '1902257SS10DDD40');
INSERT INTO `sys_role_access` VALUES ('190228B2GWAMRX68', '19022570GZ09DWX4', '190212AHGNG2SWM8');
INSERT INTO `sys_role_access` VALUES ('190228B2GWBB72W0', '19022570GZ09DWX4', '190212AHMP04K39P');
INSERT INTO `sys_role_access` VALUES ('190228B2K1DW0FA8', '190218AMZM2DX8X4', '190131A9DNSMKBTC');
INSERT INTO `sys_role_access` VALUES ('190228B2K1FHDP00', '190218AMZM2DX8X4', '190131A9X2B6CARP');
INSERT INTO `sys_role_access` VALUES ('190228B2K1FNX6Y8', '190218AMZM2DX8X4', '190131B25B3MFMRP');
INSERT INTO `sys_role_access` VALUES ('190228B2K1FYP8SW', '190218AMZM2DX8X4', '190219B3GZ9FRP00');
INSERT INTO `sys_role_access` VALUES ('190228B2K1G5GANC', '190218AMZM2DX8X4', '190219B3N4KDGGXP');
INSERT INTO `sys_role_access` VALUES ('190531C4B4B9A32W', '1', '190531BYWPS6Z25P');
INSERT INTO `sys_role_access` VALUES ('190531C4B4DFRP00', '1', '190531C4A33PTHX4');

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `id` bigint(20) NOT NULL,
  `role_id` char(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `authority_id` char(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_role_authority
-- ----------------------------
INSERT INTO `sys_role_authority` VALUES ('1', '1', '1');

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
  `sex` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`),
  UNIQUE KEY `username_3` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '6512bd43d9caa6e02c990b0a82652dca', 'polunzi', '10638', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('190221B17MKB084H', '123', 'f5bb0c8de146c67b44babbf4e6584cc0', '123', null, null, '2019-02-21 15:28:35.676000', null, '1');
INSERT INTO `sys_user` VALUES ('190221BPC5PS9CDP', '123123', '101193d7181cc88340ae5b2b17bba8a1\r\n', '123', null, null, '2019-02-21 16:26:07.737000', null, '2');
INSERT INTO `sys_user` VALUES ('190731C2RB07TK68', 'test_1', 'fced79a42c92905088dea30397cbc650', 'test_1', null, null, '2019-07-31 16:57:08.353000', null, '2');
INSERT INTO `sys_user` VALUES ('190731C3ZWKTWBC0', 'test_2', 'b82bf4f11c20779ff66a4fe55f56ba98', 'TEST-2', null, null, '2019-07-31 17:00:49.782000', null, '1');
INSERT INTO `sys_user` VALUES ('190731C4PYAX2BHH', 'test_3', '54c62ed92e968f8d370ebfb244b48205', 'test_3', null, null, '2019-07-31 17:03:05.753000', null, '2');
INSERT INTO `sys_user` VALUES ('190731C538YTHZR4', 'tt_1', '30fa724ea64c79107f6fdb9ba19dd640', 'tt_1', null, null, '2019-07-31 17:04:12.521000', null, '0');
INSERT INTO `sys_user` VALUES ('190731C5SGDBS8M8', 'lrh', 'b5d01bbcab8b4759f0774427ff6f56f0', '123', null, null, '2019-07-31 17:06:16.264000', null, '0');
INSERT INTO `sys_user` VALUES ('190731C6YDYN1XWH', 'tt3', 'e600816239db21c7e94b90f8678a1abd', 'tt3', null, null, '2019-07-31 17:09:41.799000', null, '0');
INSERT INTO `sys_user` VALUES ('190731C8G55FW3XP', 'ttt4', '7a24752e81c19ee1679bee7c802136d1', 'tt4', null, null, '2019-07-31 17:14:29.314000', null, '0');
INSERT INTO `sys_user` VALUES ('190731C8X135D968', 'tt5', 'a559e1e8f48f89e8f4dc7a314bb0992a', 'tt5', null, null, '2019-07-31 17:15:32.715000', null, '0');
INSERT INTO `sys_user` VALUES ('190731C9CS840NHH', 'tt6', 'bf2e3936a326a89546e9687ac5b24900', 'tt6', null, null, '2019-07-31 17:17:14.090000', null, null);
INSERT INTO `sys_user` VALUES ('190731CCRZGBCNHH', 'tt9', 'a9cb16bc4e956f0b0dde44b9b422c68f', '123', null, null, '2019-07-31 17:27:13.654000', null, null);
INSERT INTO `sys_user` VALUES ('190731CD2WP4CT54', 'aa1', '0cf729317cc3d3f936dc557d3ddaf1bd', 'aa1', null, null, '2019-07-31 17:28:10.909000', null, null);
INSERT INTO `sys_user` VALUES ('190731CDN5SPA8M8', 'aa2', 'bb344e39658e9e1a1be1c4eab31e5a0b', 'aa2', null, null, '2019-07-31 17:29:55.941000', null, null);
INSERT INTO `sys_user` VALUES ('190731CFTX5DWFNC', 'aa4', '8698fc4d3cf1d2c692fc3408e918aa3d', 'aa4', null, null, '2019-07-31 17:33:26.281000', null, null);
INSERT INTO `sys_user` VALUES ('190731CH8CFRB6CH', 'aa6', '9d6f02b37b8e64b25dda70b263bef3ac', 'aa6', null, null, '2019-07-31 17:37:47.161000', null, null);
INSERT INTO `sys_user` VALUES ('190731CHFMAD11P0', 'AA6', 'a9b02d04950865f47cdbf8b320f05c9b', 'AA6', null, null, '2019-07-31 17:38:27.108000', null, null);
INSERT INTO `sys_user` VALUES ('190731CHPX21BCZC', 'AA7', '779f4390e88b0600427918a943646f54', 'AA7', null, null, '2019-07-31 17:39:07.251000', null, null);
INSERT INTO `sys_user` VALUES ('190731CHY8D2ZT2W', 'LRH', '975ea8df0f671b0d71a403ba57966063', 'LRH', null, null, '2019-07-31 17:39:42.039000', null, null);
INSERT INTO `sys_user` VALUES ('190731CMZNPHCWDP', 'aa11', '55a1ce0870d172c2cf810e010cebd20e', 'aa11', null, null, '2019-07-31 17:45:51.362000', null, null);
INSERT INTO `sys_user` VALUES ('190731CNGR73M0DP', 'a12', 'b2549e82ac0f776908baf4c4a723c116', 'a12', null, null, '2019-07-31 17:47:34.654000', null, null);
INSERT INTO `sys_user` VALUES ('2', '2', '3c59dc048e8850243be8079a5c74d079', null, '1', null, null, null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `user_id` char(16) COLLATE utf8_bin NOT NULL,
  `role_id` char(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户----角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('3', '190221B17MKB084H', '190218AM4GPKA70H');
INSERT INTO `sys_user_role` VALUES ('4', '2', '1');
INSERT INTO `sys_user_role` VALUES ('5', '190221BPC5PS9CDP', '190218AM4GPKA70H');


alter table ss_note add `liked` int (10) default 0;