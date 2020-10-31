package com.lizhi.controller;

import com.lizhi.mapper.UserMapper;
import com.lizhi.model.User;
import com.lizhi.model.UserExample;
import com.lizhi.security.beans.AuthenticationBean;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/api")
public class AppController {

    @Resource
    private UserMapper userMapper;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @GetMapping("/hello")
    public String hello() {
        return "hello app";
    }

    @PutMapping("/update/pwd")
    public Boolean updatePwd(@RequestBody AuthenticationBean authentication) {
        User user = new User();
        log.info("pwd {} ", authentication.getPassword());
        user.setPassword(passwordEncoder.encode(authentication.getPassword()));
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(authentication.getUsername());
        int i = userMapper.updateByExampleSelective(user, userExample);
        return i == 1;
    }

    @GetMapping("/query/{id}")
    public User queryUser(@PathVariable Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @PutMapping("/insert/batch")
    public Boolean insertBatch(@RequestBody AuthenticationBean authentication) {
        List<AuthenticationBean> authenticationBeans = new ArrayList<>();
        authenticationBeans.add(authentication);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            for (AuthenticationBean item : authenticationBeans) {
            }
            sqlSession.commit();
        }
        return false;
    }

    @PutMapping("/insert")
    public Boolean insert(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int insert = userMapper.insert(user);
        return insert == 1;
    }


}
