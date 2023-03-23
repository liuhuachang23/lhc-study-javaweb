package com.atguigu.myssm.myspringmvc;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/21 10:09 星期二
 * @Operating:
 * @Description: 自定义异常类
 */
public class DispatcherServletException extends RuntimeException{
    public DispatcherServletException(String msg) {
        super(msg);
    }
}
