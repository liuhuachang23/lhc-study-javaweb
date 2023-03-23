package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 10:32 星期四
 * @Operating:
 * @Description: 演示向 session保存作用域 获取数据
 */
@WebServlet("/demo11")
public class Demo11Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取 session保存作用域 保存的数据，key为uname
        Object unameObj = request.getSession().getAttribute("uname");
        //在demo10Servlet 中使用 客户端重定向时(两次请求响应)，和 服务器端内部转发(一次请求响应)，都可以获取到 数据
        //但是在 别的客户端(新会话) 访问 直接demo11组件，是获取不到数据的
        //即 session保存作用域 范围是：一次会话访问
        System.out.println(unameObj);
    }
}