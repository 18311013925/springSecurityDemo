package com.lizhi.service;

import com.lizhi.security.beans.SecurityUser;

/**
 * @author: lizhi
 * @Date: 2020/1/7 10:03
 * @Description:
 */
public interface UserService {

    SecurityUser login(String username, String password);
}

