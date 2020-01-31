package com.lizhi.test;


import com.lizhi.SpringSecurityDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author: lizhi
 * @Date: 2020/1/9 14:43
 * @Description:
 */

@SpringBootTest(classes = SpringSecurityDemoApplication.class)
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test() throws SQLException {
        List clientList = stringRedisTemplate.getClientList();
        System.out.println(clientList.toString());
        stringRedisTemplate.opsForSet().add("name1", "12313");


        Set<String> name1 = stringRedisTemplate.opsForSet().members("name1");
        System.out.println(name1.toArray());
    }
}
