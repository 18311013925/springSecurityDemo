package com.lizhi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @author: lizhi
 * @Date: 2020/1/9 18:08
 * @Description:
 */
// 启用基于Redis的httpSession
//@EnableRedisHttpSession
public class HttpSessionConfig {



    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    //SpringSessionBackedSessionRegistry 是session 为spring Security提供的
    // 用于在集群环境下控制会话并发的会话注册表实现类
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }
    // httpSession 的时间监听，用于session提供的会话注册表
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
