package com.lizhi.security.web.authentication.rememberme;

import com.lizhi.security.beans.AuthenticationBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lizhi
 * @Date: 2020/1/9 16:45
 * @Description: 持久化令牌方案
 */
public class MyPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {


    public MyPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
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
