/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : share_mate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 09:36:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `title`
-- ----------------------------
DROP TABLE IF EXISTS `title`;
CREATE TABLE `title` (
  `user_id` int(6) NOT NULL AUTO_INCREMENT,
  `type_id` int(5) NOT NULL,
  PRIMARY KEY (`user_id`,`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of title
-- ----------------------------
INSERT INTO `title` VALUES ('1', '1');
INSERT INTO `title` VALUES ('1', '2');
INSERT INTO `title` VALUES ('2', '3');
INSERT INTO `title` VALUES ('2', '4');
INSERT INTO `title` VALUES ('3', '5');
INSERT INTO `title` VALUES ('3', '6');
INSERT INTO `title` VALUES ('4', '1');
INSERT INTO `title` VALUES ('4', '3');
INSERT INTO `title` VALUES ('5', '4');
INSERT INTO `title` VALUES ('5', '6');
INSERT INTO `title` VALUES ('6', '2');
INSERT INTO `title` VALUES ('6', '3');
INSERT INTO `title` VALUES ('7', '1');
INSERT INTO `title` VALUES ('7', '2');
INSERT INTO `title` VALUES ('8', '5');
INSERT INTO `title` VALUES ('9', '3');
INSERT INTO `title` VALUES ('9', '4');
INSERT INTO `title` VALUES ('10', '5');
INSERT INTO `title` VALUES ('11', '1');
INSERT INTO `title` VALUES ('12', '3');
INSERT INTO `title` VALUES ('12', '5');
INSERT INTO `title` VALUES ('13', '1');
INSERT INTO `title` VALUES ('13', '3');
INSERT INTO `title` VALUES ('14', '4');
INSERT INTO `title` VALUES ('15', '2');
INSERT INTO `title` VALUES ('15', '6');
INSERT INTO `title` VALUES ('16', '2');
INSERT INTO `title` VALUES ('16', '3');
INSERT INTO `title` VALUES ('16', '5');
INSERT INTO `title` VALUES ('17', '6');
INSERT INTO `title` VALUES ('18', '4');
INSERT INTO `title` VALUES ('18', '5');
INSERT INTO `title` VALUES ('19', '1');
INSERT INTO `title` VALUES ('19', '5');
INSERT INTO `title` VALUES ('20', '4');
INSERT INTO `title` VALUES ('20', '5');
