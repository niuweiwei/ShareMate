/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : sharemate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 17:14:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `zan`
-- ----------------------------
DROP TABLE IF EXISTS `zan`;
CREATE TABLE `zan` (
  `user_id` int(11) NOT NULL,
  `note_id` int(11) NOT NULL,
  `zan_time` date DEFAULT NULL,
  PRIMARY KEY (`user_id`,`note_id`),
  KEY `zan_note_id` (`note_id`),
  CONSTRAINT `zan_note_id` FOREIGN KEY (`note_id`) REFERENCES `note` (`note_id`),
  CONSTRAINT `zan_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zan
-- ----------------------------
INSERT INTO `zan` VALUES ('1', '1', '2018-11-06');
INSERT INTO `zan` VALUES ('1', '2', '2018-10-16');
INSERT INTO `zan` VALUES ('1', '3', '2018-10-09');
INSERT INTO `zan` VALUES ('1', '4', '2018-11-06');
INSERT INTO `zan` VALUES ('1', '5', '2018-11-15');
INSERT INTO `zan` VALUES ('1', '6', '2018-11-02');
INSERT INTO `zan` VALUES ('1', '7', '2018-12-03');
INSERT INTO `zan` VALUES ('1', '8', '2018-11-05');
INSERT INTO `zan` VALUES ('1', '9', '2018-10-01');
INSERT INTO `zan` VALUES ('1', '10', '2018-11-02');
INSERT INTO `zan` VALUES ('1', '11', '2018-11-13');
INSERT INTO `zan` VALUES ('1', '12', '2018-09-06');
INSERT INTO `zan` VALUES ('1', '13', '2018-04-19');
INSERT INTO `zan` VALUES ('1', '14', '2018-08-22');
INSERT INTO `zan` VALUES ('1', '15', '2018-08-21');
INSERT INTO `zan` VALUES ('1', '16', '2018-09-18');
INSERT INTO `zan` VALUES ('1', '17', '2018-07-12');
INSERT INTO `zan` VALUES ('1', '18', '2018-07-25');
INSERT INTO `zan` VALUES ('1', '19', '2018-07-18');
INSERT INTO `zan` VALUES ('1', '20', '2018-09-19');
INSERT INTO `zan` VALUES ('2', '21', '2018-09-12');
INSERT INTO `zan` VALUES ('2', '22', '2018-09-17');
INSERT INTO `zan` VALUES ('2', '23', '2018-09-30');
INSERT INTO `zan` VALUES ('2', '24', '2018-08-24');
INSERT INTO `zan` VALUES ('2', '25', '2018-08-25');
INSERT INTO `zan` VALUES ('2', '26', '2018-08-21');
INSERT INTO `zan` VALUES ('2', '27', '2018-11-09');
INSERT INTO `zan` VALUES ('2', '28', '2018-09-12');
INSERT INTO `zan` VALUES ('2', '29', '2018-05-18');
INSERT INTO `zan` VALUES ('2', '30', '2018-05-16');
INSERT INTO `zan` VALUES ('2', '31', '2018-07-11');
INSERT INTO `zan` VALUES ('2', '32', '2018-11-20');
INSERT INTO `zan` VALUES ('2', '33', '2018-07-17');
INSERT INTO `zan` VALUES ('2', '34', '2018-10-02');
INSERT INTO `zan` VALUES ('2', '35', '2018-07-11');
INSERT INTO `zan` VALUES ('2', '36', '2018-09-25');
INSERT INTO `zan` VALUES ('2', '37', '2018-08-09');
INSERT INTO `zan` VALUES ('2', '38', '2018-08-15');
INSERT INTO `zan` VALUES ('2', '39', '2018-09-26');
INSERT INTO `zan` VALUES ('2', '40', '2018-11-10');
INSERT INTO `zan` VALUES ('3', '41', '2018-07-10');
INSERT INTO `zan` VALUES ('3', '42', '2018-06-13');
INSERT INTO `zan` VALUES ('3', '43', '2018-07-25');
INSERT INTO `zan` VALUES ('3', '44', '2018-07-11');
INSERT INTO `zan` VALUES ('3', '45', '2018-06-12');
INSERT INTO `zan` VALUES ('3', '46', '2018-08-20');
INSERT INTO `zan` VALUES ('3', '47', '2018-03-20');
INSERT INTO `zan` VALUES ('3', '48', '2018-06-18');
INSERT INTO `zan` VALUES ('3', '49', '2018-08-29');
INSERT INTO `zan` VALUES ('3', '50', '2018-08-29');
INSERT INTO `zan` VALUES ('3', '51', '2018-08-28');
INSERT INTO `zan` VALUES ('3', '52', '2018-08-27');
INSERT INTO `zan` VALUES ('3', '53', '2018-08-11');
INSERT INTO `zan` VALUES ('3', '54', '2018-07-17');
INSERT INTO `zan` VALUES ('3', '55', '2018-07-17');
INSERT INTO `zan` VALUES ('3', '56', '2018-07-17');
INSERT INTO `zan` VALUES ('3', '57', '2017-10-24');
INSERT INTO `zan` VALUES ('3', '58', '2019-02-07');
INSERT INTO `zan` VALUES ('3', '59', '2018-10-09');
INSERT INTO `zan` VALUES ('3', '60', '2018-12-08');
INSERT INTO `zan` VALUES ('4', '61', '2018-11-20');
INSERT INTO `zan` VALUES ('4', '62', '2018-07-11');
INSERT INTO `zan` VALUES ('4', '63', '2018-11-07');
INSERT INTO `zan` VALUES ('4', '64', '2018-05-15');
INSERT INTO `zan` VALUES ('4', '65', '2018-09-14');
INSERT INTO `zan` VALUES ('4', '66', '2018-02-15');
INSERT INTO `zan` VALUES ('4', '67', '2018-08-28');
INSERT INTO `zan` VALUES ('4', '68', '2018-09-06');
INSERT INTO `zan` VALUES ('4', '69', '2018-09-25');
INSERT INTO `zan` VALUES ('4', '70', '2018-06-19');
INSERT INTO `zan` VALUES ('4', '71', '2018-07-03');
INSERT INTO `zan` VALUES ('4', '72', '2018-06-27');
INSERT INTO `zan` VALUES ('4', '73', '2018-06-26');
INSERT INTO `zan` VALUES ('4', '74', '2018-06-19');
INSERT INTO `zan` VALUES ('4', '75', '2018-07-31');
INSERT INTO `zan` VALUES ('4', '76', '2018-07-17');
INSERT INTO `zan` VALUES ('4', '77', '2018-08-21');
INSERT INTO `zan` VALUES ('4', '78', '2018-08-14');
INSERT INTO `zan` VALUES ('4', '79', '2018-05-22');
INSERT INTO `zan` VALUES ('4', '80', '2018-05-16');
INSERT INTO `zan` VALUES ('5', '81', '2018-07-12');
INSERT INTO `zan` VALUES ('5', '82', '2018-06-20');
INSERT INTO `zan` VALUES ('5', '83', '2018-07-18');
INSERT INTO `zan` VALUES ('5', '84', '2018-07-05');
INSERT INTO `zan` VALUES ('5', '85', '2018-08-22');
INSERT INTO `zan` VALUES ('5', '86', '2018-08-28');
INSERT INTO `zan` VALUES ('5', '87', '2018-08-03');
INSERT INTO `zan` VALUES ('5', '88', '2018-07-17');
INSERT INTO `zan` VALUES ('5', '89', '2018-09-18');
INSERT INTO `zan` VALUES ('5', '90', '2018-08-11');
INSERT INTO `zan` VALUES ('5', '91', '2018-09-26');
INSERT INTO `zan` VALUES ('5', '92', '2018-11-20');
INSERT INTO `zan` VALUES ('5', '93', '2018-09-18');
INSERT INTO `zan` VALUES ('5', '94', '2018-10-30');
INSERT INTO `zan` VALUES ('5', '95', '2018-08-14');
INSERT INTO `zan` VALUES ('5', '96', '2018-09-11');
INSERT INTO `zan` VALUES ('5', '97', '2018-07-25');
INSERT INTO `zan` VALUES ('5', '98', '2018-06-12');
INSERT INTO `zan` VALUES ('5', '99', '2018-06-19');
INSERT INTO `zan` VALUES ('5', '100', '2018-10-11');
INSERT INTO `zan` VALUES ('6', '101', '2018-08-28');
INSERT INTO `zan` VALUES ('6', '102', '2018-10-04');
INSERT INTO `zan` VALUES ('6', '103', '2018-09-05');
INSERT INTO `zan` VALUES ('6', '104', '2018-02-21');
INSERT INTO `zan` VALUES ('6', '105', '2018-09-03');
INSERT INTO `zan` VALUES ('6', '106', '2018-08-29');
INSERT INTO `zan` VALUES ('6', '107', '2018-05-30');
INSERT INTO `zan` VALUES ('6', '108', '2018-06-19');
INSERT INTO `zan` VALUES ('6', '109', '2018-07-24');
INSERT INTO `zan` VALUES ('6', '110', '2018-08-23');
INSERT INTO `zan` VALUES ('6', '111', '2018-06-20');
INSERT INTO `zan` VALUES ('6', '112', '2018-08-22');
INSERT INTO `zan` VALUES ('6', '113', '2018-10-17');
INSERT INTO `zan` VALUES ('6', '114', '2018-06-12');
INSERT INTO `zan` VALUES ('6', '115', '2018-08-15');
INSERT INTO `zan` VALUES ('6', '116', '2018-07-18');
INSERT INTO `zan` VALUES ('6', '117', '2018-10-17');
INSERT INTO `zan` VALUES ('6', '118', '2018-09-11');
INSERT INTO `zan` VALUES ('6', '119', '2018-09-19');
INSERT INTO `zan` VALUES ('6', '120', '2018-07-24');
