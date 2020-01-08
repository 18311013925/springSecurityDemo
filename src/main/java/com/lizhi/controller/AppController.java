package com.lizhi.controller;

import com.lizhi.mapper.UserMapper;
import com.lizhi.model.User;
import com.lizhi.model.UserExample;
import com.lizhi.security.beans.AuthenticationBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/app/api")
public class AppController {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello() {
        return "hello app";
    }

    @PutMapping("/update/pwd")
    public Boolean updatePwd(@RequestBody AuthenticationBean authentication) {
        User user = new User();
        log.info("pwd {} " , authentication.getPassword());
        user.setPassword(passwordEncoder.encode(authentication.getPassword()));
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(authentication.getUsername());

        int i = userMapper.updateByExampleSelective(user, userExample);
        return i == 1;
    }
}
