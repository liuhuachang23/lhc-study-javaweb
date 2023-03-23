package com.atguigu.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 9:16 星期一
 * @Operating:
 * @Description: 过滤器
 */
//通过注解的方式 拦截demo01.do的请求
@WebFilter("*.do")
public class Demo01Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("hello ");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("see you");
    }

    @Override
    public void destroy() {

    }
}
