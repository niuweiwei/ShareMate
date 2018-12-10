/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : sharemate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 10:39:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(6) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_photo` varchar(50) NOT NULL,
  `user_sex` char(2) NOT NULL,
  `user_phone` char(11) NOT NULL,
  `user_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_birth` date NOT NULL,
  `user_intro` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '随遇而安', 'abcde', 'images/userPhotos/1.jpg', '女', '15226560139', '石家庄市', '1998-11-01', '随遇而安');
INSERT INTO `user` VALUES ('2', '缘来是你', 'abcdef', 'images/userPhotos/2.jpg', '女', '15226500727', '哈尔滨市', '1998-02-12', '缘来是你');
INSERT INTO `user` VALUES ('3', '时光', 'asdfg', 'images/userPhotos/3.jpg', '女', '15226565596', '衡水市', '1998-04-27', '时光匆匆流去，只剩下你');
INSERT INTO `user` VALUES ('4', '薄荷少年', 'zxcvb', 'images/userPhotos/4.jpg', '男', '15226595707', '保定市', '1997-02-15', '匆匆那年的白净少年');
INSERT INTO `user` VALUES ('5', '星空', 'qwert', 'images/userPhotos/5.jpg', '女', '13930488523', '石家庄市', '1996-08-08', '怀念我们一起看过的星空');
INSERT INTO `user` VALUES ('6', 'ironman', '12345', 'images/userPhotos/6.jpg', '男', '15010165418', '北京市', '1998-04-30', '科技的力量');
INSERT INTO `user` VALUES ('7', 'doubleV', 'niuww', 'images/userPhotos/7.jpg', '女', '13801223093', '武汉市', '1976-01-30', '年轻的心');
INSERT INTO `user` VALUES ('8', 'superman', 'superman', 'images/userPhotos/8.jpg', '男', '15824799486', '重庆市', '2000-02-27', '拥有超能力的男人');
INSERT INTO `user` VALUES ('9', '爱吃火锅的小可爱', 'nww', 'images/userPhotos/9.jpg', '女', '17649027558', '上海市', '1990-07-05', '您的小可爱已上线');
INSERT INTO `user` VALUES ('10', '高冷爱神', '123456', 'images/userPhotos/10.jpg', '女', '13894755962', '深圳市', '1995-12-31', '渴望拥有全世界的化妆品');
INSERT INTO `user` VALUES ('11', '大雄', 'daxiong', 'images/userPhotos/11.jpg', '男', '18619022576', '厦门市', '1996-10-25', '想要一个哆啦A梦');
INSERT INTO `user` VALUES ('12', '怀柔鞋王', 'bjt', 'images/userPhotos/12.jpg', '男', '15925788462', '北京市', '1993-10-15', '穿着AJ才是coolboy');
INSERT INTO `user` VALUES ('13', '爱我你怕了？', 'zhuzhu', 'images/userPhotos/13.jpg', '男', '13857455980', '长沙市', '1982-05-29', null);
INSERT INTO `user` VALUES ('14', '锁骨精', '56789', 'images/userPhotos/14.jpg', '女', '15024077800', '香港特别行政区', '1995-09-27', null);
INSERT INTO `user` VALUES ('15', '喜欢吃柠檬的她', 'mff', 'images/userPhotos/15.jpg', '女', '18014588762', '天津', '1998-09-19', '够酷才是我的态度');
INSERT INTO `user` VALUES ('16', '雪莉girl', '456789', 'images/userPhotos/16.jpg', '女', '15607359772', '北京市', '1975-04-28', '梦想是雪莉');
INSERT INTO `user` VALUES ('17', '狗蛋儿', 'gou', 'images/userPhotos/17.jpg', '女', '15852160982', '上海市', '1985-09-04', '做梦都想发家致富');
INSERT INTO `user` VALUES ('18', 'AJboy', 'woaiaj', 'images/userPhotos/18.jpg', '男', '18729436087', '沧州市', '1995-03-20', '爱鞋但不卖鞋');
INSERT INTO `user` VALUES ('19', 'rapman', 'woairap', 'images/userPhotos/19.jpg', '男', '18546907215', '苏州市', '1994-01-31', 'skr skr');
INSERT INTO `user` VALUES ('20', '阿雯吖', 'wenwen', 'images/userPhotos/20.jpg', '女', '17398027516', '西安市', '1999-11-25', null);
