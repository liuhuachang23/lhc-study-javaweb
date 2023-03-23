package com.atguigu.servlets;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/5 13:53 星期日
 * @Operating:
 * @Description:
 */
public class AddServlets extends HttpServlet { //继承HttpServlet 前要配置驱动包，依赖到该项目中

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
        //get方式下，tomcat8中 不需要设置编码
        //在tomcat7中 ：
        String fname = request.getParameter("fname");
        //1.将字符串大散成字节数组
        byte[] bytes = fname.getBytes("IS0-8859-1");
        //2.将字节数组按照设定编码重新组装成字符串
        fname = new String(bytes, "UTF-8");
        */

        //post方式下，设置编码，防止中文乱码
        //需要注意的是，post设置编码，必须放在所有的获取参数动作之前，即只能放在第一行
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        boolean flag = fruitDAO.addFruit(new Fruit(0, fname, price, fcount, remark));

        System.out.println(flag ? "添加成功" : "添加失败");
    }
}
