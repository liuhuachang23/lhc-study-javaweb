package com.atguigu.book.dao;

import com.atguigu.book.pojo.User;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:44 星期五
 * @Operating:
 * @Description:
 */
public interface UserDAO {

    //登录验证（通过账号、密码 获取用户）
    User getUser(String uname, String pwd);
    //注册新用户
    void addUser(User user);
    //通过用户名 获取用户 (用于注册时 判断是否存在同名用户)
    User getUserByUname(String uname);
}
