package com.atguigu.myssm.io;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/17 19:12 星期五
 * @Operating:
 * @Description:
 */
public interface BeanFactory {
    //通过bean标签 的id属性值 获取class属性值
    Object getBean(String id);
}
