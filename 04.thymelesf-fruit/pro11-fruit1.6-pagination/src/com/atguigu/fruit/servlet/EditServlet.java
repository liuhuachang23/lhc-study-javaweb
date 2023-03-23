package com.atguigu.fruit.servlet;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 18:54 星期四
 * @Operating:
 * @Description: 接收和响应 edit.do修改表单请求 的服务器组件
 */
//使用注解的方式将 index.html中超链接(<a href="edit">) 和 EditServlet 关联起来
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO =  new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (!StringUtil.isEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            //通过fid 获取 指定的水果对象
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            //将fruit，保存到 request保存作用域 中
            request.setAttribute("fruit", fruit);
            //调用父类ViewBaseServlet 的processTemplate() 方法,
            // 该方法作用：在edit.html静态页面 上加载java内存中的 fruit对象
            super.processTemplate("edit", request,response);
        }
    }
}
