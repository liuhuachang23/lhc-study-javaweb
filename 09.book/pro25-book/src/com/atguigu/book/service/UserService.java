package com.atguigu.book.service;

import com.atguigu.book.pojo.User;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:44 星期五
 * @Operating:
 * @Description:
 */
public interface UserService {
    //登录验证
    User getUser(String uname, String pwd);
}
