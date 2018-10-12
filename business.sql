
# 用户表
CREATE TABLE neuedu_user(
	`id` INT(11) NOT NULL auto_increment COMMENT '用户表id',
	`username` VARCHAR(50) NOT NULL COMMENT '用户名',
	`password` VARCHAR(50) NOT NULL COMMENT '用户名密码，MD5加密',
	`email` VARCHAR(50) DEFAULT NULL '用户email',
	`phone` VARCHAR(20) DEFAULT NULL '用户phone',
	`question` VARCHAR(100) DEFAULT NULL COMMENT '找回密码问题',
	`answer` VARCHAR(100)  DEFAULT NULL COMMENT '找回密码答案',
	`role` int(4) NOT NULL COMMENT '角色：0-管理员，1-普通客户',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`update_time` datetime NOT NULL COMMENT '最后一次更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `user_name_unique` (`username`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT=21 DEFAULT CHARSET = UTF8;

ALTER TABLE neuedu_user ADD COLUMN token VARCHAR (50);

insert into neuedu_user (username, password, email, phone, question, answer, role, create_time, update_time) VALUES
('admin','admin','xxx@126.com','13514785296','你的大学班主任','张三',0,now(),now());

# 分类表
CREATE TABLE neuedu_category(
	`id` INT(11) NOT NULL auto_increment COMMENT '类别id',
	`parent_id` INT(11) DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点，一级类别',
	`name` VARCHAR(50) DEFAULT NULL COMMENT '类别名称',
	`status` TINYINT(1) DEFAULT '1' COMMENT '类别状态：1-正常，2-已废弃',
	`sort_order` INT(4) DEFAULT NULL COMMENT '排序编号，同类展示顺序，数值相等则自然排序',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`update_time` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)ENGINE = INNODB AUTO_INCREMENT=10032 DEFAULT CHARSET = UTF8;

# 产品表
CREATE TABLE neuedu_product(
	`id` INT(11) NOT NULL auto_increment COMMENT '商品id',
	`category_id` INT(11) NOT NULL COMMENT '分类id，对应mmall_category表的主键',
	`name` VARCHAR(100) NOT NULL COMMENT '商品名称',
	`subtitle` VARCHAR(200) DEFAULT NULL COMMENT '商品副标题',
	`main_image` VARCHAR(500) DEFAULT NULL COMMENT '产品主图，url相对地址',
	`sub_images` text COMMENT '图片地址，json格式，扩展用',
	`detail` text COMMENT '商品详情',
	`price` DECIMAL(20,2) NOT NULL COMMENT '价格，单位-元保留两位小数',
	`stock` INT(11) NOT NULL COMMENT '库存数量',
	`status` INT(6) DEFAULT '1' COMMENT '商品状态：1-在售，2-下架，3-删除',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`update_time` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)ENGINE = INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET = UTF8;

#id查询商品信息
select p.id productid,p.category_id,p.name,p.subtitle,p.main_image,p.sub_images,p.detail,p.price,p.stock,p.status,p.create_time,p.update_time,
c.id categoryid,c.parent_id,c.name,c.status,c.sort_order,c.create_time,c.update_time
from neuedu_product p
join neuedu_category c
on p.category_id=c.id
where p.id=10000;

#查询类别下的所有商品
    Category
      List<Product> productList;
select p.id productid,p.category_id,p.name,p.subtitle,p.main_image,p.sub_images,p.detail,p.price,p.stock,p.status,p.create_time,p.update_time,
c.id categoryid,c.parent_id,c.name,c.status,c.sort_order,c.create_time,c.update_time
from neuedu_category c
LEFT JOIN neuedu_product p
on p.category_id=c.id
where c.id=10032;

CREATE TABLE neuedu_cart(
	`id` INT(11) NOT NULL auto_increment,
	`user_id` INT(11) NOT NULL,
	`product_id` INT(11) NOT NULL COMMENT '商品id',
	`quantity` INT(11) DEFAULT NULL COMMENT '数量',
	`checked` INT(11) DEFAULT NULL COMMENT '是否选择：1=以勾选，0=未勾选',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`update_time` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`),
	KEY `user_id index` (`user_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT=121 DEFAULT CHARSET = UTF8;

create table shopping(
	`id` int(11) not null auto_increment,
	`user_id` int(11) DEFAULT NULL comment '用户ID',
	`receiver_name` varchar(20) DEFAULT NULL comment '收货姓名',
	`receiver_phone` varchar(20) DEFAULT NULL comment '收货固定电话',
	`receiver_mobile` varchar(20) DEFAULT NULL comment '收货移动电话',
	`receiver_province` varchar(20) DEFAULT NULL comment '省份',
	`receiver_city` varchar(20) DEFAULT NULL comment '城市',
	`receiver_district` varchar(20) DEFAULT NULL comment '区/县',
	`receiver_address` varchar(200) DEFAULT NULL comment '详细地址',
	`receiver_zip` varchar(6) DEFAULT NULL comment '邮编',
	`create_time` datetime default null comment '创建时间',
	`update_time` datetime default null comment '更新时间',
	PRIMARY KEY (`id`)
)ENGINE = INNODB AUTO_INCREMENT=32 DEFAULT CHARSET = UTF8;

