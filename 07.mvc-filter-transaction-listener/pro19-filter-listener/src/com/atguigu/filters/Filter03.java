package com.atguigu.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 11:10 星期一
 * @Operating:
 * @Description:
 */
@WebFilter("*.do")
public class Filter03 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("C");
        //执行
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("C3");
    }

    @Override
    public void destroy() {

    }
}
