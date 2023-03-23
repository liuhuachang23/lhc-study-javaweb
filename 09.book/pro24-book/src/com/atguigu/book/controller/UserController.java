package com.atguigu.book.controller;

import com.atguigu.book.pojo.User;
import com.atguigu.book.service.UserService;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:43 星期五
 * @Operating:
 * @Description:
 */

public class UserController {

    private UserService userService = null;

    public String login(String uname, String pwd){
        User user = userService.getUser(uname, pwd);
        System.out.println(user);
        return "index";
    }
}
