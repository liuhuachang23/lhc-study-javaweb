package com.atguigu.fruit.servlet;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 20:23 星期四
 * @Operating:
 * @Description: 接收和响应 update.do请求 的服务器组件
 */

//使用注解的方式将 edit.html中表单提交地址(update.do) 和 UpdateServlet 关联起来
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码格式
        request.setCharacterEncoding("utf-8");

        //获取参数值
        int fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        //执行更新
        fruitDAO.updateFruit(new Fruit(fid,fname,price,fcount,remark));

        //3.资源跳转
        // 调用父类ViewBaseServlet 的processTemplate() 方法,
        // 该方法作用：在index.html静态页面 上加载java内存中的 fruitList数据
        //相对于 request.getRequestDispatcher("index.html").forward(request,response)
        // 如果使用 服务器端内部转发，index.html仍然是更新之前的数据，所以是不可行的
        //super.processTemplate("index", request, response);

        //改成 客户端重定向,目的是重新给IndexServlet发送请求，重新获取fruitList，然后覆盖到session中，这样页面上的index.html才是更新后的
        response.sendRedirect("index");



    }
}
