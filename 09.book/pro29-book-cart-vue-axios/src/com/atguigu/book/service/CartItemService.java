package com.atguigu.book.service;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 13:35 星期日
 * @Operating:
 * @Description:
 */
public interface CartItemService {
    //添加购物车项
    void addCartItem(CartItem cartItem);

    //修改购物车项
    void updateCartItem(CartItem cartItem);

    /**
     * 判断当前用户的购物车是否有这本书（即判断要执行 添加购物车项 or 修改购物车项）
     *
     * @param cartItem 购物车项
     * @param cart     购物车
     */
    void addOrUpdateCartItem(CartItem cartItem, Cart cart);

    //获取指定用户的所有购物车项列表（查询的时候将book的详细信息设置进去）
    List<CartItem> getCartItemList(User user);

    //加载特定用户的购物车信息
    Cart getCart(User user);
}
