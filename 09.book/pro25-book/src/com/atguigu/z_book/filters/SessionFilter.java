package com.atguigu.z_book.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/17 11:05 星期日
 * @Operating:
 * @Description:
 */
@WebFilter(
        //拦截
        urlPatterns = {"*.do", "*.html"},
        //bai名单
        initParams = {
                @WebInitParam(name = "bai",
                        value = "/pro25/page.do?operate=page&page=user/login,/pro25/user.do?null")
        }
)
public class SessionFilter implements Filter {

    List<String> baiList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取 @WebInitParam 中 name="bai"的value值
        String bai = filterConfig.getInitParameter("bai");
        //使用, 分割 存储到数组中(里面存放的都是 name="bai"绑定的value值)
        String[] baiArr = bai.split(",");
        //将数组转为集合
        baiList = Arrays.asList(baiArr);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // http://localhost:8080/pro25/page.do
        // System.out.println("request.getRequestURL() = " + request.getRequestURL());
        // pro25/page.do
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        // operate=page&page=user/login
        System.out.println("request.getQueryString() = " + request.getQueryString());

        //1. 获取uri （如 pro25/page.do）
        String uri = request.getRequestURI();
        //2. 获取queryString （如 operate=page&page=user/login）
        String queryString = request.getQueryString();
        //3. 拼接 （ 如 pro25/page.do?operate=page&page=user/login ）
        String str =  uri + "?" + queryString;
        //4. 判断：这个字符串是否在 配置的bai名单中出现，有就放行
        if (baiList.contains(str)){
            //1) 放行 进行相应操作（如 pro25/page.do?operate=page&page=user/login 就是跳转到登录界面）
            filterChain.doFilter(request,response);
            return;
        } else {
            //2) 没有就进一步判断： 是不是已经登录了（session中保存了currUser）
            HttpSession session = request.getSession();
            Object currUserObj = session.getAttribute("currUser");
            if (currUserObj == null) {
                // 没有登录 就跳转到登录界面
                response.sendRedirect("page.do?operate=page&page=user/login");
            } else {
                // 用户登录了 就放行 进行相应操作
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
