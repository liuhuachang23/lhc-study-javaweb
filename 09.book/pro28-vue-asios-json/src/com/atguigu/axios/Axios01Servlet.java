package com.atguigu.axios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/22 14:01 星期五
 * @Operating:
 * @Description: 服务器端
 */
@WebServlet("/axios01.do")
public class Axios01Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收客户端传输过来的值之前 设置编码
        request.setCharacterEncoding("utf-8");
        //获取接收值
        String uname = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        //打印
        System.out.println("uname = " + uname);
        System.out.println("pwd = " + pwd);

        //响应给客户端之前 也设置一下编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获取一个写入流,写入数据 响应给客户端
        PrintWriter out = response.getWriter();
        out.write(uname+"_"+pwd);

        //故意抛出一个异常 让 axiosDame01.html中 catch到
        throw new NullPointerException("这里故意抛出一个空指针异常.....");

    }
}
