package com.atguigu.book.controller;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;
import com.atguigu.book.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:43 星期五
 * @Operating:
 * @Description:
 */

public class UserController {

    private UserService userService = null;
    private CartItemService cartItemService = null;

    //登录验证
    public String login(String uname, String pwd, HttpSession session) {
        User user = userService.getUser(uname, pwd);
        if (user != null) { //验证成功就

            //1. 加载该用户的购物车信息,并设置到 用户的属性中
            Cart cart = cartItemService.getCart(user);
            user.setCart(cart);

            //2. 将用户保存到session中
            session.setAttribute("currUser", user);
            //3. 重定向到BoolController （ operate默认为index 即执行BoolController的index() ）
            return "redirect:book.do?";
        }
        //验证失败 回到login页面
        return "user/login";
    }
}
