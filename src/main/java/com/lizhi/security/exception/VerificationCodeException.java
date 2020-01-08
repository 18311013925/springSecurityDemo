package com.lizhi.security.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:35
 * @Description:
 */
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String explanation) {
        super(explanation);
    }


}
