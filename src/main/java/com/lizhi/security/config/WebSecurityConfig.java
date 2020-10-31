package com.lizhi.security.config;

import com.lizhi.security.web.authentication.CustomAuthenticationFilter;
import com.lizhi.security.web.authentication.MyAuthenticationFailHandler;
import com.lizhi.security.web.authentication.MyAuthenticationSuccessHandler;
import com.lizhi.security.web.authentication.MyInvalidSessionStrategy;
import com.lizhi.security.web.authentication.rememberme.MyPersistentTokenBasedRememberMeServices;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.http.HttpServletRequest;



@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myWebAuthenticationDetails;

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 应用 authenticationProvider
        auth.authenticationProvider(authenticationProvider);

    }

    @Autowired
    private HikariDataSource dataSource;

//    @Autowired
//    private SpringSessionBackedSessionRegistry redisSessionRegistry;


    @Override
    protected void configure(HttpSecurity http) throws Exception /**/{
        http.authorizeRequests()

                .antMatchers("/admin/api/**").hasRole("ADMIN") // 需要具有ROLE_ADMIN角色才能访问
//                .antMatchers("/admin/api/**").hasAuthority("ADMIN") // 需要具有ROLE_ADMIN角色才能访问
                .antMatchers("/user/api/**").hasRole("USER")  // 需要具有ROLE_USER角色才能访问
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
                .antMatchers("/remember/api/**").rememberMe()
                .anyRequest().authenticated()
                .and().formLogin().disable()
                .csrf().disable()
                // 使用session 提供的会话注册表
                .sessionManagement().invalidSessionStrategy(new MyInvalidSessionStrategy());


        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {

        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailHandler);
        filter.setAuthenticationDetailsSource(myWebAuthenticationDetails);
        // 记住当前用户，实现自动登录
        filter.setRememberMeServices(rememberMeServices());
        filter.setFilterProcessesUrl("/login");
        // 实现自动登录

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        return bCryptPasswordEncoder;
    }


    //
    @Bean
    public RememberMeServices rememberMeServices() {
//        这个key 我们自己指定
//        return new MyTokenBasedRememberMeServices("blurooo", myUserDetailsService);
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return new MyPersistentTokenBasedRememberMeServices("blurooo", myUserDetailsService, jdbcTokenRepository);
    }

   /* @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message");
        return messageSource;
    }*/


}
