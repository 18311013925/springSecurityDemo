package com.lizhi.security.web.authentication;

import com.lizhi.security.exception.SecurityErrot;
import com.lizhi.security.exception.VerificationCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author: lizhi
 * @Date: 2020/1/8 11:28
 * @Description:  自定义一些自己的认证，验证验证码等， 其他流程并没有变化，
 * DaoAuthenticationProvider 是 继承了AbstractUserDetailsAuthenticationProvider 内部实现了账号密码等一系列的验证
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider{

    // 把构造方法注入 UserDetailsService 和 PasswordEncoder
    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 实现自己的认证逻辑，比如验证码是否正确
        // 在这里可能会遇到一个问题，我们如何携带 验证吗信息??  Authentication Object getDetails();可以携带任意对象
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        if (!details.isImageCodeIsRight()) {
            throw new VerificationCodeException(SecurityErrot.imageCode);
        }

        // 调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
