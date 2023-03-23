package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/7 8:25 星期二
 * @Operating: 服务器端demo06组件
 * @Description: 演示 服务器内部转发、客户端重定向
 */
public class Demo06Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("demo06...");
        //服务器端内部转发
        request.getRequestDispatcher("demo07").forward(request, response);
        //客户端重定向
        //response.sendRedirect("demo07");
    }
}