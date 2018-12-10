/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : share_mate

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-12-04 14:22:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `coment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  `comment_like_count` int(11) DEFAULT NULL,
  `user_id` int(6) DEFAULT NULL,
  `note_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`coment_id`),
  KEY `comment_user_id` (`user_id`),
  KEY `comment_note_id` (`note_id`),
  CONSTRAINT `comment_note_id` FOREIGN KEY (`note_id`) REFERENCES `note` (`note_id`),
  CONSTRAINT `comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '看着好好吃！！！', '2018-12-01 00:00:00', '10', '1', '1');
INSERT INTO `comment` VALUES ('2', '楼楼早餐很棒！', '2018-12-01 00:00:00', '10', '2', '1');
INSERT INTO `comment` VALUES ('3', '啊很少吃早餐的我突然想吃早餐？！', '2018-12-01 12:01:00', '0', '3', '1');
INSERT INTO `comment` VALUES ('4', '看着就有食欲哈。', '2018-12-01 13:01:00', '1', '4', '3');
INSERT INTO `comment` VALUES ('5', '我也做过，差距好大。。。', '2018-12-01 09:01:00', '2', '5', '3');
INSERT INTO `comment` VALUES ('6', '超喜欢吃土豆丝！', '2018-12-01 09:01:00', '2', '6', '3');
INSERT INTO `comment` VALUES ('7', '很少吃西餐啦。', '2018-12-01 09:01:00', '0', '7', '4');
INSERT INTO `comment` VALUES ('8', '我能说我没吃过咩？！', '2018-12-02 09:03:00', '20', '8', '4');
INSERT INTO `comment` VALUES ('9', '不怎么会用刀叉。', '2018-12-02 09:03:00', '3', '9', '4');
INSERT INTO `comment` VALUES ('10', '感觉自己早餐好粗糙', '2018-12-02 09:03:00', '5', '10', '5');
INSERT INTO `comment` VALUES ('11', '是我活得太不精致了吗？！', '2018-12-02 09:03:00', '6', '11', '5');
INSERT INTO `comment` VALUES ('12', '如果早餐有这些，我保证每天吃早餐a!', '2018-12-02 09:03:00', '4', '12', '5');
INSERT INTO `comment` VALUES ('13', '感觉水煮肉片都差不多呀！', '2018-12-02 09:03:00', '7', '13', '2');
INSERT INTO `comment` VALUES ('14', '每次回家我姐都做这个。', '2018-12-02 09:03:00', '5', '14', '2');
INSERT INTO `comment` VALUES ('15', '其实是我的最爱！', '2018-12-02 09:03:00', '4', '15', '2');
INSERT INTO `comment` VALUES ('16', '熏肉大饼？？', '2018-12-02 09:03:00', '0', '16', '6');
INSERT INTO `comment` VALUES ('17', '学校也有，可是很难吃', '2018-12-03 09:03:00', '5', '17', '6');
INSERT INTO `comment` VALUES ('18', '怎么我们学校的就比这个差好多？！', '2018-12-02 09:03:00', '4', '18', '6');
INSERT INTO `comment` VALUES ('19', '还行，不太喜欢', '2018-12-02 09:03:00', '2', '19', '7');
INSERT INTO `comment` VALUES ('20', '正新鸡排，我的最爱啊哈哈', '2018-12-02 09:03:00', '4', '20', '7');
INSERT INTO `comment` VALUES ('21', '图片看着不脆，喜欢脆脆哒！', '2018-12-02 09:03:00', '5', '1', '7');
INSERT INTO `comment` VALUES ('22', '喜欢煮的', '2018-12-02 09:03:00', '5', '2', '8');
INSERT INTO `comment` VALUES ('23', '不喜欢海鲜类', '2018-12-02 09:03:00', '3', '3', '8');
INSERT INTO `comment` VALUES ('24', '还行，就是不想剥螃蟹', '2018-12-02 09:03:00', '5', '4', '8');
INSERT INTO `comment` VALUES ('25', '喜欢麻辣的', '2018-12-02 09:03:00', '5', '5', '8');
INSERT INTO `comment` VALUES ('26', '好想吃', '2018-12-02 09:03:00', '6', '6', '9');
INSERT INTO `comment` VALUES ('27', '到冬天就超想吃冰淇淋', '2018-12-02 09:03:00', '5', '7', '9');
INSERT INTO `comment` VALUES ('28', '啊啊看着好想吃', '2018-12-02 09:03:00', '5', '8', '10');
INSERT INTO `comment` VALUES ('29', '其实我从没吃过正宗的章鱼小丸子', '2018-12-02 09:03:00', '5', '9', '10');
INSERT INTO `comment` VALUES ('30', '肉丸子吗？？', '2018-12-02 09:03:00', '6', '10', '11');
INSERT INTO `comment` VALUES ('31', '看着好像鸡块哈', '2018-12-02 09:03:00', '6', '11', '11');
INSERT INTO `comment` VALUES ('32', '快餐生活啊', '2018-12-02 09:03:00', '5', '12', '12');
INSERT INTO `comment` VALUES ('33', '这套餐满分', '2018-12-02 09:03:00', '5', '13', '13');
INSERT INTO `comment` VALUES ('34', '每天不知道在吃啥的我好羡慕', '2018-12-02 09:03:00', '6', '14', '13');
INSERT INTO `comment` VALUES ('35', '啊我要去吃', '2018-12-02 09:03:00', '5', '15', '13');
INSERT INTO `comment` VALUES ('36', '这颜色有点。。。', '2018-12-02 09:03:00', '7', '16', '14');
INSERT INTO `comment` VALUES ('37', '更喜欢清淡点的', '2018-12-02 09:03:00', '6', '17', '14');
INSERT INTO `comment` VALUES ('38', '看着还行吧', '2018-12-02 09:03:00', '6', '18', '14');
INSERT INTO `comment` VALUES ('39', '是米饭？？', '2018-12-02 09:03:00', '8', '19', '15');
INSERT INTO `comment` VALUES ('40', '我还以为是粥', '2018-12-02 09:03:00', '8', '20', '15');
INSERT INTO `comment` VALUES ('41', '更喜欢吃溏心蛋', '2018-12-02 09:03:00', '8', '1', '16');
INSERT INTO `comment` VALUES ('42', '不会做这种海鲜', '2018-12-02 09:03:00', '13', '2', '17');
INSERT INTO `comment` VALUES ('43', '现在做菜都要有艺术细胞哈哈', '2018-12-02 09:03:00', '14', '3', '18');
INSERT INTO `comment` VALUES ('44', '喜欢鸡翅', '2018-12-02 09:03:00', '8', '4', '19');
INSERT INTO `comment` VALUES ('45', '啊突然想吃肉', '2018-12-02 09:03:00', '7', '5', '19');
INSERT INTO `comment` VALUES ('46', '衡水不是烤鸭蛋吗哈哈', '2018-12-02 09:03:00', '9', '6', '20');
INSERT INTO `comment` VALUES ('47', '舍友是衡水的说。', '2018-12-02 09:03:00', '9', '7', '20');
INSERT INTO `comment` VALUES ('48', '不喜欢吃蛋', '2018-12-02 09:03:00', '7', '8', '20');
INSERT INTO `comment` VALUES ('49', '法国牛排看着都很浪漫啊', '2018-12-02 09:03:00', '6', '9', '21');
INSERT INTO `comment` VALUES ('50', '想尝试法国餐', '2018-12-02 09:03:00', '7', '10', '21');
INSERT INTO `comment` VALUES ('51', '环境看着好好', '2018-12-02 09:03:00', '8', '11', '22');
INSERT INTO `comment` VALUES ('52', '是美景还是人啊！！', '2018-12-02 09:03:00', '5', '12', '23');
INSERT INTO `comment` VALUES ('53', '喜欢香港的街边', '2018-12-02 09:03:00', '6', '13', '24');
INSERT INTO `comment` VALUES ('54', '想去大馆走一波', '2018-12-02 09:03:00', '8', '14', '24');
INSERT INTO `comment` VALUES ('55', '一直想去还没去的长白山', '2018-12-02 09:03:00', '6', '15', '25');
INSERT INTO `comment` VALUES ('56', '长白上总让我想起老白干是什么鬼？！', '2018-12-02 09:03:00', '6', '16', '25');
INSERT INTO `comment` VALUES ('57', '大理也是个不错的景点啊', '2018-12-02 09:03:00', '8', '17', '26');
INSERT INTO `comment` VALUES ('58', '想在杭州养老', '2018-12-02 09:03:00', '3', '18', '27');
INSERT INTO `comment` VALUES ('59', '没怎么听过太古汇', '2018-12-02 09:03:00', '4', '19', '28');
INSERT INTO `comment` VALUES ('60', '恩我想去看看', '2018-12-02 09:03:00', '6', '20', '28');
INSERT INTO `comment` VALUES ('61', '老北京(＾－＾)V', '2018-12-02 09:03:00', '5', '1', '29');
INSERT INTO `comment` VALUES ('62', '感觉胡同让人很亲切啊', '2018-12-02 09:03:00', '6', '2', '29');
INSERT INTO `comment` VALUES ('63', '这图片莫名高大上', '2018-12-02 09:03:00', '6', '3', '29');
INSERT INTO `comment` VALUES ('64', '这搭配很美', '2018-12-02 09:03:00', '6', '4', '30');
INSERT INTO `comment` VALUES ('65', '这是哪里？？', '2018-12-02 09:03:00', '6', '5', '30');
INSERT INTO `comment` VALUES ('66', '很美的感觉', '2018-12-02 09:03:00', '4', '6', '30');
INSERT INTO `comment` VALUES ('67', '照片拍的好好', '2018-12-02 09:03:00', '4', '7', '30');
INSERT INTO `comment` VALUES ('68', '会有神仙住里面吗哈哈', '2018-12-02 09:03:00', '5', '8', '31');
INSERT INTO `comment` VALUES ('69', '小学课本里是不是有黄山', '2018-12-02 09:03:00', '4', '9', '31');
INSERT INTO `comment` VALUES ('70', '黄山在哪？我竟然不知道', '2018-12-02 09:03:00', '5', '10', '31');
INSERT INTO `comment` VALUES ('71', '向往的地方', '2018-12-02 09:03:00', '4', '11', '32');
INSERT INTO `comment` VALUES ('72', '闪哭', '2018-12-02 09:03:00', '4', '12', '33');
INSERT INTO `comment` VALUES ('73', '我不是好汉', '2018-12-02 09:03:00', '4', '13', '34');
INSERT INTO `comment` VALUES ('74', '好汉加1', '2018-12-02 09:03:00', '14', '14', '34');
INSERT INTO `comment` VALUES ('75', '看着就很幸福啊', '2018-12-02 09:03:00', '13', '15', '35');
INSERT INTO `comment` VALUES ('76', '浪漫花海咩', '2018-12-02 09:03:00', '3', '16', '35');
INSERT INTO `comment` VALUES ('77', '我小风车都没见过哦', '2018-12-02 09:03:00', '3', '17', '36');
INSERT INTO `comment` VALUES ('78', '怎么这么多美哭我的地方', '2018-12-02 09:03:00', '3', '18', '37');
INSERT INTO `comment` VALUES ('79', '炫富了哦', '2018-12-02 09:03:00', '3', '19', '38');
INSERT INTO `comment` VALUES ('80', '50元抢镜了吧', '2018-12-02 09:03:00', '3', '20', '38');
INSERT INTO `comment` VALUES ('81', '好晕', '2018-12-02 09:03:00', '3', '1', '39');
INSERT INTO `comment` VALUES ('82', '云南有点远，离我哈', '2018-12-02 09:03:00', '4', '2', '39');
INSERT INTO `comment` VALUES ('83', '谢谢分享哦', '2018-12-02 09:03:00', '2', '3', '39');
INSERT INTO `comment` VALUES ('84', '泰国也是旅游胜地呢', '2018-12-02 09:03:00', '2', '4', '40');
INSERT INTO `comment` VALUES ('85', '不喜欢泰国人说话的慢节奏', '2018-12-02 09:03:00', '2', '5', '40');
INSERT INTO `comment` VALUES ('86', '看泰剧里的景都很不错', '2018-12-02 09:03:00', '2', '6', '40');
INSERT INTO `comment` VALUES ('87', '想去泰国，可是我不会英语又土又穷哈哈', '2018-12-02 09:03:00', '2', '7', '40');
INSERT INTO `comment` VALUES ('88', '有点怕怕的', '2018-12-02 09:03:00', '3', '8', '41');
INSERT INTO `comment` VALUES ('89', '不敢尝试', '2018-12-02 09:03:00', '3', '9', '41');
INSERT INTO `comment` VALUES ('90', '名模的彩妆都很大胆啊', '2018-12-02 09:03:00', '6', '10', '41');
INSERT INTO `comment` VALUES ('91', '不全，几乎没有的我', '2018-12-02 09:03:00', '7', '11', '42');
INSERT INTO `comment` VALUES ('92', '没画过眉毛，感觉自己不用画也很好看', '2018-12-02 09:03:00', '8', '12', '43');
INSERT INTO `comment` VALUES ('93', '喜欢橙色的那种', '2018-12-02 09:03:00', '10', '13', '44');
INSERT INTO `comment` VALUES ('94', '同桌画的眼影超好看', '2018-12-02 09:03:00', '12', '14', '44');
INSERT INTO `comment` VALUES ('95', '舍友刚去做的美甲', '2018-12-02 09:03:00', '12', '15', '45');
INSERT INTO `comment` VALUES ('96', '没有指甲得我默默欣赏', '2018-12-02 09:03:00', '16', '16', '45');
INSERT INTO `comment` VALUES ('97', '唯一的口红送给了姐姐', '2018-12-02 09:03:00', '17', '17', '46');
INSERT INTO `comment` VALUES ('98', '看着有点那啥怎么破', '2018-12-02 09:03:00', '16', '18', '47');
INSERT INTO `comment` VALUES ('99', '我只有bb霜', '2018-12-02 09:03:00', '13', '19', '48');
INSERT INTO `comment` VALUES ('100', '没用过粉底还，这么多讲究吗', '2018-12-02 09:03:00', '17', '20', '48');
INSERT INTO `comment` VALUES ('101', '有点过了吧', '2018-12-02 09:03:00', '18', '1', '49');
INSERT INTO `comment` VALUES ('102', '模特能带出去，平时就算了吧', '2018-12-02 09:03:00', '4', '2', '49');
INSERT INTO `comment` VALUES ('103', '好吓人，欣赏不来了', '2018-12-02 09:03:00', '5', '3', '49');
INSERT INTO `comment` VALUES ('104', '好像做广告的哦', '2018-12-02 09:03:00', '6', '4', '50');
INSERT INTO `comment` VALUES ('105', '美肤宝隔离防晒？隔离紫外线？', '2018-12-02 09:03:00', '7', '5', '50');
INSERT INTO `comment` VALUES ('106', '有的眼霜会过敏', '2018-12-02 09:03:00', '5', '6', '50');
INSERT INTO `comment` VALUES ('107', '眼霜是干嘛的', '2018-12-02 09:03:00', '5', '7', '50');
INSERT INTO `comment` VALUES ('108', '一直不敢画眼线的我', '2018-12-02 09:03:00', '5', '8', '51');
INSERT INTO `comment` VALUES ('109', '不会弄到眼睛里面吗', '2018-12-02 09:03:00', '7', '9', '51');
INSERT INTO `comment` VALUES ('110', '洗的时候怎么破', '2018-12-02 09:03:00', '2', '10', '51');
INSERT INTO `comment` VALUES ('111', '天生卧蚕哦', '2018-12-02 09:03:00', '5', '11', '52');
INSERT INTO `comment` VALUES ('112', '几乎没有怎么说', '2018-12-02 09:03:00', '6', '12', '53');
INSERT INTO `comment` VALUES ('113', '有显瘦的美甲咩', '2018-12-02 09:03:00', '8', '13', '54');
INSERT INTO `comment` VALUES ('114', '呦呦哟真的可以吗', '2018-12-02 09:03:00', '13', '14', '54');
INSERT INTO `comment` VALUES ('115', '想想想', '2018-12-02 09:03:00', '15', '15', '55');
INSERT INTO `comment` VALUES ('116', '好厉害', '2018-12-02 09:03:00', '4', '16', '55');
INSERT INTO `comment` VALUES ('117', '恩步骤很好学', '2018-12-02 09:03:00', '6', '17', '56');
INSERT INTO `comment` VALUES ('118', '喜欢大红色', '2018-12-02 09:03:00', '5', '18', '57');
INSERT INTO `comment` VALUES ('119', '谢谢推荐', '2018-12-02 09:03:00', '7', '19', '58');
INSERT INTO `comment` VALUES ('120', '啊我要去做美甲啦', '2018-12-02 09:03:00', '7', '20', '58');
INSERT INTO `comment` VALUES ('121', '舍友就很会编头发', '2018-12-02 09:03:00', '7', '1', '59');
INSERT INTO `comment` VALUES ('122', '不会自己给自己编发', '2018-12-02 09:03:00', '7', '2', '59');
INSERT INTO `comment` VALUES ('123', '看着很简单，上手很难破', '2018-12-02 09:03:00', '7', '3', '59');
INSERT INTO `comment` VALUES ('124', '今天也要元气满满哦', '2018-12-02 09:03:00', '8', '4', '60');
INSERT INTO `comment` VALUES ('125', '要给闺蜜这个色号', '2018-12-02 09:03:00', '9', '5', '60');
INSERT INTO `comment` VALUES ('126', '可以，很元气', '2018-12-02 09:03:00', '0', '6', '60');
INSERT INTO `comment` VALUES ('127', '恩看着很精神啊', '2018-12-02 09:03:00', '9', '7', '60');
INSERT INTO `comment` VALUES ('128', '超级喜欢海贼王，期待更新！！！', '2018-12-02 09:03:00', '9', '8', '61');
INSERT INTO `comment` VALUES ('129', '看到你根本停不下来的动漫', '2018-12-02 09:03:00', '2', '9', '61');
INSERT INTO `comment` VALUES ('130', '想要问一下在哪看呢', '2018-12-02 09:03:00', '3', '10', '61');
INSERT INTO `comment` VALUES ('131', '看到罗宾身世，艾斯死的时候还有克拉松先生死的时候哭到窒息啊', '2018-12-02 09:03:00', '4', '11', '61');
INSERT INTO `comment` VALUES ('132', '超级喜欢名侦探柯南，希望柯南能变回新一和小兰在一起！！', '2018-12-02 09:03:00', '2', '12', '62');
INSERT INTO `comment` VALUES ('133', '柯南一共有好多集，这是伴随我长大的动漫啊', '2018-12-02 09:03:00', '15', '13', '62');
INSERT INTO `comment` VALUES ('134', '喜欢柯南，喜欢小哀，我更希望他们在一起呢', '2018-12-02 09:03:00', '14', '14', '62');
INSERT INTO `comment` VALUES ('135', '看着超级好看呢', '2018-12-02 09:03:00', '15', '15', '63');
INSERT INTO `comment` VALUES ('136', '连当个值日生也能迷死人', '2018-12-02 09:03:00', '17', '16', '63');
INSERT INTO `comment` VALUES ('137', '画风很赞，情节很有意思，超级喜欢', '2018-12-02 09:03:00', '15', '17', '64');
INSERT INTO `comment` VALUES ('138', '赞同作者大大，特备好看，尤其声优简直了', '2018-12-02 09:03:00', '2', '18', '65');
INSERT INTO `comment` VALUES ('139', '请问哪里可以看啊', '2018-12-02 09:03:00', '6', '19', '65');
INSERT INTO `comment` VALUES ('140', '无动漫，不青春', '2018-12-02 09:03:00', '0', '20', '65');
INSERT INTO `comment` VALUES ('141', '喜欢里面的哥哥，各个无敌，但妹妹也很可爱', '2018-12-02 09:03:00', '4', '1', '66');
INSERT INTO `comment` VALUES ('142', '没有人喜欢我青山优雅吗', '2018-12-02 09:03:00', '6', '2', '67');
INSERT INTO `comment` VALUES ('143', '啊啊啊是的超好看可以说是', '2018-12-02 09:03:00', '4', '3', '67');
INSERT INTO `comment` VALUES ('144', '很燃很好看！喜欢绿谷、常暗、轰少年、上鸣、切岛', '2018-12-02 09:03:00', '7', '4', '67');
INSERT INTO `comment` VALUES ('145', '轰轰真的帅炸', '2018-12-02 09:03:00', '5', '5', '67');
INSERT INTO `comment` VALUES ('146', '超级喜欢这个设定，强推', '2018-12-02 09:03:00', '2', '6', '68');
INSERT INTO `comment` VALUES ('147', '我不喜欢那个画质，为什么脖子那会有阴影啊', '2018-12-02 09:03:00', '5', '7', '68');
INSERT INTO `comment` VALUES ('148', '感觉黑执事圈粉一波', '2018-12-02 09:03:00', '2', '8', '69');
INSERT INTO `comment` VALUES ('149', '这个结局是好的吗，受不了虐啊~~', '2018-12-02 09:03:00', '6', '9', '69');
INSERT INTO `comment` VALUES ('150', '喜欢这部剧，慢慢的都是青春', '2018-12-02 09:03:00', '8', '10', '70');
INSERT INTO `comment` VALUES ('151', '喜欢最后可以在一起啊', '2018-12-02 09:03:00', '9', '11', '70');
INSERT INTO `comment` VALUES ('152', '每个人的青春都有一个“田中君”', '2018-12-02 09:03:00', '4', '12', '70');
INSERT INTO `comment` VALUES ('153', '恶魔少爷的经典款，永不过时', '2018-12-02 09:03:00', '6', '13', '71');
INSERT INTO `comment` VALUES ('154', '喜欢，炸裂我的少女心啊', '2018-12-02 09:03:00', '14', '14', '71');
INSERT INTO `comment` VALUES ('155', '这部剧很有意思，主角配角都很帅啊', '2018-12-02 09:03:00', '14', '15', '72');
INSERT INTO `comment` VALUES ('156', '三季了我一直在追，希望还有后续', '2018-12-02 09:03:00', '14', '16', '73');
INSERT INTO `comment` VALUES ('157', '超级喜欢快斗，感觉他超级厉害啊！！！', '2018-12-02 09:03:00', '16', '17', '74');
INSERT INTO `comment` VALUES ('158', '结局是什么，是还没出吗', '2018-12-02 09:03:00', '16', '18', '74');
INSERT INTO `comment` VALUES ('159', '在哪看？', '2018-12-02 09:03:00', '13', '19', '75');
INSERT INTO `comment` VALUES ('160', '求资源', '2018-12-02 09:03:00', '3', '20', '75');
INSERT INTO `comment` VALUES ('161', '小姐姐，求资源啊', '2018-12-02 09:03:00', '15', '1', '75');
INSERT INTO `comment` VALUES ('162', '贼好看，亚修就是我新的天使', '2018-12-02 09:03:00', '15', '2', '75');
INSERT INTO `comment` VALUES ('163', '剧情很有意思，但感觉画质不是很好啊', '2018-12-02 09:03:00', '13', '3', '76');
INSERT INTO `comment` VALUES ('164', '喜欢佐条！！！', '2018-12-02 09:03:00', '16', '4', '76');
INSERT INTO `comment` VALUES ('165', '支持国产动漫', '2018-12-02 09:03:00', '1', '5', '77');
INSERT INTO `comment` VALUES ('166', '语笑阑珊居然出动漫了，我天啊', '2018-12-02 09:03:00', '4', '6', '77');
INSERT INTO `comment` VALUES ('167', '刚看到第九回……心情难以平复啊~~', '2018-12-02 09:03:00', '6', '7', '77');
INSERT INTO `comment` VALUES ('168', '喜欢这部剧，很励志', '2018-12-02 09:03:00', '8', '8', '78');
INSERT INTO `comment` VALUES ('169', '果然柯南从来都不会让我失望', '2018-12-02 09:03:00', '1', '9', '79');
INSERT INTO `comment` VALUES ('170', '这个是不是要收费啊', '2018-12-02 09:03:00', '6', '10', '79');
INSERT INTO `comment` VALUES ('171', '超级无敌喜欢路飞', '2018-12-02 09:03:00', '14', '11', '80');
INSERT INTO `comment` VALUES ('172', 'got，感谢小姐姐', '2018-12-02 09:03:00', '1', '12', '81');
INSERT INTO `comment` VALUES ('173', '喜欢push it', '2018-12-02 09:03:00', '5', '13', '81');
INSERT INTO `comment` VALUES ('174', '真的管用吗', '2018-12-02 09:03:00', '13', '14', '82');
INSERT INTO `comment` VALUES ('175', '感谢小姐姐，坚持下去，相信自己会瘦的~', '2018-12-02 09:03:00', '15', '15', '82');
INSERT INTO `comment` VALUES ('176', '真的，拉伸拉不好，再减也也必不会瘦的', '2018-12-02 09:03:00', '19', '16', '83');
INSERT INTO `comment` VALUES ('177', '求完整拉伸操动作图解', '2018-12-02 09:03:00', '20', '17', '83');
INSERT INTO `comment` VALUES ('178', '最喜欢的运动就是打网球了~~', '2018-12-02 09:03:00', '2', '18', '84');
INSERT INTO `comment` VALUES ('179', '感觉自己长知识了', '2018-12-02 09:03:00', '3', '19', '84');
INSERT INTO `comment` VALUES ('180', '感谢小姐姐，技能GET', '2018-12-02 09:03:00', '4', '20', '84');
INSERT INTO `comment` VALUES ('181', '短跑是我运动的痛，为什么上了大学还有体测~~', '2018-12-02 09:03:00', '14', '1', '85');
INSERT INTO `comment` VALUES ('182', '亲自试了试，感觉超级管用，感谢作者大大', '2018-12-02 09:03:00', '17', '2', '85');
INSERT INTO `comment` VALUES ('183', '希望下次测试能及格', '2018-12-02 09:03:00', '1', '3', '84');
INSERT INTO `comment` VALUES ('184', '可以说是十分帅气了，男友力上线', '2018-12-02 09:03:00', '2', '4', '86');
INSERT INTO `comment` VALUES ('185', '在哪里学的击剑', '2018-12-02 09:03:00', '25', '5', '86');
INSERT INTO `comment` VALUES ('186', '小姐姐练的时候不要穿这个鞋噢，会很难受的 千万别把脚腕扭了。', '2018-12-02 09:03:00', '5', '6', '86');
INSERT INTO `comment` VALUES ('187', '技能GET,感觉可以自学篮球了。', '2018-12-02 09:03:00', '3', '7', '87');
INSERT INTO `comment` VALUES ('188', '多打篮球，有助于长高', '2018-12-02 09:03:00', '6', '8', '87');
INSERT INTO `comment` VALUES ('189', '羽毛球是根本停不下来的运动', '2018-12-02 09:03:00', '2', '9', '88');
INSERT INTO `comment` VALUES ('190', '衣服在哪买的，有链接吗', '2018-12-02 09:03:00', '6', '10', '88');
INSERT INTO `comment` VALUES ('191', '享受挥汗如雨', '2018-12-02 09:03:00', '2', '11', '88');
INSERT INTO `comment` VALUES ('192', '小姐姐，能不能推荐一下球', '2018-12-02 09:03:00', '6', '12', '88');
INSERT INTO `comment` VALUES ('193', '亚狮龙7不抗打', '2018-12-02 09:03:00', '15', '13', '89');
INSERT INTO `comment` VALUES ('194', '如果没有会滑的人一起会不会不安全呢？', '2018-12-02 09:03:00', '2', '14', '90');
INSERT INTO `comment` VALUES ('195', '人多吗？', '2018-12-02 09:03:00', '6', '15', '91');
INSERT INTO `comment` VALUES ('196', '请问几点关门啊？？？', '2018-12-02 09:03:00', '3', '16', '91');
INSERT INTO `comment` VALUES ('197', '不回溜冰的有人教吗？', '2018-12-02 09:03:00', '3', '17', '91');
INSERT INTO `comment` VALUES ('198', '感觉非常管用，强烈推荐大家试一下', '2018-12-02 09:03:00', '7', '18', '92');
INSERT INTO `comment` VALUES ('199', '都不用去游泳课外班了', '2018-12-02 09:03:00', '8', '19', '92');
INSERT INTO `comment` VALUES ('200', '钓鱼真的是一项修身养性的运动', '2018-12-02 09:03:00', '9', '20', '93');
INSERT INTO `comment` VALUES ('201', '请问要收费吗', '2018-12-02 09:03:00', '4', '1', '93');
INSERT INTO `comment` VALUES ('202', '大大，渔具是要自带的吗', '2018-12-02 09:03:00', '5', '2', '93');
INSERT INTO `comment` VALUES ('203', '喜欢滑雪', '2018-12-02 09:03:00', '0', '3', '94');
INSERT INTO `comment` VALUES ('204', '这个滑雪镜多少钱', '2018-12-02 09:03:00', '6', '4', '94');
INSERT INTO `comment` VALUES ('205', '连体裤滑双板 方便吗？', '2018-12-02 09:03:00', '7', '5', '94');
INSERT INTO `comment` VALUES ('206', '去的话需要预约吗', '2018-12-02 09:03:00', '2', '6', '95');
INSERT INTO `comment` VALUES ('207', '你好 请问攀岩全天营业吗', '2018-12-02 09:03:00', '5', '7', '95');
INSERT INTO `comment` VALUES ('208', '攀岩基地有专门的摄影师吗', '2018-12-02 09:03:00', '5', '8', '95');
INSERT INTO `comment` VALUES ('209', '穿了牛仔短裤可以攀吗', '2018-12-02 09:03:00', '7', '9', '95');
INSERT INTO `comment` VALUES ('210', '请问大概要爬多久呢', '2018-12-02 09:03:00', '7', '10', '95');
INSERT INTO `comment` VALUES ('211', '减肥在于坚持，加油', '2018-12-02 09:03:00', '8', '11', '96');
INSERT INTO `comment` VALUES ('212', '要注意身体啊', '2018-12-02 09:03:00', '9', '12', '96');
INSERT INTO `comment` VALUES ('213', '国内有的，需要可以联系', '2018-12-02 09:03:00', '3', '13', '97');
INSERT INTO `comment` VALUES ('214', '哇 图片太好看了。我以为是哪个3D游戏截图呢', '2018-12-02 09:03:00', '4', '14', '97');
INSERT INTO `comment` VALUES ('215', '宝宝喜欢你的牛仔短裤，有链接吗', '2018-12-02 09:03:00', '14', '15', '97');
INSERT INTO `comment` VALUES ('216', '小姐姐好酷', '2018-12-02 09:03:00', '16', '16', '97');
INSERT INTO `comment` VALUES ('217', '会不会把手臂肌肉练的很突出啊', '2018-12-02 09:03:00', '14', '17', '98');
INSERT INTO `comment` VALUES ('218', '喜欢皮划艇，超级有意思的', '2018-12-02 09:03:00', '13', '18', '99');
INSERT INTO `comment` VALUES ('219', '这个去哪玩比较好啊', '2018-12-02 09:03:00', '11', '19', '99');
INSERT INTO `comment` VALUES ('220', '感觉滑滑板的，不管男生女生都超级帅', '2018-12-02 09:03:00', '12', '20', '100');
INSERT INTO `comment` VALUES ('221', '想要尝试一次啊', '2018-12-02 09:03:00', '12', '1', '100');
INSERT INTO `comment` VALUES ('222', '请问作者大大有推荐的滑板链接吗', '2018-12-02 09:03:00', '11', '2', '100');
INSERT INTO `comment` VALUES ('223', '超级震撼 过山车一样', '2018-12-02 09:03:00', '13', '3', '101');
INSERT INTO `comment` VALUES ('224', '能提供下购买方式吗', '2018-12-02 09:03:00', '16', '4', '101');
INSERT INTO `comment` VALUES ('225', '多少钱啊', '2018-12-02 09:03:00', '3', '5', '101');
INSERT INTO `comment` VALUES ('226', '会不会对眼睛有损伤', '2018-12-02 09:03:00', '6', '6', '101');
INSERT INTO `comment` VALUES ('227', '在哪可以买到？', '2018-12-02 09:03:00', '3', '7', '101');
INSERT INTO `comment` VALUES ('228', '感觉好神奇', '2018-12-02 09:03:00', '7', '8', '102');
INSERT INTO `comment` VALUES ('229', '3D打印需要什么材料吗', '2018-12-02 09:03:00', '4', '9', '102');
INSERT INTO `comment` VALUES ('230', '可以自己打印吗', '2018-12-02 09:03:00', '4', '10', '102');
INSERT INTO `comment` VALUES ('231', '古人的智慧是无穷的', '2018-12-02 09:03:00', '6', '11', '103');
INSERT INTO `comment` VALUES ('232', '-这是不是可以代替人的骨头，瘫痪患者的福音', '2018-12-02 09:03:00', '8', '12', '104');
INSERT INTO `comment` VALUES ('233', '感觉是医学的一大突破', '2018-12-02 09:03:00', '9', '13', '104');
INSERT INTO `comment` VALUES ('234', '好像体验一下', '2018-12-02 09:03:00', '12', '14', '105');
INSERT INTO `comment` VALUES ('235', '感觉电影里的场景都可以实现了', '2018-12-02 09:03:00', '14', '15', '106');
INSERT INTO `comment` VALUES ('236', '好酷！！！', '2018-12-02 09:03:00', '15', '16', '106');
INSERT INTO `comment` VALUES ('237', '好神奇！！', '2018-12-02 09:03:00', '15', '17', '107');
INSERT INTO `comment` VALUES ('238', '推荐一款智能芯片可以当我们身体的健康管家，真的有效哦。', '2018-12-02 09:03:00', '13', '18', '108');
INSERT INTO `comment` VALUES ('239', '遥感是一门科学还是一门技术？', '2018-12-02 09:03:00', '16', '19', '109');
INSERT INTO `comment` VALUES ('240', '是不是有遥感这个专业，就业前景好吗', '2018-12-02 09:03:00', '3', '20', '109');
INSERT INTO `comment` VALUES ('241', '一个小型航怕无人机要多少钱啊？', '2018-12-02 09:03:00', '5', '1', '110');
INSERT INTO `comment` VALUES ('242', '现在无人机够可以应用在农作物打药上了', '2018-12-02 09:03:00', '6', '2', '110');
INSERT INTO `comment` VALUES ('243', '深井潜水泵哪家质量好', '2018-12-02 09:03:00', '7', '3', '111');
INSERT INTO `comment` VALUES ('244', '深井潜水泵怎么拆', '2018-12-02 09:03:00', '8', '4', '111');
INSERT INTO `comment` VALUES ('245', '求深井潜水泵结构图', '2018-12-02 09:03:00', '910', '5', '111');
INSERT INTO `comment` VALUES ('246', '手机怎么看gps卫星地图', '2018-12-02 09:03:00', '12', '6', '112');
INSERT INTO `comment` VALUES ('247', 'gps导航收费吗', '2018-11-13 11:11:07', '11', '7', '112');
INSERT INTO `comment` VALUES ('248', 'GPS卫星导航信号如何被干扰和反干扰', '2018-11-13 11:11:33', '11', '8', '112');
INSERT INTO `comment` VALUES ('249', '智能家居哪家好？', '2018-11-21 11:11:57', '13', '9', '113');
INSERT INTO `comment` VALUES ('250', '机器人会拥有感情吗', '2018-11-20 11:12:14', '15', '10', '114');
INSERT INTO `comment` VALUES ('251', '感觉生活中以后机器人会越来越多', '2018-11-28 11:12:34', '13', '11', '114');
INSERT INTO `comment` VALUES ('252', '原子印章价格是多少啊？', '2018-11-21 11:12:53', '14', '12', '115');
INSERT INTO `comment` VALUES ('253', '那家的院子印章比较好？', '2018-11-21 11:13:09', '15', '13', '115');
INSERT INTO `comment` VALUES ('254', '这是真的吗，想要尝一尝', '2018-10-20 11:13:28', '12', '14', '116');
INSERT INTO `comment` VALUES ('255', '吃了不会有问题吧', '2018-10-18 11:13:58', '12', '15', '116');
INSERT INTO `comment` VALUES ('256', '想吃', '2018-11-21 11:14:17', '15', '16', '116');
INSERT INTO `comment` VALUES ('257', '感觉好神奇', '2018-12-03 11:14:32', '6', '17', '116');
INSERT INTO `comment` VALUES ('258', '空间站一般有几个舱啊', '2018-11-28 11:14:51', '3', '18', '117');
INSERT INTO `comment` VALUES ('259', '轨道舱和返回舱分离后,轨道舱还会回来吗？', '2018-11-14 11:15:07', '6', '19', '117');
INSERT INTO `comment` VALUES ('260', '好好奇宇航员在太空是怎么生活的', '2018-10-18 11:15:27', '4', '20', '118');
INSERT INTO `comment` VALUES ('261', '科技的力量是无穷的', '2018-11-16 11:15:45', '3', '1', '118');
INSERT INTO `comment` VALUES ('262', '量子计算机怎么来的，好神奇~', '2018-11-17 11:16:04', '3', '2', '119');
INSERT INTO `comment` VALUES ('263', '量子力学真的很厉害', '2018-11-22 13:55:54', '6', '3', '119');
INSERT INTO `comment` VALUES ('264', '感谢袁大大~~', '2018-11-23 13:56:24', '8', '4', '120');
INSERT INTO `comment` VALUES ('265', '杂交水稻是什么原理啊？', '2018-11-15 13:57:04', '4', '5', '120');
INSERT INTO `comment` VALUES ('266', '杂交水稻和普通水稻味道上有差别吗', '2018-11-13 13:57:25', '6', '6', '120');
