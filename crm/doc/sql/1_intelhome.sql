/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost:3306
 Source Schema         : intelhome_crm

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 14/12/2019 10:07:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for burying_point
-- ----------------------------
DROP TABLE IF EXISTS `burying_point`;
CREATE TABLE `burying_point`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `product_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品ID',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道ID',
  `point_sequence` int(11) NOT NULL COMMENT '埋点序号',
  `point_content` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '埋点内容',
  `remote_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主机',
  `browsing_time` int(11) NULL DEFAULT NULL COMMENT '浏览时长',
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '埋点信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `remote_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主机',
  `created_date` datetime(0) NOT NULL COMMENT '创建时间',
  `request_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求地址',
  `user_agent` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `method` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法',
  `protocol` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议',
  `accept` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求头',
  `accept_language` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `accept_encoding` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品ID',
  `uid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户访问信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：1=有效，0=无效',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for member_order
-- ----------------------------
DROP TABLE IF EXISTS `member_order`;
CREATE TABLE `member_order`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `member_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预定单ID',
  `out_trade_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `money` decimal(5, 2) NOT NULL COMMENT '金额',
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：1=已经支付，0=预支付',
  `created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品种类',
  `style` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品风格',
  `material` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品材质',
  `color` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品颜色',
  `model` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品型号',
  `image` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品图片',
  `price` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品进价',
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：1=有效，0=无效',
  `desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('157340247842500000', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001438', '20191111001438');
INSERT INTO `product` VALUES ('157340247995300001', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001439', '20191111001439');
INSERT INTO `product` VALUES ('157340248095800002', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001440', '20191111001440');
INSERT INTO `product` VALUES ('157340248196200003', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001441', '20191111001441');
INSERT INTO `product` VALUES ('157340248296500004', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001442', '20191111001442');
INSERT INTO `product` VALUES ('157340248397000005', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001443', '20191111001443');
INSERT INTO `product` VALUES ('157340248497400006', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001444', '20191111001444');
INSERT INTO `product` VALUES ('157340248597800007', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001445', '20191111001445');
INSERT INTO `product` VALUES ('157340248698600008', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001446', '20191111001446');
INSERT INTO `product` VALUES ('157340248800900009', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001448', '20191111001448');
INSERT INTO `product` VALUES ('157340248901300010', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001449', '20191111001449');
INSERT INTO `product` VALUES ('157340249001700011', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001450', '20191111001450');
INSERT INTO `product` VALUES ('157340249102100012', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001451', '20191111001451');
INSERT INTO `product` VALUES ('157340249203400013', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001452', '20191111001452');
INSERT INTO `product` VALUES ('157340249303700014', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001453', '20191111001453');
INSERT INTO `product` VALUES ('157340249405200015', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001454', '20191111001454');
INSERT INTO `product` VALUES ('157340249505800016', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001455', '20191111001455');
INSERT INTO `product` VALUES ('157340249606100017', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001456', '20191111001456');
INSERT INTO `product` VALUES ('157340249706600018', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001457', '20191111001457');
INSERT INTO `product` VALUES ('157340249806800019', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001458', '20191111001458');
INSERT INTO `product` VALUES ('157340249907100020', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001459', '20191111001459');
INSERT INTO `product` VALUES ('157340250008500021', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001500', '20191111001500');
INSERT INTO `product` VALUES ('157340250108900022', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001501', '20191111001501');
INSERT INTO `product` VALUES ('157340250209300023', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001502', '20191111001502');
INSERT INTO `product` VALUES ('157340250309600024', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001503', '20191111001503');
INSERT INTO `product` VALUES ('157340250409800025', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001504', '20191111001504');
INSERT INTO `product` VALUES ('157340250510100026', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001505', '20191111001505');
INSERT INTO `product` VALUES ('157340250610500027', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001506', '20191111001506');
INSERT INTO `product` VALUES ('157340250710800028', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001507', '20191111001507');
INSERT INTO `product` VALUES ('157340250811000029', '白色雕花实木沙发', '实木沙发', '简约现代', '实木', '白色', 'DM2018101601', '/image/20131215', '3200.0', '1', '价格实惠', '20191111001508', '20191111001508');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型：1=管理员，0=普通用户',
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：1=启用，0=禁用',
  `desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('157278730363300000', 'admin', '3291f0cca73813c4a572470523b0a3d4', '1', '1', '1', '2019/11/03', '2019/11/03');
INSERT INTO `user` VALUES ('157344676292400000', 'xiaoming', '3291f0cca73813c4a572470523b0a3d4', '0', '1', '1', '2019/11/11', '2019/11/11');

-- ----------------------------
-- Table structure for user_product
-- ----------------------------
DROP TABLE IF EXISTS `user_product`;
CREATE TABLE `user_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道ID',
  `link_address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接地址',
  `created` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户产品表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
