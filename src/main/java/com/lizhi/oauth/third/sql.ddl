CREATE TABLE `t_secret_key` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) NOT NULL,
  `public_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '公钥 | 庞跃强 |  2017-05-17',
  `private_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '私钥 | 庞跃强 | 2017-05-17',
  `create_by_id` bigint(20) NOT NULL COMMENT '创建人|庞跃强 | 2017-05-17',
  `update_by_id` bigint(20) NOT NULL COMMENT '更新人| 庞跃强 | 2017-05-17',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_index_tenantId` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='第三方系统免密登陆加密秘钥';

