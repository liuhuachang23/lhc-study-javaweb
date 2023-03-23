package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/13 14:42 星期一
 * @Operating:
 * @Description:
 */
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    private Map<String,Object> beanMap = new HashMap<>();

    //构造器
    public DispatcherServlet(){

    }

    public void init() {
        System.out.println("init-config...");
        try {
            //通过类加载器，获取一个和 applicationContext.xml文件 相关联的通道
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");

            //创建DocumentBuilderFactory对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //创建一个Document对象（）
            Document document = documentBuilder.parse(inputStream);

            //获取所有的bean结点 （通过 指定标签名 获取对应的结点集合）
            NodeList beanNodeList = document.getElementsByTagName("bean");
            //遍历结点集合
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                //判断该结点是否为 元素结点
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element)beanNode;
                    //按属性名称 获取 属性值
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    //通过 全类名className 获取类对象
                    Class<?> controllerBeanClass = Class.forName(className);
                    //通过类对象 创建实例
                    Object beanObj = controllerBeanClass.newInstance();


                    //通过类对象 获取指定的方法对象 (获取 setServletContext方法对象)
                    Method setServletContextMethod = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
                    //通过反射调用方法：方法对象.invoke(类的对象实例,形参类型类对象列表)
                    setServletContextMethod.invoke(beanObj,this.getServletContext());


                    //将 beanId 和 beanObj （k-v）存储到HashMap中
                    beanMap.put(beanId,beanObj);

                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //service服务方法
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        //假设url为 http://localhost:8080/pro15/fruit.do
        //那么 servletPath为 /fruit.do
        String servletPath = request.getServletPath();
        //System.out.println(servletPath);

        //我们想要将  fruit 和 FruitController 形成关联
        //我的思路是：
        //第1步：/fruit.do --> fruit (字符串截取)
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(1,lastDotIndex);
        //System.out.println(servletPath);
        //第2步：fruit --> FruitController (形成关联)
        //1)通过 applicationContext.xml配置文件 配置相关信息
        //2)在 本类构造器 中完成关联
        //3)通过 servletPath 获取 对应的 FruitController对象（即通过 fruit --> FruitController对象）
        Object controllerBeanObj = beanMap.get(servletPath);

        try {
            //获取浏览器请求 参数
            String operate = request.getParameter("operate");
            //为空 默认为index
            if (StringUtil.isEmpty(operate)){
                operate = "index";
            }
            //获取 controllerBeanObj对象(如FruitController对象) 的Class类对象
            Class<?> controllerBeanClass = controllerBeanObj.getClass();
            //通过方法名获取 controllerBeanClass中 对应的方法对象 getDeclaredMethod("方法名",形参类型对象列表)
            Method method = controllerBeanClass.getDeclaredMethod(operate, HttpServletRequest.class, HttpServletResponse.class);
            //再判断该方法是否存在
            if (method!=null){
                //FruitController中的方法为private 需暴力访问
                method.setAccessible(true);
                //存在就调用相应方法
                //在反射中的方法调用：方法对象.invoke(对象,实参列表)
                method.invoke(controllerBeanObj,request,response);
            } else {
                //不存在 就抛出异常
                throw new RuntimeException("operate不合格");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
