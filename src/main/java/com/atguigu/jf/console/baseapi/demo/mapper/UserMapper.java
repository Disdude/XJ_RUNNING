package com.atguigu.jf.console.baseapi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.jf.console.demo.bean.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long opId);

    int insert(User record);

    User selectByPrimaryKey(Long opId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User selectByNameAndPwd(@Param("user")User user);
}