package com.aiguigu.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/14 20:30 星期二
 * @Operating:
 * @Description:
 */
//使用注解的方式配置参数 和 在web.xml配置文件中配置参数，效果一样
    //urlPatterns 就相对于一个或多个 <url-pattern></url-pattern>
    //initParams 就相对于一个或多个 <init-param></url-pattern>
@WebServlet(urlPatterns = {"/demo01"},
        initParams = {
            @WebInitParam(name = "hello", value = "world"),
            @WebInitParam(name = "uname", value = "lhc")
        }
        )
public class Demo01Servlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String initValue = config.getInitParameter("hello");
        System.out.println("初始化参数值：" + initValue);
    }
}
