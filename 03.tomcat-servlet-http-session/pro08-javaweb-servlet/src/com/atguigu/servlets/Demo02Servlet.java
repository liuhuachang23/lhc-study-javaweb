package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/6 13:46 星期一
 * @Operating:
 * @Description: Servlet的生命周期
 */
public class Demo02Servlet extends HttpServlet {

    public /*private*/ Demo02Servlet(){
        System.out.println("正在实例化...");
    }

    //重写init()
    @Override
    public void init() throws ServletException {
        System.out.println("正在初始化");
    }

    //重写service()
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("正在服务");
    }

    //重写destroy()
    @Override
    public void destroy() {
        System.out.println("正在销毁");
    }
}
