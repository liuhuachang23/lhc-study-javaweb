package com.atguigu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 9:05 星期一
 * @Operating:
 * @Description:
 */
//通过注解的方式 接收demo01.do的请求
@WebServlet("/demo02.do")
public class Demo02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("demo02 service...");

        request.getRequestDispatcher("succ.html").forward(request,response);

    }
}
