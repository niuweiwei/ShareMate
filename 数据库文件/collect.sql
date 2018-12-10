/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : share_mate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 10:56:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `user_id` int(6) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`note_id`),
  KEY `collect_note_id` (`note_id`),
  CONSTRAINT `collect_note_id` FOREIGN KEY (`note_id`) REFERENCES `note` (`note_id`),
  CONSTRAINT `collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('1', '2');
INSERT INTO `collect` VALUES ('3', '2');
INSERT INTO `collect` VALUES ('1', '3');
INSERT INTO `collect` VALUES ('1', '4');
INSERT INTO `collect` VALUES ('1', '5');
INSERT INTO `collect` VALUES ('6', '6');
INSERT INTO `collect` VALUES ('1', '7');
INSERT INTO `collect` VALUES ('8', '8');
INSERT INTO `collect` VALUES ('10', '10');
INSERT INTO `collect` VALUES ('9', '12');
INSERT INTO `collect` VALUES ('10', '12');
INSERT INTO `collect` VALUES ('10', '15');
INSERT INTO `collect` VALUES ('11', '15');
INSERT INTO `collect` VALUES ('12', '15');
INSERT INTO `collect` VALUES ('6', '16');
INSERT INTO `collect` VALUES ('8', '16');
INSERT INTO `collect` VALUES ('6', '17');
INSERT INTO `collect` VALUES ('6', '18');
INSERT INTO `collect` VALUES ('10', '18');
INSERT INTO `collect` VALUES ('10', '20');
INSERT INTO `collect` VALUES ('12', '20');
INSERT INTO `collect` VALUES ('3', '23');
INSERT INTO `collect` VALUES ('7', '23');
INSERT INTO `collect` VALUES ('13', '23');
INSERT INTO `collect` VALUES ('3', '24');
INSERT INTO `collect` VALUES ('8', '24');
INSERT INTO `collect` VALUES ('9', '25');
INSERT INTO `collect` VALUES ('11', '25');
INSERT INTO `collect` VALUES ('7', '32');
INSERT INTO `collect` VALUES ('8', '32');
INSERT INTO `collect` VALUES ('5', '34');
INSERT INTO `collect` VALUES ('5', '35');
INSERT INTO `collect` VALUES ('12', '35');
INSERT INTO `collect` VALUES ('5', '36');
INSERT INTO `collect` VALUES ('8', '40');
INSERT INTO `collect` VALUES ('7', '42');
INSERT INTO `collect` VALUES ('2', '43');
INSERT INTO `collect` VALUES ('7', '43');
INSERT INTO `collect` VALUES ('9', '43');
INSERT INTO `collect` VALUES ('4', '44');
INSERT INTO `collect` VALUES ('3', '45');
INSERT INTO `collect` VALUES ('7', '45');
INSERT INTO `collect` VALUES ('11', '45');
INSERT INTO `collect` VALUES ('12', '45');
INSERT INTO `collect` VALUES ('13', '45');
INSERT INTO `collect` VALUES ('3', '46');
INSERT INTO `collect` VALUES ('11', '47');
INSERT INTO `collect` VALUES ('8', '48');
INSERT INTO `collect` VALUES ('12', '48');
INSERT INTO `collect` VALUES ('2', '49');
INSERT INTO `collect` VALUES ('7', '49');
INSERT INTO `collect` VALUES ('10', '50');
INSERT INTO `collect` VALUES ('4', '53');
INSERT INTO `collect` VALUES ('2', '54');
INSERT INTO `collect` VALUES ('7', '54');
INSERT INTO `collect` VALUES ('9', '54');
INSERT INTO `collect` VALUES ('5', '55');
INSERT INTO `collect` VALUES ('11', '55');
INSERT INTO `collect` VALUES ('2', '56');
INSERT INTO `collect` VALUES ('4', '56');
INSERT INTO `collect` VALUES ('7', '56');
INSERT INTO `collect` VALUES ('8', '56');
INSERT INTO `collect` VALUES ('11', '58');
INSERT INTO `collect` VALUES ('5', '63');
INSERT INTO `collect` VALUES ('7', '63');
INSERT INTO `collect` VALUES ('8', '64');
INSERT INTO `collect` VALUES ('5', '65');
INSERT INTO `collect` VALUES ('9', '65');
INSERT INTO `collect` VALUES ('1', '67');
INSERT INTO `collect` VALUES ('3', '67');
INSERT INTO `collect` VALUES ('5', '67');
INSERT INTO `collect` VALUES ('13', '67');
INSERT INTO `collect` VALUES ('6', '69');
INSERT INTO `collect` VALUES ('10', '70');
INSERT INTO `collect` VALUES ('8', '72');
INSERT INTO `collect` VALUES ('9', '75');
INSERT INTO `collect` VALUES ('3', '76');
INSERT INTO `collect` VALUES ('4', '76');
INSERT INTO `collect` VALUES ('1', '78');
INSERT INTO `collect` VALUES ('2', '78');
INSERT INTO `collect` VALUES ('6', '78');
INSERT INTO `collect` VALUES ('13', '78');
INSERT INTO `collect` VALUES ('4', '85');
INSERT INTO `collect` VALUES ('11', '85');
INSERT INTO `collect` VALUES ('12', '85');
INSERT INTO `collect` VALUES ('12', '86');
INSERT INTO `collect` VALUES ('9', '87');
INSERT INTO `collect` VALUES ('12', '87');
INSERT INTO `collect` VALUES ('5', '88');
INSERT INTO `collect` VALUES ('7', '88');
INSERT INTO `collect` VALUES ('8', '88');
INSERT INTO `collect` VALUES ('4', '89');
INSERT INTO `collect` VALUES ('7', '89');
INSERT INTO `collect` VALUES ('13', '89');
INSERT INTO `collect` VALUES ('4', '90');
INSERT INTO `collect` VALUES ('5', '90');
INSERT INTO `collect` VALUES ('10', '90');
INSERT INTO `collect` VALUES ('2', '98');
INSERT INTO `collect` VALUES ('6', '100');
INSERT INTO `collect` VALUES ('8', '102');
INSERT INTO `collect` VALUES ('11', '102');
INSERT INTO `collect` VALUES ('10', '110');
INSERT INTO `collect` VALUES ('9', '111');
INSERT INTO `collect` VALUES ('3', '112');
INSERT INTO `collect` VALUES ('11', '112');
INSERT INTO `collect` VALUES ('11', '113');
INSERT INTO `collect` VALUES ('8', '115');
INSERT INTO `collect` VALUES ('12', '118');
INSERT INTO `collect` VALUES ('8', '119');
INSERT INTO `collect` VALUES ('4', '120');
INSERT INTO `collect` VALUES ('6', '120');
INSERT INTO `collect` VALUES ('9', '120');
