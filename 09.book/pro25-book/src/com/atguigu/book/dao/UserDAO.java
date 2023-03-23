package com.atguigu.book.dao;

import com.atguigu.book.pojo.User;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:44 星期五
 * @Operating:
 * @Description:
 */
public interface UserDAO {

    User getUser(String uname, String pwd);
}
