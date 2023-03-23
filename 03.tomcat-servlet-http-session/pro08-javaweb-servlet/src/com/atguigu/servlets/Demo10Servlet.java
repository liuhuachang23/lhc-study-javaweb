package com.atguigu.servlets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 10:31 星期四
 * @Operating:
 * @Description: 演示向 session保存作用域 保存数据
 */
@WebServlet("/demo10")
public class Demo10Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        //1.向 session保存作用域 保存数据
        request.getSession().setAttribute("uname", "lili");
        //2.客户端重定向
        //response.sendRedirect("demo11");
        //2.服务器内部转发
        request.getRequestDispatcher("demo11").forward(request,response);
    }

}
