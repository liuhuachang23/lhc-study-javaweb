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
 * @Description: 演示向 application 保存作用域 获取数据
 */
@WebServlet("/demo13")
public class Demo13Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //向application保存作用域 中获取数据
        ServletContext application = request.getServletContext();
        //在demo12Servlet 中使用 客户端重定向时(两次请求响应)，和 服务器端内部转发(一次请求响应)，客户端访问demo12都可以获取到 数据
        //在 别的客户端(新会话) 访问 直接demo13组件，也可以获取到数据的
        //即 application保存作用域 范围是：一次应用程序范围有效
        Object unameObj = application.getAttribute("uname");
        System.out.println(unameObj);


    }
}
