package com.lizhi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lizhi
 * @Date: 2020/1/10 18:18
 * @Description: 测试记住当前用户允许访问
 */
@RestController
@RequestMapping("/remember/api")
public class RememberMeController {
    @GetMapping("/hello")
    public String hello() {
        return "hello remember";
    }
}
