package com.atguigu.book.pojo;

import java.math.BigDecimal;

//购物车项，一种图书代表一个购物车项，即一个购物车项可以存放多个相同的图书
public class CartItem {
    private Integer id ;
    private Book book ;
    private Integer buyCount ;
    private User userBean;

    private Double xj;

    public CartItem(){}

    public CartItem(Book book, Integer buyCount, User userBean) {
        this.book = book;
        this.buyCount = buyCount;
        this.userBean = userBean;
    }

    public CartItem(Integer id, Integer buyCount) {
        this.id = id;
        this.buyCount = buyCount;
    }

    public CartItem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public Double getXj() {
        //转为BigDecimal 进行运算
        BigDecimal bigDecimalPrice = new BigDecimal(getBook().getPrice() + "");
        BigDecimal bigDecimalBuyCount = new BigDecimal(buyCount + "");

        BigDecimal bigDecimalXj = bigDecimalPrice.multiply(bigDecimalBuyCount);
        xj = bigDecimalXj.doubleValue();
        return xj;
    }
}
