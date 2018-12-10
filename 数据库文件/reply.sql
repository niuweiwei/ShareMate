/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : sharemate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 15:23:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) DEFAULT NULL,
  `re_reply_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `reply_detail` varchar(255) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `reply_like_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `comment_id` (`comment_id`),
  KEY `re_reply_id` (`re_reply_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`coment_id`),
  CONSTRAINT `re_reply_id` FOREIGN KEY (`re_reply_id`) REFERENCES `reply` (`reply_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('1', '1', null, '20', '恩看着就想吃', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('2', '1', null, '20', '但是不会做啊', '2018-12-03 10:10:00', '0');
INSERT INTO `reply` VALUES ('3', null, '2', '19', '恩能吃不会做哈哈', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('4', null, '3', '20', '恩我要每天吃早餐', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('5', '2', null, '17', '的确很棒！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('6', '2', null, '16', '超级羡慕楼楼', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('7', null, '6', '15', '哈哈羡慕也没用，学做饭吧！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('8', null, '7', '16', '恩可是我只想吃哈哈', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('9', '3', null, '13', '我也是', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('10', '3', null, '12', '同上', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('11', '3', null, '11', '同上加一', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('12', '4', null, '10', '完美治疗食欲不振', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('13', null, '12', '4', '哈哈真逗', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('14', '4', null, '8', '恩我病好了', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('15', '5', null, '7', '天赋问题', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('16', null, '15', '5', '恩我一直以为自己做饭很有天赋的说', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('17', '6', null, '5', '我不喜欢加醋', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('18', '6', null, '4', '不爱吃醋加一', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('19', '7', null, '3', '和小伙伴吃过一次', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('20', null, '19', '7', '我也是就和小伙伴吃过两次啦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('21', '8', null, '1', '哈哈我还吃过一次那', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('22', '9', null, '20', '恩毕竟每天还是常用筷子', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('23', null, '22', '9', '记得初中都是在用勺子', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('24', '10', null, '18', '恩一看就不是精致的猪猪女孩', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('25', '11', null, '17', '是的，认知很准确', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('26', '12', null, '16', '请问有人会给你做咩', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('27', null, '26', '12', '这就扎心了', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('28', null, '27', '16', '哈哈，会有哒', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('29', '13', null, '14', '是差不多', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('30', null, '29', '12', '但味道可能不一样吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('31', null, '30', '14', '恩我更喜欢超级辣的', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('32', '14', null, '10', '我没有姐姐，羡慕', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('33', null, '32', '14', '我姐像第二个老妈哈哈', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('34', '14', null, '8', '羡慕加一', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('35', '15', null, '7', '恩同道中人', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('36', null, '35', '15', 'give me five！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('37', '16', null, '5', '恩熏肉大饼！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('38', '16', null, '4', '是的，就是你说的熏肉大饼，没见过咩', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('39', '17', null, '3', '我们学校还行，就是贵了点', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('40', null, '39', '17', '我们的又贵又难吃', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('41', '18', null, '1', '学校很好有这种美食啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('42', '19', null, '20', '恩每个人口味都不一样', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('43', '21', null, '19', '我喜欢软软的', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('44', '23', null, '18', '我也是，只能吃虾', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('45', '25', null, '17', '嗯嗯嗯，喜欢麻辣', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('46', null, '45', '5', '哈哈，好多爱辣人士', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('47', '28', null, '15', '恩流口水', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('48', '30', null, '14', '猪肉吗？看着像鸡肉', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('49', null, '48', '13', '应该是鸡肉吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('50', '31', null, '12', '对对对，像肯德基的鸡块', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('51', '33', null, '11', '满分加一', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('52', '37', null, '10', '恩我也是，身体不好得吃清淡的', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('53', '39', null, '9', '恩彩色的米饭呀', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('54', null, '53', '19', '好神奇，看着下不去口', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('55', '40', null, '7', '哈哈，我也是，还以为是养生粥', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('56', '43', null, '6', '恩文艺小厨', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('57', null, '56', '3', '哈哈，都是艺术的说', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('58', '48', null, '4', '喜欢煎蛋和鸡蛋羹', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('59', '49', null, '3', '恩法国哪里都有一种浪漫气息', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('60', '50', null, '2', '可以去西餐厅尝一下', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('61', null, '60', '10', '还想吃意面', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('62', '59', null, '20', '广州和香港很像吗？？', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('63', '61', null, '19', '恩老北京很有感觉', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('64', '73', null, '18', '我家这边就有长城，但没去过', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('65', '75', null, '17', '拍照的好地方', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('66', null, '65', '16', '恩摄影师的天堂', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('67', '88', null, '15', '模特妆容都很有特点', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('68', '90', null, '14', '很有设计感，模特整体效果很赞', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('69', null, '68', '13', '很难欣赏', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('70', '91', null, '12', '平时基本不化妆吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('71', '103', null, '11', '艺术，要尊重艺术哈哈', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('72', '107', null, '10', '眼霜是胡眼皮的吧，我也不知道', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('73', '109', null, '8', '分人，我是不敢', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('74', null, '73', '9', '到眼睛里得多难受', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('75', '110', null, '7', '闭眼然后用卸妆水抹掉吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('76', '113', null, '6', '呵天真的小孩', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('77', null, '76', '13', '我手超级丑的', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('78', '118', null, '4', '我是因为花花喜欢红色才喜欢红色的', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('79', '119', null, '3', '恩不用谢哈哈', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('80', '120', null, '2', '我手很丑不敢做', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('81', null, '80', '20', '总要去尝试的啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('82', '121', null, '20', '那你舍友很贤惠啊', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('83', null, '82', '1', '对啊，超级贤惠的老妈子', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('84', '123', null, '18', '我试过，敲难弄得', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('85', '125', null, '17', '那你闺蜜是个元气满满的人呀', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('86', null, '85', '5', '怎么说，每天都很有元气！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('87', '128', null, '15', '路飞大佬，最最喜欢他啦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('88', '130', null, '14', '爱奇艺会定期更新动漫', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('89', null, '88', '10', '哦哦，海贼迷认个亲', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('90', '131', null, '12', '艾斯那里根本不敢看，太难受了', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('91', '133', null, '11', '对啊，喜欢柯南到现在，是信仰啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('92', '137', null, '10', '每个人都有自己的故事，但又能连起来，超好看', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('93', '139', null, '9', 'b站就有全集哦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('94', null, '93', '19', '知道啦，谢谢啦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('95', '140', null, '7', '这楼是来打酱油的咩', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('96', '143', null, '6', '我记得花花也在追这个番', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('97', null, '96', '3', '啊花花也是超级可爱了', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('98', '148', null, '4', '恩我是它的路人粉', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('99', '149', null, '3', '当然是好哒啦，塞巴斯蒂安敲温柔啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('100', '150', null, '2', '可是它真的没在更新了', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('101', null, '100', '10', '对啊，剧情没发展超级难受', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('102', '151', null, '20', '然而估计看不到了', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('103', null, '102', '11', '再等等，没准就更了', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('104', '153', null, '18', '恩永远的经典，湘琴直树大爱', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('105', '155', null, '17', '恩画风很棒', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('106', '155', null, '16', '主角之间很有cp感啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('107', '158', null, '15', '柯南都不会完结，这篇更不会完结啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('108', '160', null, '14', '这个应该很好找吧，百度或者B站去看看', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('109', null, '108', '20', '恩谢啦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('110', '161', null, '12', '看楼上哦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('111', '163', null, '11', '天啊，你竟然说画质不好？？很赞的画风啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('112', '167', null, '10', '感觉小说的剧情更好看', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('113', '169', null, '9', '对啊，每次剧场版都很厉害', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('114', '169', null, '8', '剧场版真的好多炸弹', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('115', '170', null, '7', '爱奇艺要VIP才能看', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('116', '173', null, '6', 'push！！！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('117', null, '116', '13', '哈哈哈。。。', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('118', '178', null, '4', '怎么说，网球拍很沉', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('119', '179', null, '3', '恩我也长知识了', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('120', '180', null, '2', '技能get+1！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('121', '180', null, '1', 'get+1！！！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('122', '181', null, '20', '我只要是跑步就不行啊', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('123', null, '122', '1', '那要多锻炼才行啊', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('124', '183', null, '18', '恩我这次也没及格', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('125', '185', null, '17', '报的击剑班吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('126', null, '125', '5', '哦哦了解', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('127', '188', null, '15', '大一选修的篮球，然而学不会', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('128', '190', null, '14', '有啊，发给你', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('129', null, '128', '10', '好啊，谢啦', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('130', '191', null, '12', '享受运动的快乐吗', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('131', '193', null, '11', '恩我不知道你在说啥，尴尬', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('132', '197', null, '10', '错别字，是不会哦，应该有人教吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('133', '199', null, '9', '哈哈哈有道理', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('134', '199', null, '8', '不敢尝试游泳这种运动', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('135', '210', null, '7', '不清楚(＾－＾)V一个小时？半天？', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('136', '213', null, '6', '我需要！！', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('137', null, '136', '13', '好，私聊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('138', '228', null, '4', '哪里神奇，一般般啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('139', '239', null, '3', '既是科学又是技术吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('140', '240', null, '2', '有这个专业啊，前景也不错啊', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('141', null, '140', '20', '哦哦谢谢解答', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('142', '245', null, '20', '我有，发给你', '2018-12-04 20:00:00', '0');
INSERT INTO `reply` VALUES ('143', '251', null, '19', '但可能还要再等等吧', '2018-12-03 10:20:00', '0');
INSERT INTO `reply` VALUES ('144', '253', null, '18', '我也不太清楚，外行', '2018-12-03 09:59:00', '0');
INSERT INTO `reply` VALUES ('145', '255', null, '17', '不会吧，能吃就没事', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('146', null, '145', '15', '不怕一万就怕万一', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('147', '258', null, '15', '没进去过，你去百度吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('148', '260', null, '14', '很难受吧', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('149', null, '148', '20', '但是可以飘起来', '2018-12-03 19:59:00', '0');
INSERT INTO `reply` VALUES ('150', '266', null, '12', '没太多区别', '2018-12-03 19:59:00', '0');
