package com.atguigu.book.controller;

import com.atguigu.book.pojo.Book;
import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;

import javax.servlet.http.HttpSession;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 13:12 星期日
 * @Operating:
 * @Description:
 */
public class CartController {

    private CartItemService cartItemService = null;

    //添加图书到购物车
    public String addCart(Integer bookId, HttpSession session){

        //1.获取user
        User user = (User)session.getAttribute("currUser");
        //2.根据用户获取购物车
        Cart cart = user.getCart();
        //3.通过图书id创建一个book对象（只有id属性），并封装成一个cartItem
        CartItem cartItem = new CartItem(new Book(bookId), 1, user);
        //4.将指定的图书（已经封装成了一个cartItem） 添加到当前购物车中
        cartItemService.addOrUpdateCartItem(cartItem,cart);

        //执行 CartController的index()
        return "redirect:cart.do";
    }

    //加载当前用户的购物车信息
    public String index(HttpSession session){
        //获取当前用户
        User user = (User)session.getAttribute("currUser");
        //获取用户的 购物车信息 （在里面会根据book的id重新查询book的详细信息）
        Cart cart = cartItemService.getCart(user);
        //将购物车信息 填入到用户属性中
        user.setCart(cart);
        //保存到session
        session.setAttribute("currUser",user);
        //视图处理，（跳转到cart.html页面）
        return "cart/cart";
    }

    public String editCart(Integer cartItemId, Integer buyCount){
        cartItemService.updateCartItem(new CartItem(cartItemId,buyCount));

        return "redirect:cart.do";
    }
}
