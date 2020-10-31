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


create table t_secret_key
(
    id           bigint unsigned auto_increment
        primary key,
    tenant_id    bigint                                  not null,
    public_key   varchar(1024) default ''                not null comment '公钥 | 庞跃强 |  2017-05-17',
    private_key  varchar(1024) default ''                not null comment '私钥 | 庞跃强 | 2017-05-17',
    create_by_id bigint                                  not null comment '创建人|庞跃强 | 2017-05-17',
    update_by_id bigint                                  not null comment '更新人| 庞跃强 | 2017-05-17',
    create_at   datetime not null comment '创建时间',
    update_at    timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint uniq_index_tenantId
        unique (tenant_id)
) comment '第三方系统免密登陆加密秘钥' charset = utf8;


    -- auto-generated definition
create table third_sso_relation
(
    id            bigint auto_increment comment '系统内部ID'
        primary key,
    tenant_id     bigint       null comment '微办公公司ID',
    user_id       bigint       null comment '微办公用户ID',
    third_account varchar(255) not null comment '第三方账号和微办公的唯一关联,目前仅关联hro',
    status        int          null comment '状态',
    created_by_id bigint       null comment '创建帐号ID',
    created_at    datetime  null,
    updated_by_id bigint       null comment '更新帐号ID',
    updated_at    datetime   null
)
    comment '第三方账号免密登录关联表' charset = utf8;

create index third_account
    on third_sso_relation (third_account);




