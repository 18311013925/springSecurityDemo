package com.lizhi.security.userdetails;

import com.lizhi.security.beans.SecurityUser;
import com.lizhi.mapper.UserMapper;
import com.lizhi.model.User;
import com.lizhi.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lizhi
 * @Date: 2020/1/6 14:54
 * @Description:
 */
@Service
public class MyUserDetailsService implements UserDetailsService{

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SecurityUser userInfo = new SecurityUser();
        BeanUtils.copyProperties(users.get(0), userInfo);

        //解析 roles, 将数据库形式的roles 解析为UserDetails 的权限集
        // AuthorityUtils.commaSeparatedStringToAuthorityList 是Spring security 提供的，该方法用提将逗号隔开的权限集切割成可用权限对像列表， 也可以自己是吸纳，参考generateAuthorities
        userInfo.setAuthorityList(AuthorityUtils.commaSeparatedStringToAuthorityList(userInfo.getRoles()));
        return userInfo;
    }

    /**
     * 自行实现权限的转换
     * SimpleGrantedAuthority 是 GrantedAuthority 的一个实现类
     * @param roles
     * @return
     */
    private List<GrantedAuthority> generateAuthorities(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String[] roleArray = roles.split(";");
        if (roleArray != null && "".equals(roles)) {
            for (String role : roleArray) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }
}
