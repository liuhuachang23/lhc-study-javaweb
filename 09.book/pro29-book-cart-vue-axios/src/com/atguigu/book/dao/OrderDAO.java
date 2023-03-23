package com.atguigu.book.dao;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/13 13:00 星期三
 * @Operating:
 * @Description:
 */
public interface OrderDAO {
    //添加订单
    void addOrderBean(OrderBean orderBean);
    //获取指定用户的订单列表
    List<OrderBean> getOrderList(User user);
    //获取指定订单的书包总数量
    Integer getOrderTotalBookCount(OrderBean orderBean);


}
