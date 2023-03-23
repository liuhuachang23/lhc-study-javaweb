package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 9:27 星期四
 * @Operating:
 * @Description: 演示向 request保存作用域 保存数据
 */
@WebServlet("/demo08")
public class Demo08Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.向 request保存作用域 保存数据
        request.setAttribute("uname", "lili");
        //2.客户端重定向
        //response.sendRedirect("demo09");
        //2.服务器内部转发
        request.getRequestDispatcher("demo09").forward(request,response);
    }
}
