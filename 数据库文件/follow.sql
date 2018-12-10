/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : sharemate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 17:31:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `follow`
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `follow_id` int(6) NOT NULL AUTO_INCREMENT,
  `user_id` int(6) NOT NULL,
  `follow_date` date NOT NULL,
  PRIMARY KEY (`follow_id`,`user_id`),
  KEY `follow_id` (`follow_id`),
  KEY `follow_user_id` (`user_id`),
  CONSTRAINT `follow_id` FOREIGN KEY (`follow_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `follow_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES ('1', '2', '2017-12-11');
INSERT INTO `follow` VALUES ('1', '3', '2018-07-09');
INSERT INTO `follow` VALUES ('1', '5', '2018-08-28');
INSERT INTO `follow` VALUES ('1', '12', '2018-06-30');
INSERT INTO `follow` VALUES ('1', '16', '2018-11-22');
INSERT INTO `follow` VALUES ('1', '18', '2018-08-14');
INSERT INTO `follow` VALUES ('1', '20', '2018-08-29');
INSERT INTO `follow` VALUES ('2', '1', '2018-01-18');
INSERT INTO `follow` VALUES ('2', '4', '2018-03-22');
INSERT INTO `follow` VALUES ('2', '5', '2018-06-13');
INSERT INTO `follow` VALUES ('2', '6', '2018-05-15');
INSERT INTO `follow` VALUES ('2', '7', '2018-02-21');
INSERT INTO `follow` VALUES ('2', '8', '2018-05-21');
INSERT INTO `follow` VALUES ('2', '9', '2018-06-19');
INSERT INTO `follow` VALUES ('2', '15', '2018-08-14');
INSERT INTO `follow` VALUES ('2', '17', '2018-09-18');
INSERT INTO `follow` VALUES ('3', '2', '2018-12-01');
INSERT INTO `follow` VALUES ('3', '5', '2018-09-30');
INSERT INTO `follow` VALUES ('3', '8', '2018-05-14');
INSERT INTO `follow` VALUES ('3', '18', '2018-03-21');
INSERT INTO `follow` VALUES ('3', '20', '2018-04-24');
INSERT INTO `follow` VALUES ('4', '5', '2017-09-21');
INSERT INTO `follow` VALUES ('4', '6', '2018-09-18');
INSERT INTO `follow` VALUES ('4', '8', '2018-07-02');
INSERT INTO `follow` VALUES ('4', '12', '2017-12-28');
INSERT INTO `follow` VALUES ('4', '15', '2017-06-29');
INSERT INTO `follow` VALUES ('4', '18', '2018-06-09');
INSERT INTO `follow` VALUES ('4', '19', '2018-04-10');
INSERT INTO `follow` VALUES ('4', '20', '2018-09-09');
INSERT INTO `follow` VALUES ('5', '1', '2018-01-16');
INSERT INTO `follow` VALUES ('5', '2', '2018-02-14');
INSERT INTO `follow` VALUES ('5', '3', '2018-02-16');
INSERT INTO `follow` VALUES ('5', '4', '2018-02-17');
INSERT INTO `follow` VALUES ('5', '6', '2018-02-21');
INSERT INTO `follow` VALUES ('6', '7', '2018-08-08');
INSERT INTO `follow` VALUES ('6', '9', '2018-07-11');
INSERT INTO `follow` VALUES ('6', '10', '2018-10-17');
INSERT INTO `follow` VALUES ('6', '11', '2018-11-17');
INSERT INTO `follow` VALUES ('6', '12', '2018-11-22');
INSERT INTO `follow` VALUES ('6', '15', '2018-11-25');
INSERT INTO `follow` VALUES ('7', '2', '2018-04-14');
INSERT INTO `follow` VALUES ('7', '3', '2018-05-15');
INSERT INTO `follow` VALUES ('7', '5', '2018-05-15');
INSERT INTO `follow` VALUES ('7', '9', '2018-06-21');
INSERT INTO `follow` VALUES ('7', '11', '2018-05-17');
INSERT INTO `follow` VALUES ('7', '15', '2018-07-20');
INSERT INTO `follow` VALUES ('7', '16', '2018-12-01');
INSERT INTO `follow` VALUES ('7', '18', '2018-12-01');
INSERT INTO `follow` VALUES ('7', '19', '2018-12-03');
INSERT INTO `follow` VALUES ('8', '9', '2018-02-23');
INSERT INTO `follow` VALUES ('8', '10', '2018-02-25');
INSERT INTO `follow` VALUES ('9', '1', '2018-02-27');
INSERT INTO `follow` VALUES ('9', '2', '2018-02-27');
INSERT INTO `follow` VALUES ('9', '4', '2018-02-27');
INSERT INTO `follow` VALUES ('9', '5', '2018-03-13');
INSERT INTO `follow` VALUES ('9', '6', '2018-11-22');
INSERT INTO `follow` VALUES ('9', '7', '2018-11-17');
INSERT INTO `follow` VALUES ('9', '12', '2018-11-23');
INSERT INTO `follow` VALUES ('9', '13', '2018-12-02');
INSERT INTO `follow` VALUES ('9', '15', '2018-12-03');
INSERT INTO `follow` VALUES ('10', '1', '2017-04-18');
INSERT INTO `follow` VALUES ('10', '5', '2017-08-30');
INSERT INTO `follow` VALUES ('10', '11', '2018-10-11');
INSERT INTO `follow` VALUES ('10', '12', '2017-10-14');
INSERT INTO `follow` VALUES ('10', '15', '2017-09-15');
INSERT INTO `follow` VALUES ('10', '16', '2018-06-15');
INSERT INTO `follow` VALUES ('10', '18', '2018-11-29');
INSERT INTO `follow` VALUES ('10', '20', '2018-12-13');
INSERT INTO `follow` VALUES ('11', '1', '2018-01-06');
INSERT INTO `follow` VALUES ('11', '2', '2018-02-08');
INSERT INTO `follow` VALUES ('11', '3', '2018-03-13');
INSERT INTO `follow` VALUES ('11', '4', '2018-04-25');
INSERT INTO `follow` VALUES ('11', '5', '2018-05-26');
INSERT INTO `follow` VALUES ('11', '6', '2018-06-17');
INSERT INTO `follow` VALUES ('11', '7', '2018-08-09');
INSERT INTO `follow` VALUES ('11', '8', '2018-06-23');
INSERT INTO `follow` VALUES ('11', '9', '2018-07-20');
INSERT INTO `follow` VALUES ('11', '10', '2018-11-01');
INSERT INTO `follow` VALUES ('11', '12', '2018-09-14');
INSERT INTO `follow` VALUES ('11', '13', '2018-11-23');
INSERT INTO `follow` VALUES ('11', '14', '2018-09-21');
INSERT INTO `follow` VALUES ('11', '15', '2018-09-30');
INSERT INTO `follow` VALUES ('11', '16', '2018-10-17');
INSERT INTO `follow` VALUES ('11', '17', '2018-10-13');
INSERT INTO `follow` VALUES ('11', '18', '2018-11-21');
INSERT INTO `follow` VALUES ('11', '19', '2018-12-20');
INSERT INTO `follow` VALUES ('11', '20', '2018-12-04');
INSERT INTO `follow` VALUES ('12', '2', '2018-01-23');
INSERT INTO `follow` VALUES ('12', '3', '2017-08-25');
INSERT INTO `follow` VALUES ('12', '4', '2017-09-07');
INSERT INTO `follow` VALUES ('12', '6', '2017-10-14');
INSERT INTO `follow` VALUES ('12', '8', '2017-09-24');
INSERT INTO `follow` VALUES ('12', '9', '2017-11-17');
INSERT INTO `follow` VALUES ('12', '14', '2018-06-22');
INSERT INTO `follow` VALUES ('12', '15', '2018-08-11');
INSERT INTO `follow` VALUES ('12', '16', '2018-09-17');
INSERT INTO `follow` VALUES ('12', '19', '2018-10-14');
INSERT INTO `follow` VALUES ('13', '1', '2018-05-13');
INSERT INTO `follow` VALUES ('13', '3', '2017-06-17');
INSERT INTO `follow` VALUES ('13', '5', '2017-08-23');
INSERT INTO `follow` VALUES ('13', '6', '2018-06-16');
INSERT INTO `follow` VALUES ('13', '7', '2018-06-17');
INSERT INTO `follow` VALUES ('13', '8', '2018-05-20');
INSERT INTO `follow` VALUES ('13', '9', '2018-03-28');
INSERT INTO `follow` VALUES ('13', '15', '2018-04-01');
INSERT INTO `follow` VALUES ('13', '18', '2018-02-20');
INSERT INTO `follow` VALUES ('13', '19', '2018-05-03');
INSERT INTO `follow` VALUES ('14', '1', '2018-10-10');
INSERT INTO `follow` VALUES ('14', '2', '2017-04-12');
INSERT INTO `follow` VALUES ('14', '5', '2017-07-19');
INSERT INTO `follow` VALUES ('14', '7', '2018-06-23');
INSERT INTO `follow` VALUES ('14', '8', '2018-10-20');
INSERT INTO `follow` VALUES ('14', '15', '2018-05-13');
INSERT INTO `follow` VALUES ('14', '16', '2018-12-03');
INSERT INTO `follow` VALUES ('14', '19', '2018-02-17');
INSERT INTO `follow` VALUES ('14', '20', '2018-05-28');
INSERT INTO `follow` VALUES ('15', '1', '2018-11-16');
INSERT INTO `follow` VALUES ('15', '2', '2018-03-31');
INSERT INTO `follow` VALUES ('15', '3', '2018-02-02');
INSERT INTO `follow` VALUES ('15', '4', '2018-02-23');
INSERT INTO `follow` VALUES ('15', '5', '2018-03-01');
INSERT INTO `follow` VALUES ('15', '6', '2018-04-25');
INSERT INTO `follow` VALUES ('15', '7', '2018-09-16');
INSERT INTO `follow` VALUES ('15', '8', '2018-12-21');
INSERT INTO `follow` VALUES ('15', '9', '2018-09-09');
INSERT INTO `follow` VALUES ('15', '10', '2018-09-21');
INSERT INTO `follow` VALUES ('15', '11', '2017-08-23');
INSERT INTO `follow` VALUES ('15', '12', '2018-10-09');
INSERT INTO `follow` VALUES ('15', '16', '2017-05-13');
INSERT INTO `follow` VALUES ('15', '18', '2017-06-11');
INSERT INTO `follow` VALUES ('15', '19', '2017-07-27');
INSERT INTO `follow` VALUES ('16', '1', '2018-11-30');
INSERT INTO `follow` VALUES ('16', '2', '2018-10-20');
INSERT INTO `follow` VALUES ('16', '3', '2018-10-21');
INSERT INTO `follow` VALUES ('16', '5', '2018-07-14');
INSERT INTO `follow` VALUES ('16', '6', '2018-09-13');
INSERT INTO `follow` VALUES ('16', '8', '2018-10-21');
INSERT INTO `follow` VALUES ('16', '9', '2018-11-14');
INSERT INTO `follow` VALUES ('16', '10', '2018-06-23');
INSERT INTO `follow` VALUES ('16', '11', '2018-10-19');
INSERT INTO `follow` VALUES ('16', '12', '2017-08-20');
INSERT INTO `follow` VALUES ('16', '13', '2018-10-21');
INSERT INTO `follow` VALUES ('16', '15', '2017-08-16');
INSERT INTO `follow` VALUES ('16', '17', '2018-07-26');
INSERT INTO `follow` VALUES ('16', '18', '2017-07-14');
INSERT INTO `follow` VALUES ('17', '2', '2018-12-18');
INSERT INTO `follow` VALUES ('17', '5', '2018-11-21');
INSERT INTO `follow` VALUES ('17', '7', '2018-03-15');
INSERT INTO `follow` VALUES ('17', '9', '2018-09-22');
INSERT INTO `follow` VALUES ('17', '11', '2018-09-20');
INSERT INTO `follow` VALUES ('17', '13', '2018-09-19');
INSERT INTO `follow` VALUES ('17', '15', '2018-09-13');
INSERT INTO `follow` VALUES ('17', '16', '2018-08-22');
INSERT INTO `follow` VALUES ('17', '18', '2018-07-18');
INSERT INTO `follow` VALUES ('18', '5', '2018-01-11');
INSERT INTO `follow` VALUES ('18', '10', '2018-06-26');
INSERT INTO `follow` VALUES ('18', '15', '2018-12-13');
INSERT INTO `follow` VALUES ('18', '20', '2018-06-16');
INSERT INTO `follow` VALUES ('20', '1', '2018-01-01');
INSERT INTO `follow` VALUES ('20', '2', '2018-04-01');
INSERT INTO `follow` VALUES ('20', '3', '2018-03-04');
INSERT INTO `follow` VALUES ('20', '4', '2018-09-17');
INSERT INTO `follow` VALUES ('20', '5', '2018-10-29');
INSERT INTO `follow` VALUES ('20', '6', '2018-12-02');
INSERT INTO `follow` VALUES ('20', '7', '2018-06-11');
INSERT INTO `follow` VALUES ('20', '8', '2018-06-18');
INSERT INTO `follow` VALUES ('20', '9', '2018-06-29');
INSERT INTO `follow` VALUES ('20', '10', '2018-09-27');
INSERT INTO `follow` VALUES ('20', '11', '2018-06-11');
INSERT INTO `follow` VALUES ('20', '12', '2018-09-20');
INSERT INTO `follow` VALUES ('20', '13', '2018-10-31');
INSERT INTO `follow` VALUES ('20', '14', '2018-12-04');
INSERT INTO `follow` VALUES ('20', '15', '2018-05-05');
INSERT INTO `follow` VALUES ('20', '16', '2018-07-20');
INSERT INTO `follow` VALUES ('20', '17', '2018-09-18');
INSERT INTO `follow` VALUES ('20', '18', '2018-10-16');
INSERT INTO `follow` VALUES ('20', '19', '2018-10-01');
