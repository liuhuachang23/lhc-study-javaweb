package com.atguigu.fruit.controller;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/12 20:37 星期日
 * @Operating:
 * @Description:
 */

public class FruitController {

    //创建一个FruitDAOImpl对象
    FruitDAO fruitDAO = new FruitDAOImpl();

    private String index(String oper, String keyword, Integer pageNo, HttpServletRequest request){

        //1. 获取请求参数，改为方法的形参，统一由中央控制器 传入

        //没有传参，就默认为第一页
        if (pageNo == null){
            pageNo = 1;
        }

        HttpSession session = request.getSession();

        if (!StringUtil.isEmpty(oper) && "search".equals(oper)) {
            //是查询关键字操作发过来的请求
            //此时 pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo = 1;
            //如果keyword为空就给他一个空字符串"",保证在sql语句中字符串拼接的时候为 %%，而不是 %null%
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            //不是是查询关键字操作发过来的请求 (比如点击下面的 上一页，下一页..)
            //从session保存作用域中获取 keyword
            Object keywordObj = session.getAttribute("keyword");
            //将keywordObj 转成字符串，用于sql语句的拼接
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        //将pageNo保存到 会话保存作用域中(用于在页面上获取)
        session.setAttribute("pageNo", pageNo);

        //调用 getFruitList() 方法，得到一个存储Fruit对象的list集合 （一个Fruit对象代表数据库中的一条记录）
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword, pageNo);
        //将fruitList集合 以k-v的形式 存储到 会话保存作用域 中
        session.setAttribute("fruitList", fruitList);

        //获取总记录条数
        int fruitCount = fruitDAO.getFruitCount(keyword);
        //计算总页数
        int pageCount = (fruitCount + 5 - 1) / 5;
        //将pageCount保存到 会话保存作用域中(用于在页面上获取)
        session.setAttribute("pageCount", pageCount);

        //调用父类ViewBaseServlet 的processTemplate() 方法,（服务器内部转发型 资源跳转）
        // 该方法作用：在index.html静态页面 上加载java内存中的 fruitList数据
        //super.processTemplate("index",request,response);
        //2. 将资源跳转交给 中央控制器DispatcherServlet 统一处理
        return "index";
    }

    private String add(Integer fid, String fname, Integer price, Integer fcount, String remark) {

        //1. 获取请求参数，改为方法的形参，统一由中央控制器 传入

        //执行添加
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);

        //资源跳转（重定向）
        //response.sendRedirect("fruit.do");
        //2. 将资源跳转交给 中央控制器DispatcherServlet 统一处理
        return "redirect:fruit.do";
    }

    private String del(Integer fid, HttpServletRequest request) {

        //1. 获取请求参数，改为方法的形参，统一由中央控制器 传入

        if (fid != null) {

            //根据fid 删除水果
            fruitDAO.delFruit(fid);

            //使用客户端重定向，重新给FruitController发送请求，重新获取fruitList，然后覆盖到session中，更新index.html页面
            //response.sendRedirect("fruit.do");
            //2. 将资源跳转交给 中央控制器 DispatcherServlet 统一处理
            return "redirect:fruit.do";

        }
        return "error";
    }

    private String edit(Integer fid, HttpServletRequest request) {

        //1. 获取请求参数，改为方法的形参，统一由中央控制器 传入

        if (fid != null) {
            //通过fid 获取 指定的水果对象
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            //将fruit，保存到 request保存作用域 中
            request.setAttribute("fruit", fruit);

            //调用父类ViewBaseServlet 的processTemplate() 方法,
            // 该方法作用：在edit.html静态页面 上加载java内存中的 fruit对象
            //super.processTemplate("edit", request,response);
            //2. 将资源跳转交给 中央控制器DispatcherServlet 统一处理
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {

        //1. 获取请求参数，改为方法的形参，统一由中央控制器 传入

        //执行更新
        fruitDAO.updateFruit(new Fruit(fid, fname, price, fcount, remark));

        //资源跳转 使用客户端重定向
        // 目的是重新给FruitController发送请求，重新获取fruitList，然后覆盖到session中，这样页面上的index.html才是更新后的
        //response.sendRedirect("fruit.do");
        //2. 将资源跳转交给 中央控制器DispatcherServlet 统一处理
        return "redirect:fruit.do";
    }
}
