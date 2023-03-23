package com.atguigu.book.dao;

import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 13:18 星期日
 * @Operating:
 * @Description:
 */
public interface CartItemDAO {

    //新增购物车项
    void addCartItem(CartItem cartItem);
    //修改特定的购物车项
    void updateCartItem(CartItem cartItem);
    //获取特定用户的购物车项
    List<CartItem> getCartItemList(User user);
    //删除指定的购物车项
    void delCartItem(CartItem cartItem);
}
