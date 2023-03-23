package com.atguigu.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 11:04 星期四
 * @Operating:
 * @Description: 演示向 application 保存作用域 保存数据
 */
@WebServlet("/demo12")
public class Demo12Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //向application保存作用域 中保存数据
        ServletContext application = request.getServletContext();
        application.setAttribute("uname","lili");

        //2.客户端重定向
        //response.sendRedirect("demo13");
        //2.服务器内部转发
        request.getRequestDispatcher("demo13").forward(request,response);

    }
}
