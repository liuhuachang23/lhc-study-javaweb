package com.atguigu.book.controller;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;
import com.atguigu.book.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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

    /**
     * 用户注册功能
     *
     * @param uname      用户名
     * @param pwd        密码
     * @param email      邮箱
     * @param verifyCode 验证码
     * @param session    session作用域
     * @param response   响应
     * @return 跳转地址
     */
    public String regist(String uname, String pwd, String email, String verifyCode, HttpSession session, HttpServletResponse response) throws IOException {
        //获取session中保存的验证码
        Object kaptchaCodeObj = session.getAttribute("KAPTCHA_SESSION_KEY");
        //比较，是否和输入的验证码一致
        if (kaptchaCodeObj == null || !kaptchaCodeObj.equals(verifyCode)) {
            //不一致: 弹窗提示 "验证码不正确" 并且跳转回注册页面
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            //out.println("<script language='javascript'>alert('验证码不正确！');window.location.href='page.do?operate=page&page=user/regist'</script>");
            out.println("<script language='javascript'>alert('验证码不正确！')</script>");
            //这里需要注意：
            // 1）在上面标签中跳转的话，下面就要 return null 或者 "", 但是在DispatcherServlet中视图处理的时候 需要判断 当返回值为空时不需要视图处理;
            // 2）如果不在上面标签中跳转 在下面跳转就直接 return "user/regist"; 但是这种做法 可能会导致客户端还没打印出弹窗提示 就已经进行跳转了
            //return "";
            return "user/regist";
        } else {
            // 一致：就进行新用户注册 并跳转到登录界面
            if (kaptchaCodeObj.equals(verifyCode)) {
                userService.regist(new User(0, uname, pwd, email, 0));
                return "user/login";
            }
        }
        return "user/login";
    }

    /**
     * 验证用户名是否已被注册 , 客户端 (regist.js中 ckUname()方法) 向服务器端发送异步请求
     * @param uname 想要注册的用户名
     * @return 返回到中央控制器做视图处理 json开头的返回值 会进行字符串分割 再通过流 响应给客户端
     * 客户端在 回调函数中可以获取到响应数据,从而做出判断并响应到html页面
     */
    public String ckUname(String uname){
        User user = userService.getUserByUname(uname);
        if (user != null){
            //用户名已经被注册 就返回"json:{'uname':'1'}"  给DispatcherServlet做视图处理
            //return "ajax:1";
            return "json:{'uname':'1'}";
        } else {
            //用户名可以被注册 就返回"json:{'uname':'0'}" 给DispatcherServlet做视图处理
            //return "ajax:0";
            return "json:{'uname':'0'}";
        }
    }


}
