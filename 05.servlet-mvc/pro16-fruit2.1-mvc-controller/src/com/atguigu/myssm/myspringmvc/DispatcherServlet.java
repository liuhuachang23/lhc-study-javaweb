package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/13 14:42 星期一
 * @Operating:
 * @Description:
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private Map<String, Object> beanMap = new HashMap<>();

    //构造器
    public DispatcherServlet() {
    }

    public void init() throws ServletException {
        super.init();
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
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    //按属性名称 获取 属性值
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    //通过 全类名className 获取类对象
                    Class<?> controllerBeanClass = Class.forName(className);
                    //通过类对象 创建实例
                    Object beanObj = controllerBeanClass.newInstance();

                    //将 beanId 和 beanObj （k-v）存储到HashMap中
                    beanMap.put(beanId, beanObj);

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
        servletPath = servletPath.substring(1, lastDotIndex);
        //System.out.println(servletPath);
        //第2步：fruit --> FruitController (形成关联)
        //1)通过 applicationContext.xml配置文件 配置相关信息
        //2)在 本类构造器 中完成关联
        //3)通过 servletPath 获取 对应的 FruitController对象（即通过 fruit --> FruitController对象）
        Object controllerBeanObj = beanMap.get(servletPath);


        //获取浏览器请求 参数
        String operate = request.getParameter("operate");
        //为空 默认为index
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {

            //获取 controllerBeanObj的Class类对象 (如FruitController对象的类对象)
            Class<?> controllerBeanClass = controllerBeanObj.getClass();
            //获取类对象的所有方法
            Method[] methods = controllerBeanClass.getDeclaredMethods();
            //遍历，匹配
            for (Method method : methods) {
                if (operate.equals(method.getName())) {

                    //1. 统一获取请求参数
                    //1-1 获取当前方法的所有参数，返回一个参数数组
                    Parameter[] parameters = method.getParameters();
                    //1-2 获取 参数的名称，通过名称获取参数的值
                    //再先做一些准备工作：
                    //1）在jdk8新特性中，才能获取到参数的真实名称，所以我们要进行设置
                    //2）创建一个Object数组，用于存放参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    //开始获取：
                    //遍历 parameters参数数组
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        //1）获取参数名称
                        String parameterName = parameter.getName();
                        //2）判断
                        //① 如果参数名为 request、response、session 则不是到请求方式获取参数，直接存储到Object数组中
                        if ("request".equals(parameterName)) {
                            parameterValues[i] = request;
                        } else if ("response".equals(parameterName)) {
                            parameterValues[i] = response;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = request.getSession();
                        } else {
                            //② 如果参数是其他 （如 fid fname pageNo oper ...） 就通过请求的方式 获取参数值
                            String parameterValue = request.getParameter(parameterName);

                            //3）获取参数的类型
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;
                            if (parameterObj != null) {
                                //4）判断 如果是Integer就转换为Integer，其他的(String)就不用转
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            //5）并存放到 Object数组中
                            parameterValues[i] = parameterObj;

                        }

                    }

                    //2. Controller组件中的方法调用，得到一个返回值
                    method.setAccessible(true); //爆破
                    Object methodReturnObj = method.invoke(controllerBeanObj, parameterValues);//方法对象.invoke(对象,实参列表)

                    //3. 视图处理
                    String methodReturnStr = (String) methodReturnObj;
                    if (methodReturnStr.startsWith("redirect:")) { //如果返回值是以 redirect: 开头
                        // 取掉 redirect: 部分 （如 redirect:fruit.do --> fruit.do ）
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        //资源跳转（客户端重定向）
                        response.sendRedirect(redirectStr);
                    } else {   //如果返回值不是以 redirect: 开头
                        //资源跳转（服务器内部转发型）
                        super.processTemplate(methodReturnStr, request, response);
                    }
                }
            }

            /*} else {
                //不存在 就抛出异常
                throw new RuntimeException("operate不合格");
            }*/

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
