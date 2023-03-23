package com.atguigu.cookies_kaptcha.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/17 15:01 星期日
 * @Operating:
 * @Description:
 */
@WebServlet("/cookie01")
public class CookieService01 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 创建cookies对象
        Cookie cookie = new Cookie("uname", "jim");
        //2. 将cookie对象添加到响应中
        response.addCookie(cookie);

        //跳转
        request.getRequestDispatcher("hello01.html").forward(request,response);

    }
}
