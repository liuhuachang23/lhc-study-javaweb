package com.atguigu.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextServlet1 extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取ServletContext对象
        ServletContext servletContext = getServletContext();
        System.out.println(servletContext);
        // 向  ServletContext 中存储域数据 （ key-value 形式）
        servletContext.setAttribute("key1", "value1");
        //从 ServletContext 中获取域数据 (通过key，获取value)
        Object valueObj = servletContext.getAttribute("key1");
        System.out.println("Context1 中获取域数据key1的值是:"+ valueObj);
    }
}
