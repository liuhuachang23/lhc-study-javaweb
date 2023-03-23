package com.atguigu.book.controller;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/13 21:05 星期三
 * @Operating:
 * @Description:
 */
public class OrderController {
    private OrderService orderService = null;
    //结账
    public String checkout(HttpSession session){

        //创建订单OrderBean
        OrderBean orderBean = new OrderBean();
        Date now = new Date();
        orderBean.setOrderNo(UUID.randomUUID().toString());
        orderBean.setOrderDate(now);
        User user = (User)session.getAttribute("currUser");
        orderBean.setOrderUser(user);
        orderBean.setOrderMoney(user.getCart().totalMoney);
        orderBean.setOrderStatus(0);
        //执行结账
        orderService.addOderBean(orderBean);

        return "index";
    }

    //查看订单列表
    public String getOrderList(HttpSession session){
        //获取当前用户
        User user = (User) session.getAttribute("currUser");
        //查询当前用户绑定的订单列表
        List<OrderBean> orderList = orderService.getOrderList(user);
        //设置到user属性中
        user.setOrderBeanList(orderList);
        //覆盖一下 session
        session.setAttribute("currUser",user);
        return "order/order";
    }
}
