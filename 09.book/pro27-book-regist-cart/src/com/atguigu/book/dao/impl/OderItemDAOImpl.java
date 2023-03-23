package com.atguigu.book.dao.impl;

import com.atguigu.book.dao.OrderItemDAO;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.myssm.basedao.BaseDAO;


/**
 * @Author: 刘华昌
 * @DATE: 2022/7/13 13:09 星期三
 * @Operating:
 * @Description:
 */
public class OderItemDAOImpl extends BaseDAO<OrderItem> implements OrderItemDAO {
    @Override
    public void addOrderItem(OrderItem orderItem) {
        super.executeUpdate("insert into t_order_item values(0,?,?,?)",orderItem.getBook().getId(),orderItem.getBuyCount(),orderItem.getOrderBean().getId());
    }
}
