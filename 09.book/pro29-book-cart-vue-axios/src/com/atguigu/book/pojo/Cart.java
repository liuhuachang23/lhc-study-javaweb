package com.atguigu.book.pojo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 14:12 星期日
 * @Operating:
 * @Description:
 */
public class Cart {
    private Map<Integer, CartItem> cartItemMap;     //购物车中购物车项的集合，key为图书id
    public Double totalMoney;                       //购物车中图书的总金额
    private Integer totalCount;                     //购物车中购物项的总数量
    private Integer totalBookCount;                 //购物车中图书的总数量



    public Cart() {
    }

    public Map<Integer, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Double getTotalMoney() {
        totalMoney = 0.0;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            Set<Map.Entry<Integer, CartItem>> entries = cartItemMap.entrySet();
            for (Map.Entry<Integer, CartItem> cartItemEntry : entries) {
                CartItem cartItem = cartItemEntry.getValue();
                //转为BigDecimal 进行运算
                BigDecimal bigDecimalPrice = new BigDecimal(cartItem.getBook().getPrice() + "");
                BigDecimal bigDecimalBuyCount = new BigDecimal(cartItem.getBuyCount() + "");
                totalMoney +=  bigDecimalPrice.multiply(bigDecimalBuyCount).doubleValue() ;
            }
        }
        return totalMoney;
    }

    public Integer getTotalCount() {
        totalCount = 0;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            totalCount = cartItemMap.size();
        }
        return totalCount;
    }

    public Integer getTotalBookCount() {
        totalBookCount = 0;
        if (cartItemMap != null && cartItemMap.size() > 0) {
            for (CartItem cartItem : cartItemMap.values()){
                totalBookCount += cartItem.getBuyCount();
            }
        }
        return totalBookCount;
    }
}
