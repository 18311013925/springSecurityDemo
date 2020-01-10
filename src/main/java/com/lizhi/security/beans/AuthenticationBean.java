package com.lizhi.security.beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: lizhi
 * @Date: 2020/1/7 10:34
 * @Description:
 */
@Getter
@Setter
public class AuthenticationBean implements Serializable {
    private String username;
    private String password;
    private String captcha;
    private String rememberme;
}
