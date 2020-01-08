package com.lizhi.security.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizhi.security.beans.AuthenticationBean;
import com.lizhi.security.config.WebSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: lizhi
 * @Date: 2020/1/7 10:18
 * @Description: 配置json 登录，重写 UsernamePasswordAuthenticationFilter 中的 attemptAuthentication 方法，需要在webSecurityConfig
 * <p>
 * 用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
 * http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
 * {@link WebSecurityConfig}
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        return super.attemptAuthentication(request, response);
        //attempt Authentication when Content-Type is json
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());

                // 将扩展的参数，放到session中，如果不需要，可以删除掉，这是是为了测试图片验证吗
                request.getSession().setAttribute("reqCaptcha", authenticationBean.getCaptcha());

            } catch (IOException e) {
                e.printStackTrace();
                new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                // details 可以携带任意自定义的对象;
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
