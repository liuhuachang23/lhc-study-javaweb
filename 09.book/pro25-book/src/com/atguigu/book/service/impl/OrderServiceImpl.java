package com.atguigu.book.service.impl;

import com.atguigu.book.dao.CartItemDAO;
import com.atguigu.book.dao.OrderDAO;
import com.atguigu.book.dao.OrderItemDAO;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;

import java.util.List;
import java.util.Map;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/13 20:45 星期三
 * @Operating:
 * @Description:
 */
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = null;
    private OrderItemDAO orderItemDAO = null;
    private CartItemDAO cartItemDAO = null;

    @Override
    public void addOderBean(OrderBean orderBean) {

        //1. 订单添加一条记录,并给oderBean中的id属性赋值
        orderDAO.addOrderBean(orderBean);
        //2. 订单详情表添加7条记录 ，并删除 购物车项表对应的7条数据
        // （一个订单详情 对应 一个购物车项，所以我们要将 购物车项 封装为一个 订单详情，添加到订单详情表中，并删除对应的 购物车项）

        //2）获取订单所属用户
        User currUser = orderBean.getOrderUser();
        //3）获取用户的 购物车订单项集合
        Map<Integer, CartItem> cartItemMap = currUser.getCart().getCartItemMap();
        for (CartItem cartItem : cartItemMap.values()) {
            //创建订单详情,并赋值（赋对应订单项的值）
            OrderItem orderItem = new OrderItem();
            orderItem.setId(cartItem.getId());
            orderItem.setBook(cartItem.getBook());
            orderItem.setBuyCount(cartItem.getBuyCount());
            orderItem.setOrderBean(orderBean);
            //添加订单详情
            orderItemDAO.addOrderItem(orderItem);
        }

        //List<OrderItem> orderItemList = orderBean.getOrderItemList();

        //删除购物车项
        for (CartItem cartItem : cartItemMap.values()) {
            cartItemDAO.delCartItem(cartItem);
        }
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        //获取指定用户的订单
        List<OrderBean> orderBeanList = orderDAO.getOrderList(user);
        //获取订单中书本的总数量，并设置到orderBean的是属性中
        for (OrderBean orderBean : orderBeanList) {
            Integer totalBookCount = orderDAO.getOrderTotalBookCount(orderBean);
            orderBean.setTotalBookCount(totalBookCount);
        }
        return orderBeanList;
    }
}
