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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/7 16:36 星期二
 * @Operating:
 * @Description: 接收和响应 index.do请求 的服务器组件
 */
//Servlet从3.0开始就支持注解的方式注册
//就可以 不用去 web.xml配置文件中 将IndexServlet 和 index关联了
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //默认页面为1(默认显示第一页)
        Integer pageNo = 1;
        //获取当前页码
        String pageNoStr = request.getParameter("pageNo");
        if (!StringUtil.isEmpty(pageNoStr)){
            pageNo = Integer.parseInt(pageNoStr);
        }
        //将pageNo保存到 会话保存作用域中(用于在页面上获取)
        HttpSession session = request.getSession();
        session.setAttribute("pageNo",pageNo);

        //创建一个FruitDAOImpl对象
        FruitDAO fruitDAO = new FruitDAOImpl();
        //1. 调用 getFruitList() 方法，得到一个存储Fruit对象的list集合 （一个Fruit对象代表数据库中的一条记录）
        List<Fruit> fruitList = fruitDAO.getFruitList(pageNo);
        //2. 将fruitList集合 以k-v的形式 存储到 会话保存作用域 中
        session.setAttribute("fruitList",fruitList);

        //获取总记录条数
        int fruitCount = fruitDAO.getFruitCount();
        //计算总页数
        int pageCount = (fruitCount + 5 -1)/5;
        //将pageCount保存到 会话保存作用域中(用于在页面上获取)
        session.setAttribute("pageCount",pageCount);

        //3. 调用父类ViewBaseServlet 的processTemplate() 方法,
        // 该方法作用：在index.html静态页面 上加载java内存中的 fruitList数据
        /**
         *  "index" 为视图名称，thymeleaf会将这个 逻辑视图名称 对应到 物理视图名称 上去
         *  物理视图=视图前缀+逻辑视图+视图后缀
         *        =   /     index   .html
         */
        super.processTemplate("index",request,response);

    }
}
