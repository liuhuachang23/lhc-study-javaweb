package com.atguigu.fruit.servlet;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/10 10:03 星期五
 * @Operating:
 * @Description: 接收和响应 del.do请求(点击删除事件) 的服务器组件
 */
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (!StringUtil.isEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            //根据fid 删除水果
            fruitDAO.delFruit(fid);
            //使用客户端重定向，重新给IndexServlet发送请求，重新获取fruitList，然后覆盖到session中，更新index.html页面
            response.sendRedirect("index");
        }
    }
}
