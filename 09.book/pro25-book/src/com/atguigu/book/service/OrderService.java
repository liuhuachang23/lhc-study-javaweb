package com.atguigu.book.service;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/13 20:42 星期三
 * @Operating:
 * @Description:
 */
public interface OrderService {
    /**
     * 添加订单，添加订单详情，并删除相应购物车项
     * @param orderBean 需要添加的订单表
     */
    void addOderBean(OrderBean orderBean);

    /**
     * 获取指定用户的订单
     * @param user 用户
     * @return 订单列表
     */
    List<OrderBean> getOrderList(User user);
}
