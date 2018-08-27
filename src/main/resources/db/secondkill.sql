/*
 Navicat MySQL Data Transfer

 Source Server         : mysql_aliyun
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : 47.98.140.133:3306
 Source Schema         : secondkill

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 24/08/2018 15:22:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品的详情介绍',
  `goods_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品单价',
  `goods_stock` int(11) NULL DEFAULT 0 COMMENT '商品库存，-1代表没有限制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9223372036854775808 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'iPhone X', 'iPhone X', '../imgs/iphonex.png', '一部纪念苹果公司创建十周年的产品', 8654.00, 50);
INSERT INTO `goods` VALUES (2, 'iPhone 8', 'iPhone 8', '../imgs/iphone8.png', '苹果第八代手机', 7654.00, 100);
INSERT INTO `goods` VALUES (3, '小米 6', '小米 6', '../imgs/mi6.png', '小米第六代手机', 4320.00, 200);
INSERT INTO `goods` VALUES (4, '华为mate 10', '华为mate 10', '../imgs/meta10.png', '华为mate系列第10代手机', 5230.00, 350);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `receive_address_id` bigint(20) NULL DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品单价',
  `order_channel` tinyint(4) NULL DEFAULT 0 COMMENT '订单渠道，1：pc , 2：android , 3：ios',
  `order_status` tinyint(4) NULL DEFAULT 0 COMMENT '订单状态，0：新建未支付，1：已支付，2：已发货，3：已收货，4：已退款，5：已完成',
  `create_date` datetime NULL DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for second_kill_goods
-- ----------------------------
DROP TABLE IF EXISTS `second_kill_goods`;
CREATE TABLE `second_kill_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表ID',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `second_kill_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '秒杀价',
  `stock_count` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime NULL DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of second_kill_goods
-- ----------------------------
INSERT INTO `second_kill_goods` VALUES (1, 1, 6999.00, 50, '2018-08-24 09:54:59', '2018-08-24 15:55:05');
INSERT INTO `second_kill_goods` VALUES (2, 2, 5999.00, 50, '2018-08-24 09:54:59', '2018-08-24 15:55:05');
INSERT INTO `second_kill_goods` VALUES (3, 3, 3999.00, 100, '2018-08-24 15:00:34', '2018-08-24 22:00:38');
INSERT INTO `second_kill_goods` VALUES (4, 4, 4999.00, 200, '2018-08-24 15:01:08', '2018-08-24 21:01:10');

-- ----------------------------
-- Table structure for second_kill_order
-- ----------------------------
DROP TABLE IF EXISTS `second_kill_order`;
CREATE TABLE `second_kill_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for second_kill_user
-- ----------------------------
DROP TABLE IF EXISTS `second_kill_user`;
CREATE TABLE `second_kill_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户ID，手机号码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MD5(MD5 pass明文+固定salt)+salt',
  `salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `head_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_kill_user
-- ----------------------------
INSERT INTO `second_kill_user` VALUES (12345678910, 'admin', '49dd46970f58e1202a07d768b133962b', '1a2b3c4d', NULL, '2018-08-21 14:43:01', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
