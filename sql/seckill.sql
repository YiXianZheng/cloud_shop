/*
 Navicat Premium Data Transfer

 Source Server         : 蜗小牛
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : 106.13.234.26:3307
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 23/08/2021 22:55:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for BI_DDXX_T_J
-- ----------------------------
DROP TABLE IF EXISTS `BI_DDXX_T_J`;
CREATE TABLE `BI_DDXX_T_J`  (
  `ID0000` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AAB301` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '行政区划代码',
  `AAE036` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单日期',
  `AAB001` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台企业服务单位编号',
  `AAB004` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '平台企业单位名称',
  `XZDDL0` int(11) NULL DEFAULT NULL COMMENT '新增订单量',
  `GLSGBA` int(11) NULL DEFAULT NULL COMMENT '关联事故备案量',
  `SJGXSJ` datetime NULL DEFAULT NULL COMMENT '数据更新时间',
  `CJSJ00` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID0000`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of BI_DDXX_T_J
-- ----------------------------
INSERT INTO `BI_DDXX_T_J` VALUES (1, '350000', '20210812', '350000', '测试平台', 103147, 2, '2021-08-12 10:55:13', '2021-08-12 10:55:18');
INSERT INTO `BI_DDXX_T_J` VALUES (2, '350201', '20210812', '350001', '测试平台1', 10314, 2, '2021-08-12 10:55:13', '2021-08-12 10:55:18');
INSERT INTO `BI_DDXX_T_J` VALUES (3, '350106', '20210812', '350002', '测试平台2', 13147, 1, '2021-08-12 10:55:13', '2021-08-12 10:55:18');
INSERT INTO `BI_DDXX_T_J` VALUES (4, '000000', '20210812', '350003', '美团', 103147, 2, '2021-08-12 10:55:13', '2021-08-12 10:55:18');
INSERT INTO `BI_DDXX_T_J` VALUES (5, '350002', '20210812', '350004', '饿了么', 103147, 2, '2021-08-12 10:55:13', '2021-08-12 10:55:18');
INSERT INTO `BI_DDXX_T_J` VALUES (6, '350003', '20210812', '350005', '货拉拉', 103147, 2, '2021-08-12 10:55:13', '2021-08-12 10:55:18');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `createtime` datetime NULL DEFAULT NULL COMMENT '下单时间',
  `createor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (93, 7, '2021-08-20 20:03:04', '郑益贤012a', '0');
INSERT INTO `orders` VALUES (94, 7, '2021-08-20 20:03:04', '郑益贤f1ef', '0');
INSERT INTO `orders` VALUES (95, 7, '2021-08-20 20:03:04', '郑益贤f0a5', '0');
INSERT INTO `orders` VALUES (96, 7, '2021-08-20 20:03:04', '郑益贤a481', '0');
INSERT INTO `orders` VALUES (97, 7, '2021-08-20 20:03:04', '郑益贤87ad', '0');
INSERT INTO `orders` VALUES (98, 7, '2021-08-20 20:03:04', '郑益贤6b60', '0');
INSERT INTO `orders` VALUES (99, 7, '2021-08-20 20:03:04', '郑益贤95d9', '0');
INSERT INTO `orders` VALUES (100, 7, '2021-08-20 20:03:05', '郑益贤6497', '0');
INSERT INTO `orders` VALUES (101, 7, '2021-08-20 20:03:05', '郑益贤f4b7', '0');
INSERT INTO `orders` VALUES (102, 7, '2021-08-20 20:03:05', '郑益贤259c', '0');
INSERT INTO `orders` VALUES (103, 7, '2021-08-20 20:03:07', '郑益贤2b76', '0');
INSERT INTO `orders` VALUES (104, 7, '2021-08-20 20:03:07', '郑益贤0dbf', '0');
INSERT INTO `orders` VALUES (105, 7, '2021-08-20 20:03:08', '郑益贤55c3', '0');
INSERT INTO `orders` VALUES (106, 7, '2021-08-20 20:03:08', '郑益贤781d', '0');
INSERT INTO `orders` VALUES (107, 7, '2021-08-20 20:03:08', '郑益贤92e5', '0');
INSERT INTO `orders` VALUES (108, 7, '2021-08-20 20:03:09', '郑益贤bff7', '0');
INSERT INTO `orders` VALUES (109, 7, '2021-08-20 20:03:10', '郑益贤917f', '0');
INSERT INTO `orders` VALUES (110, 7, '2021-08-20 20:03:10', '郑益贤1028', '0');
INSERT INTO `orders` VALUES (111, 7, '2021-08-20 20:03:10', '郑益贤7c08', '0');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (7, 'iPhone XS', '2020', 18688.00, '2');

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES (6, 7, 0);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
