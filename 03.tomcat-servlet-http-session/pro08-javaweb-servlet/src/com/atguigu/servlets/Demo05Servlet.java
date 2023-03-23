package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/6 20:06 星期一
 * @Operating: 服务器端demo05组件
 * @Description: 演示从HttpSession保存作用域 中获取数据
 */
public class Demo05Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object unameObj = session.getAttribute("uname");
        System.out.println(unameObj);
    }
}
