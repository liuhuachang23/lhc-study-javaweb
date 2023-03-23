package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/6 16:51 星期一
 * @Operating:
 * @Description: 演示会话Session
 */
public class Demo03Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取Session,获取不到就会创建一个新会话
        HttpSession session = request.getSession();
        System.out.println(session.getId());
    }
}
