package com.atguigu.book.dao;

import com.atguigu.book.pojo.Book;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 10:53 星期日
 * @Operating:
 * @Description:
 */
public interface BookDAO {
    //获取所有图书
    List<Book> getBookList();
    //根据id获取图书
    Book getBookById(Integer id);

}
