package com.atguigu.jf.console.demo.test;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class TestConnection {

	
	ApplicationContext ac= new ClassPathXmlApplicationContext("spring-config.xml");
	@Test
	public void test () throws SQLException{
		
		DruidDataSource bean = ac.getBean(DruidDataSource.class);
		DruidPooledConnection connection = bean.getConnection();
		System.out.println(connection);
		
	}
	
	
}
