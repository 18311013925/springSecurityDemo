package com.lizhi.test;


import com.lizhi.SpringDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: lizhi
 * @Date: 2020/1/9 14:43
 * @Description:
 */

@SpringBootTest(classes = SpringDemoApplication.class)
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Autowired
    private DataSource dataSource;
    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection.getClass());
        System.out.println(dataSource.getClass());

    }
}
