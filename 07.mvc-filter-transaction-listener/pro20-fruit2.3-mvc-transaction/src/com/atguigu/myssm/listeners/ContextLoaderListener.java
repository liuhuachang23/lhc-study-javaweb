package com.atguigu.myssm.listeners;

import com.atguigu.myssm.ioc.BeanFactory;
import com.atguigu.myssm.ioc.ClassPathXmlApplicationContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/21 15:03 星期二
 * @Operating:
 * @Description: ServletContextListener监听器
 * 监听上下文启动，在上下文启动前去创建IOC容器，然后将其保存到application作用域
 * DispatcherServlet中央控制器，只需从application作用域中获取IOC容器
 */
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1. 获取一个 ServletContext保存作用域（application保存作用域）
        ServletContext application = servletContextEvent.getServletContext();
        //2. 从web.xml配置文件中获取 上下文参数 path（applicationContext.xml）
        String path = application.getInitParameter("contextConfigLocation");
        //3. 创建 IOC容器（ClassPathXmlApplicationContext对象，实现bean标签的作用）
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        //4. 将IOC容器 保存到application保存作用域中
        application.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
