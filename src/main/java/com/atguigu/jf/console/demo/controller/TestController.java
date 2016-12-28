package com.atguigu.jf.console.demo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.jf.console.demo.bean.pojo.User;
import com.atguigu.jf.console.demo.service.UserService;

@Controller
public class TestController {
	@Autowired
	UserService userService;
	
	
	
	@RequestMapping(value="/user/queryUserCombox",method=RequestMethod.GET)
	@ResponseBody
	public List<JSONObject> queryUserCombox(@DateTimeFormat(iso=ISO.DATE,pattern="yyyy-MM-dd HH:mm:ss")Date dateStr){
		Map<String,Object> map = new HashMap<String, Object>();
		List<User> ulist = userService.queryUserList(map);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject obj = null;
//		for (User user : ulist) {
//			obj = new JSONObject();
//			obj.put("id", user.getId());
//			obj.put("name", user.getName());
//			obj.put("age", user.getAge());
//			obj.put("sex", user.getSex());
//			list.add(obj);
//		}
		return list;
	}
	@RequestMapping(value="/user/queryUserList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject queryUserList(int start,int limit){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		List<User> ulist = userService.queryUserList(map);
		JSONObject obj = new JSONObject();
		obj.put("total", userService.queryUserListCount());
		obj.put("rows", ulist);
		return obj;
	}
}
