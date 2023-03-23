package com.atguigu.book.service.impl;

import com.atguigu.book.dao.UserDAO;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.UserService;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:47 星期五
 * @Operating:
 * @Description:
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO = null;


    @Override
    public User getUser(String uname, String pwd) {
        return userDAO.getUser(uname,pwd);
    }
}
