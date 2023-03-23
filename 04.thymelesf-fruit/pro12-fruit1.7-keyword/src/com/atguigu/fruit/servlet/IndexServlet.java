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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码(这个是为了上面doPost调用doGet时准备的，此版本doGet本身 不需要设置编码)
        request.setCharacterEncoding("utf-8");

        //获取session会话
        HttpSession session = request.getSession();

        //设置当前页，默认值1
        Integer pageNo = 1;

        //获取oper参数
        String oper = request.getParameter("oper");
        //如果oper不为空，并且就是传过来的"search"，就代表此时是 查询关键字表单 发过来的请求
        //否则，就表示是 其他表单发送的请求
        String keyword = null;
        if (!StringUtil.isEmpty(oper) && "search".equals(oper)){
            //进入if 代表是查询关键字操作发过来的请求

            //pageNo还原为1 (从第一页开始 按关键字查询)
            pageNo = 1;
            //keyword从请求参数中获取
            keyword = request.getParameter("keyword");
            //对keyword判断，为空，就给它一个空字符串""
            //否则查询的时候 sql语句会拼接成%null%，我们期望是%%(等同于 任何关键字查询)
            if (StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            //将keyword存放（覆盖）到session作用域中
            //目的是为了，当使用关键字查询的时候，去换页依然会按照关键字查询 来换页
            session.setAttribute("keyword",keyword);
        } else {
            //不是查询关键字操作发过来的请求 (比如点击下面的 上一页，下一页..等换页请求)
            //1.获取当前页码
            String pageNoStr = request.getParameter("pageNo");
            //如果从请求中读取到pageNo，就直接强转，如果读不到就使用上面设定的默认值1
            if (!StringUtil.isEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }
            //2.从session作用域中获取 keyword
            Object keywordObj = session.getAttribute("keyword");

            //判断keyword
            if (keywordObj!=null){
                //不为空，就代表此时 是按照关键字查询，来换页
                //转为字符串，查询的时候，sql语句拼接为 %关键字%
                keyword = (String) keywordObj;
            }else {
                //为空 就是简单的翻页，将keyword置为空串，查询的时候拼接为 %%
                keyword = "";
            }

        }

        //将pageNo保存到 会话保存作用域中(重新更新当前页的页码，用于在页面上获取使用)
        session.setAttribute("pageNo",pageNo);

        //创建一个FruitDAOImpl对象
        FruitDAO fruitDAO = new FruitDAOImpl();
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
}
