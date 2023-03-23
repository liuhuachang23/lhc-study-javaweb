package com.atguigu.book.dao;

import com.atguigu.book.pojo.OrderItem;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/13 13:01 星期三
 * @Operating:
 * @Description:
 */
public interface OrderItemDAO {
    //添加订单项
    void addOrderItem(OrderItem orderItem);
}
