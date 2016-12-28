package com.atguigu.jf.console.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.jf.console.baseapi.demo.mapper.UserMapper;
import com.atguigu.jf.console.demo.bean.pojo.User;
import com.atguigu.jf.console.demo.service.UserService;
@Service
public class userServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	public List<User> queryUserList(Map<String,Object> map) {
		return null;
	}

	@Override
	public Integer queryUserListCount() {
		return null;
	}

	@Override
	public User login(User user) {
		User user2 = userMapper.selectByNameAndPwd(user);
		
		return user2;
	}
	

}
