CREATE DATABASE gov CHARACTER SET UTF8;

CREATE TABLE gov.`init_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `url` varchar(128) NOT NULL DEFAULT '' COMMENT 'url',
  `doc_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'doc id',
  `content` LONGTEXT COMMENT 'content',
  `create_time` datetime NOT NULL DEFAULT '1980-03-10 00:00:00' COMMENT '请求时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='gov抓取数据基本表';