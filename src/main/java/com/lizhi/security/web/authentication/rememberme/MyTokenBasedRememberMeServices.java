package com.lizhi.security.web.authentication.rememberme;

import com.lizhi.security.beans.AuthenticationBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lizhi 自动登录，散列加密方案
 * @Date: 2020/1/8 18:01
 * @Description: 实现自动登录，将用户信息保存在浏览器的cookie 中，
 * 因为我们是前后端分离， 所以我们这里需要扩展 TokenBasedRememberMeServices 重写rememberMeRequested方法，
 *
 */
public class MyTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    public MyTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        AuthenticationBean authenticationBean = (AuthenticationBean) request.getSession().getAttribute("authenticationBean");
        if (authenticationBean == null) {
            return false;
        }
        String paramValue = authenticationBean.getRememberme();
        if (paramValue != null) {
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                    || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                return true;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Did not send remember-me cookie (principal did not set parameter '"
                    + parameter + "')");
        }

        return false;
    }

}
