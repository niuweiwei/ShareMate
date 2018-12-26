/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : sharemate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-19 08:40:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `like_comment`
-- ----------------------------
DROP TABLE IF EXISTS `like_comment`;
CREATE TABLE `like_comment` (
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`comment_id`),
  KEY `comment` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of like_comment
-- ----------------------------
INSERT INTO `like_comment` VALUES ('1', '3');
INSERT INTO `like_comment` VALUES ('2', '3');
INSERT INTO `like_comment` VALUES ('4', '1');
INSERT INTO `like_comment` VALUES ('8', '12');
INSERT INTO `like_comment` VALUES ('9', '15');
INSERT INTO `like_comment` VALUES ('11', '4');
INSERT INTO `like_comment` VALUES ('11', '8');
INSERT INTO `like_comment` VALUES ('11', '17');
INSERT INTO `like_comment` VALUES ('13', '3');
INSERT INTO `like_comment` VALUES ('13', '16');
INSERT INTO `like_comment` VALUES ('14', '6');
INSERT INTO `like_comment` VALUES ('14', '7');
INSERT INTO `like_comment` VALUES ('14', '10');
INSERT INTO `like_comment` VALUES ('14', '17');
INSERT INTO `like_comment` VALUES ('16', '14');
INSERT INTO `like_comment` VALUES ('17', '8');
INSERT INTO `like_comment` VALUES ('21', '20');
INSERT INTO `like_comment` VALUES ('22', '20');
INSERT INTO `like_comment` VALUES ('25', '5');
INSERT INTO `like_comment` VALUES ('28', '16');
INSERT INTO `like_comment` VALUES ('29', '13');
INSERT INTO `like_comment` VALUES ('30', '12');
INSERT INTO `like_comment` VALUES ('30', '13');
INSERT INTO `like_comment` VALUES ('31', '3');
INSERT INTO `like_comment` VALUES ('40', '1');
INSERT INTO `like_comment` VALUES ('57', '7');
INSERT INTO `like_comment` VALUES ('100', '20');
