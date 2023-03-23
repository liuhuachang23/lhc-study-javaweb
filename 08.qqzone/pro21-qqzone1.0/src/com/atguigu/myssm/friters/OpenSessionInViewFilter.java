package com.atguigu.myssm.friters;

import com.atguigu.myssm.trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 20:19 星期一
 * @Operating:
 * @Description: 事务 过滤器
 */
@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        try {
            //1. 开启事务
            TransactionManager.beginTrans();
            System.out.println("开启事务...");
            //2. 放行
            filterChain.doFilter(servletRequest,servletResponse);
            //3. 提交事务
            TransactionManager.commit();
            System.out.println("提交事务...");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //4. 回滚事务
                TransactionManager.rollback();
                System.out.println("回滚事务...");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
