/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : sharemate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-19 08:40:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `like_reply`
-- ----------------------------
DROP TABLE IF EXISTS `like_reply`;
CREATE TABLE `like_reply` (
  `user_id` int(11) NOT NULL,
  `reply_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`reply_id`),
  KEY `likeReply` (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of like_reply
-- ----------------------------
INSERT INTO `like_reply` VALUES ('3', '2');
INSERT INTO `like_reply` VALUES ('3', '5');
INSERT INTO `like_reply` VALUES ('11', '7');
INSERT INTO `like_reply` VALUES ('4', '8');
INSERT INTO `like_reply` VALUES ('10', '8');
INSERT INTO `like_reply` VALUES ('11', '8');
INSERT INTO `like_reply` VALUES ('12', '8');
INSERT INTO `like_reply` VALUES ('7', '9');
INSERT INTO `like_reply` VALUES ('17', '10');
INSERT INTO `like_reply` VALUES ('10', '12');
INSERT INTO `like_reply` VALUES ('5', '13');
INSERT INTO `like_reply` VALUES ('12', '13');
INSERT INTO `like_reply` VALUES ('20', '13');
INSERT INTO `like_reply` VALUES ('11', '14');
INSERT INTO `like_reply` VALUES ('1', '20');
INSERT INTO `like_reply` VALUES ('18', '21');
INSERT INTO `like_reply` VALUES ('20', '21');
INSERT INTO `like_reply` VALUES ('13', '27');
INSERT INTO `like_reply` VALUES ('13', '40');
INSERT INTO `like_reply` VALUES ('19', '44');
INSERT INTO `like_reply` VALUES ('12', '100');
INSERT INTO `like_reply` VALUES ('16', '100');
INSERT INTO `like_reply` VALUES ('20', '122');
INSERT INTO `like_reply` VALUES ('15', '132');
