/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.9-log : Database - member
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`intelhome_crm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `intelhome_crm`;

/*Table structure for table `info` */

DROP TABLE IF EXISTS `info`;

CREATE TABLE `info` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `remote_addr` varchar(32) NOT NULL COMMENT '主机',
  `created_date` datetime NOT NULL COMMENT '创建时间',
  `request_uri` varchar(256) NOT NULL COMMENT '请求地址',
  `user_agent` varchar(512) DEFAULT NULL COMMENT '用户代理',
  `method` varchar(32) DEFAULT NULL COMMENT '方法',
  `protocol` varchar(256) DEFAULT NULL COMMENT '协议',
  `accept` varchar(256) DEFAULT NULL COMMENT '请求头',
  `accept_language` varchar(32) DEFAULT NULL COMMENT '语言',
  `accept_encoding` varchar(32) DEFAULT NULL COMMENT '编码',
  `pid` varchar(64) NOT NULL COMMENT '产品ID',
  `uid` varchar(64) NOT NULL COMMENT '渠道ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户访问信息表';

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `user_id` varchar(64) NOT NULL COMMENT '渠道ID',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机',
  `status` varchar(16) DEFAULT NULL COMMENT '状态：1=有效，0=无效',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `desc` varchar(128) DEFAULT NULL COMMENT '备注',
  `created` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `pid` varchar(64) DEFAULT NULL COMMENT '产品ID',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='会员表';

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `name` varchar(64) DEFAULT NULL COMMENT '产品名称',
  `category` varchar(64) DEFAULT NULL COMMENT '产品种类',
  `style` varchar(64) DEFAULT NULL COMMENT '产品风格',
  `material` varchar(64) DEFAULT NULL COMMENT '产品材质',
  `color` varchar(64) DEFAULT NULL COMMENT '产品颜色',
  `model` varchar(64) DEFAULT NULL COMMENT '产品型号',
  `image` varchar(128) DEFAULT NULL COMMENT '产品图片',
  `price` varchar(128) DEFAULT NULL COMMENT '产品进价',
  `status` varchar(16) DEFAULT NULL COMMENT '状态：1=有效，0=无效',
  `desc` varchar(128) DEFAULT NULL COMMENT '备注',
  `created` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='产品表';

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `type` varchar(16) DEFAULT NULL COMMENT '类型：1=管理员，0=普通用户',
  `status` varchar(16) DEFAULT NULL COMMENT '状态：1=启用，0=禁用',
  `desc` varchar(128) DEFAULT NULL COMMENT '备注',
  `created` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Table structure for table `user_product` */

DROP TABLE IF EXISTS `user_product`;

CREATE TABLE `user_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` varchar(64) DEFAULT NULL COMMENT '产品ID',
  `user_id` varchar(64) NOT NULL COMMENT '渠道ID',
  `link_address` varchar(256) NOT NULL COMMENT '链接地址',
  `created` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='用户产品表';

/*Table structure for table `burying_point` */

DROP TABLE IF EXISTS `burying_point`;

CREATE TABLE `burying_point` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `product_id` varchar(64) NOT NULL COMMENT '产品ID',
  `user_id` varchar(64) NOT NULL COMMENT '渠道ID',
  `point_sequence` INT(11) NOT NULL COMMENT '埋点序号',
  `point_content` varchar(256)  NULL COMMENT '埋点内容',
  `remote_addr` varchar(32) NOT NULL COMMENT '主机',
  `browsing_time` INT(11) DEFAULT NULL COMMENT '浏览时长',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `created` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='埋点信息表';

/*Table structure for table `member_order` */

DROP TABLE IF EXISTS `member_order`;

CREATE TABLE `member_order` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `member_id` varchar(64) NOT NULL COMMENT '预定单ID',
  `out_trade_no` varchar(32) NOT NULL COMMENT '订单号',
  `money` DECIMAL(5,2) NOT NULL COMMENT '金额',
  `status` varchar(16) DEFAULT NULL COMMENT '状态：1=已经支付，0=预支付',
  `created` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `updated` varchar(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='订单信息表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
