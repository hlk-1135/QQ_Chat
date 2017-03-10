/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50629
Source Host           : localhost:3306
Source Database       : qq_program

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2017-01-08 14:38:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `realname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `sex` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1004', '111111', '史坤', '男', '17862821585');
INSERT INTO `user` VALUES ('2', '1042', '111111', '何立意', '女', '15552201622');
INSERT INTO `user` VALUES ('3', '1028', '123456', '逯其鲁', '男', '17862821853');
INSERT INTO `user` VALUES ('4', '1022', '406406', '李冬杰', '男', '17862821996');
INSERT INTO `user` VALUES ('5', '1035', '123123', '张岳强', '男', '17862824448');
INSERT INTO `user` VALUES ('6', '1041', '14031403', '刘雪丽', '女', '17862821589');
INSERT INTO `user` VALUES ('7', '1043', '19961102', '曹恒阳', '男', '15553624856');
INSERT INTO `user` VALUES ('8', '1038', '19950323', '张浩瑞', '女', '17862821758');
