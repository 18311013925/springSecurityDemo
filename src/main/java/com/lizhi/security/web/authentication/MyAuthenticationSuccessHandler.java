package com.lizhi.security.web.authentication;

import com.lizhi.security.beans.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lizhi
 * @Date: 2020/1/6 11:21
 * @Description:
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

//        // 允许跨域
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        // 允许自定义请求头token(允许head跨域)
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        log.info("登录成功");

        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        log.info("principal :[{}] role : [{}] ", principal.getUsername(), principal.getRoles());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write("{\"error_code\":\"0\", \"message\":\"欢迎登录系统\"}");
    }

}
