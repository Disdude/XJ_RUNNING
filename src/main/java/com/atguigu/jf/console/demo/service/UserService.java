package com.atguigu.jf.console.demo.service;

import java.util.List;
import java.util.Map;

import com.atguigu.jf.console.demo.bean.pojo.User;

public interface UserService {
	public List<User> queryUserList(Map<String,Object> map);

	public Integer queryUserListCount();

	public User login(User user);
}
