CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `enable` tinyint(4) DEFAULT NULL,
  `roles` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';



CREATE TABLE `persistent_logins` (
  `series` VARCHAR(64) PRIMARY KEY,
  `username` VARCHAR(50) COLLATE utf8_bin NOT NULL,

  `token` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL,
  `last_used` TIMESTAMP NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户自动登录信息表';