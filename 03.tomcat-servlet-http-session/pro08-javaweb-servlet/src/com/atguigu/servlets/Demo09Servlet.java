package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 9:28 星期四
 * @Operating:
 * @Description: 演示向  request保存作用域 取数据
 */
@WebServlet("/demo09")
public class Demo09Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取 request保存作用域 保存的数据，key为uname
        Object unameObj = request.getAttribute("uname");
        //当 在在demo08Servlet 中使用 客户端重定向时(两次请求响应)，获取不到 request保存作用域 中的数据
        //但改成 服务器端内部转发(一次请求响应)，这里可以获取到 数据
        //即 request保存作用域 范围是：一次请求响应
        System.out.println(unameObj);
    }
}
