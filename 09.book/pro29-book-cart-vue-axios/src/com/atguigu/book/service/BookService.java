package com.atguigu.book.service;

import com.atguigu.book.pojo.Book;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 10:57 星期日
 * @Operating:
 * @Description:
 */
public interface BookService {
    //获取图书集合
    List<Book> getBookList();
    //根据id获取book
    Book getBookById(Integer id);

}
