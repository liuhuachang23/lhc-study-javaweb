package com.atguigu.book.controller;

import com.atguigu.book.pojo.Book;
import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.CartItemService;
import com.google.gson.Gson;

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
        //返回一个空字符串 给客户端表示执行成功就行 不需要其他数据
        return "";
    }

    //在客户端 index.html页面点 击购物车时 （通过pageController处理 跳转到cart.html页面）
    //加载 购物车界面 会调用 cart.js 中的 window.onload=function(){} ,创建一个vue对象
    // vue对象中 , 当数据渲染的时候,执行匿名函数 mounted: function () {}
    // 匿名函数调用 vue中的 getCart: function () {}
    //这个方法会向 我们服务器发送异步请求 axios({ method: "POST", url: "cart.do", params: {operate: 'cartInfo'} }) .then().catch()
    //从而执行一下方法
    public String cartInfo(HttpSession session){

        //获取当前用户
        User user = (User)session.getAttribute("currUser");
        //获取用户的 购物车信息 （在里面会根据book的id重新查询book的详细信息）
        Cart cart = cartItemService.getCart(user);

        //目前 cart 中 totalMoney totalCount totalBookCount 还没有赋值  (之前是在thymeleaf渲染的时候调用其get方法进行计算赋值)
        // 没有赋值就为null 导致的结果就是 下一步的gson转化时, 为null的属性会被忽略 即无法响应给客户端
        // 所以我们必须在 转化和响应之前 给这三个属性赋值
        cart.getTotalBookCount();
        cart.getTotalCount();
        cart.getTotalMoney();

        //使用gson 将上面从数据库中到的 cart对象 转换为Json字符串
        Gson gson = new Gson();
        String cartJsonStr = gson.toJson(cart);


        //视图处理 (响应返回给客户端)
        return "json:" + cartJsonStr;
    }
}
