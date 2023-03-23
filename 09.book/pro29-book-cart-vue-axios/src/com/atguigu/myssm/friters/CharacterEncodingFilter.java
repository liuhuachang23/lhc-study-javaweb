package com.atguigu.myssm.friters;

import com.atguigu.myssm.util.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 15:03 星期一
 * @Operating:
 * @Description: 设置编码格式 过滤器
 */
@WebFilter(urlPatterns = {"*.do"},
        //使用注解的方式 配置初始化参数 name = encoding 的值为 UTF-8
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        })
public class CharacterEncodingFilter implements Filter {

    //默认编码格式为UTF-8
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //从 xml配置文件中读取 初始化参数 （k-v）
        String encodingStr = filterConfig.getInitParameter("encoding");
        if (StringUtil.isNotEmpty(encodingStr)) {
            encoding = encodingStr;
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ((HttpServletRequest) servletRequest).setCharacterEncoding(encoding);
        ((HttpServletResponse)servletResponse).setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
