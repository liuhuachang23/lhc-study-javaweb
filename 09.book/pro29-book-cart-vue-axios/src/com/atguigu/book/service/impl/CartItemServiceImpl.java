package com.atguigu.book.service.impl;

import com.atguigu.book.dao.CartItemDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.BookService;
import com.atguigu.book.service.CartItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 13:36 星期日
 * @Operating:
 * @Description:
 */
public class CartItemServiceImpl implements CartItemService {

    private CartItemDAO cartItemDAO = null;
    private BookService bookService = null;

    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemDAO.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.updateCartItem(cartItem);

    }

    @Override
    public void addOrUpdateCartItem(CartItem cartItem, Cart cart) {

        //存在购物车
        if (cart != null) {
            //获取购物车中的 cartItemMap（它是一个Map集合 存放了购物车中的所有购物车项 （key为图书id，value为购物车项））
            Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
            if (cartItemMap == null) {
                cartItemMap = new HashMap<>();
            }
            //如果购物车中已经存在了该图书，那么将购物车中图书的数量+1
            if (cartItemMap.containsKey(cartItem.getBook().getId())) {
                //1）获取cartItemMap中的指定cartItem
                CartItem cartItemTemp = cartItemMap.get(cartItem.getBook().getId());
                //2）将cartItem 内的图书数量加1
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount() + 1);
                //3）执行修改
                updateCartItem(cartItemTemp);

            //否则，在购物车中新增一个这本图书的CartItem
            } else {
                addCartItem(cartItem);
            }
        //购物车不存在，也直接添加CartItem
        } else {
            addCartItem(cartItem);
        }

    }

    @Override
    public List<CartItem> getCartItemList(User user) {
        //查询指定用户的 所有购物车项(存储的book只有id属性)
        List<CartItem> cartItemList = cartItemDAO.getCartItemList(user);
        for (CartItem cartItem : cartItemList){
            //根据id查询完整的 Book
            Book book = bookService.getBookById(cartItem.getBook().getId());
            //在设置到cartItem中
            cartItem.setBook(book);

            //此处需要调用getXj(),目的是执行 getXj() 内部的代码 计算小计 给xj属性赋值,
            cartItem.getXj();
        }
        return cartItemList;
    }

    @Override
    public Cart getCart(User user) {

        //调用上面的方法，获取购物车中 的购物车项（详细信息）
        List<CartItem> cartItemList = getCartItemList(user);
        //并封装到cartItemMap中 （key为bookId, value为cartItem）
        Map<Integer, CartItem> cartItemMap = new HashMap<>();
        for (CartItem cartItem : cartItemList) {
            cartItemMap.put(cartItem.getBook().getId(), cartItem);
        }

        //创建购物车，将cartItemMap 添加到购物车中
        Cart cart = new Cart();
        cart.setCartItemMap(cartItemMap);

        return cart;
    }
}
