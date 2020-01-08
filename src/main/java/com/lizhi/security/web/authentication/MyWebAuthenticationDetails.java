package com.lizhi.security.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizhi.security.beans.AuthenticationBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: lizhi
 * @Date: 2020/1/8 14:04
 * @Description: 基于图形验证码的场景，我们可以继承 WebAuthenticationDetails 并扩展需要的信息
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private boolean imageCodeIsRight;

    public boolean isImageCodeIsRight() {
        return imageCodeIsRight;
    }

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    // 补充用户提交的验证码和session 保存的验证码
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
//        获取请求的验证码
        String imageCode = obtainCaptcha(request);
        HttpSession session = request.getSession();

        // 取出session中的验证码
        String savedImageCode = (String) session.getAttribute("captcha");
        //真是开发把这个去掉

        if (StringUtils.isNotEmpty(imageCode)) {
            // 清除验证码，不管是失败，还是成功，所有客户端都应在登录失败时刷新验证码
            session.removeAttribute("captcha");

            if (StringUtils.isNotEmpty(imageCode) && imageCode.equals(savedImageCode)) {
                this.imageCodeIsRight = true;
            }
        }
    }

    // 获取验证码
    protected String obtainCaptcha(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("reqCaptcha");
    }


}
