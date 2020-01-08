package com.lizhi.controller;

import com.lizhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api")
public class UserController {
    @Autowired
    private UserMapper usersMapper;
    @GetMapping("/hello")
    public String hello() {
//        Page<Users> page = PageHelper.startPage(0, 10);
//        UsersExample example = new UsersExample();
//        List<Users> usersList = usersMapper.selectByExample(example);
//        PageInfo<Users> pageInfo= new PageInfo<>(usersList);
        return "hello user";
    }


}
