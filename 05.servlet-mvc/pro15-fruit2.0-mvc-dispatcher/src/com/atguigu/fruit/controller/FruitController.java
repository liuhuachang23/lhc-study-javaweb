package com.atguigu.fruit.controller;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/12 20:37 星期日
 * @Operating:
 * @Description:
 */

public class FruitController extends ViewBaseServlet {

    //之前FruitController 是一个Servlet组件(设置了 WebServlet注解)，那么其中的init()一定会被调用
    //并且 init方法内有 super.init() 即调用ViewBaseServlet 的init()
    //现在不是 Servlet组件了，就不会自动调用, 而且没有ServletContext，但是ViewBaseServlet 的init() 需要用到ServletContext
    //所以我们要自己去获取 ServletContext，并且调用 ViewBaseServlet 的init()
    //1.创建一个 ServletContext属性，并给他赋值
    //  1）创建一个 ServletContext属性
    //  2）创建一个 set方法，在 DispatcherServlet中使用反射 调用这个set方法给 ServletContext属性赋值
    //2. 在set方法中调用 ViewBaseServlet 的init() ，并传入ServletContext
    private ServletContext servletContext;
    public void setServletContext(ServletContext servletContext) throws ServletException {
        this.servletContext = servletContext;
        super.init(servletContext);
    }

    //创建一个FruitDAOImpl对象
    FruitDAO fruitDAO = new FruitDAOImpl();

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //默认页面为1(默认显示第一页)
        Integer pageNo = 1;

        HttpSession session = request.getSession();

        //获取oper参数
        String oper = request.getParameter("oper");
        //如果oper不为空，就代表此时是 查询关键字操作发过来的请求
        //如果为空，就表示此时 不是 查询关键字操作发过来的请求
        String keyword = null;
        if (!StringUtil.isEmpty(oper) && "search".equals(oper)){
            //是查询关键字操作发过来的请求
            //此时 pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        } else {
            //不是是查询关键字操作发过来的请求 (比如点击下面的 上一页，下一页..)
            //此时keyword应该从session保存作用域获取
            //获取当前页码
            String pageNoStr = request.getParameter("pageNo");
            if (!StringUtil.isEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }
            //从会话作用域中获取 keyword
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null){
                keyword = (String) keywordObj;
            }else {
                keyword = "";
            }

        }

        //将pageNo保存到 会话保存作用域中(用于在页面上获取)
        session.setAttribute("pageNo",pageNo);


        //1. 调用 getFruitList() 方法，得到一个存储Fruit对象的list集合 （一个Fruit对象代表数据库中的一条记录）
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword, pageNo);
        //2. 将fruitList集合 以k-v的形式 存储到 会话保存作用域 中
        session.setAttribute("fruitList",fruitList);

        //获取总记录条数
        int fruitCount = fruitDAO.getFruitCount(keyword);
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取参数
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");

        //执行添加
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);

        //资源跳转
        response.sendRedirect("fruit.do");
    }

    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (!StringUtil.isEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            //根据fid 删除水果
            fruitDAO.delFruit(fid);
            //使用客户端重定向，重新给IndexServlet发送请求，重新获取fruitList，然后覆盖到session中，更新index.html页面
            response.sendRedirect("fruit.do");
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        response.sendRedirect("fruit.do");

    }
}
