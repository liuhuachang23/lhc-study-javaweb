package com.atguigu.book.dao.impl;

import com.atguigu.book.dao.CartItemDAO;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.myssm.basedao.BaseDAO;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 13:21 星期日
 * @Operating:
 * @Description:
 */
public class CartItemDAOImpl extends BaseDAO<CartItem> implements CartItemDAO {

    @Override
    public void addCartItem(CartItem cartItem) {
        executeUpdate("insert into t_cart_item values(0,?,?,?)", cartItem.getBook().getId(),cartItem.getBuyCount(),cartItem.getUserBean().getId());
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        executeUpdate("update t_cart_item set buyCount = ? where id = ?", cartItem.getBuyCount(), cartItem.getId());
    }

    @Override
    public List<CartItem> getCartItemList(User user) {
        return executeQuery("select * from t_cart_item where userBean = ?", user.getId());
    }

    @Override
    public void delCartItem(CartItem cartItem) {
        super.executeUpdate("delete from t_cart_item where id= ?", cartItem.getId());
    }
}
