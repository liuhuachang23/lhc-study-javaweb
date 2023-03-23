package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.ioc.BeanFactory;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/13 14:42 星期一
 * @Operating: 中央控制器
 * @Description:
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    //构造器
    public DispatcherServlet() {
    }

    //将里面的代码 移动到 ClassPathXmlApplicationContext类 （用于关联所有 bean标签内的 id和class, 并保存到Map中）
    public void init() throws ServletException {
        super.init();
        //1. 直接创建 ClassPathXmlApplicationContext对象
        // ClassPathXmlApplicationContext对象 的构造方法 会实现 bean标签的作用
        // beanFactory = new ClassPathXmlApplicationContext();
        //2. 将这一步提到 ServletContextListener监听器中，并保存到application保存作用域中
        //3. 现在优化为从application作用域去获取
        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if (beanFactoryObj != null) {
            beanFactory = (BeanFactory)beanFactoryObj ;
        } else {
            throw new RuntimeException("IOC容器获取失败！");
        }
    }


    //service服务方法
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        //设置编码 (放到 CharacterEncodingFilter过滤器中)
        //request.setCharacterEncoding("utf-8");

        //1. 解析URL
        //假设url为 http://localhost:8080/pro15/fruit.do
        //那么 servletPath为 /fruit.do
        String servletPath = request.getServletPath();
        //我们想要将  fruit 和 FruitController 形成关联
        //我的思路是：
        //第1步：/fruit.do --> fruit (字符串截取)
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(1, lastDotIndex);
        //System.out.println(servletPath);
        //第2步：fruit --> FruitController (形成关联)
        //通过 ClassPathXmlApplicationContext对象（beanFactory） 获取对应的 Map(fruit-FruitController对象)
        Object controllerBeanObj = beanFactory.getBean(servletPath);

        //获取浏览器请求 参数
        String operate = request.getParameter("operate");
        //为空 默认为index
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            //获取 controllerBeanObj的Class类对象 (如FruitController对象的类对象)
            Class controllerBeanClass = controllerBeanObj.getClass();
            //获取类对象的所有方法
            Method[] methods = controllerBeanClass.getDeclaredMethods();
            //遍历，匹配
            for (Method method : methods) {
                if (operate.equals(method.getName())) {

                    //1. 统一处理请求参数
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
                    //1) 爆破
                    method.setAccessible(true);
                    //2) 方法对象.invoke(对象,实参列表)
                    Object methodReturnObj = method.invoke(controllerBeanObj, parameterValues);

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

        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet service 出错了...");
        }

    }
}
