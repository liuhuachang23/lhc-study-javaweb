package com.atguigu.book.service.impl;

import com.atguigu.book.dao.BookDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.book.service.BookService;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 10:57 星期日
 * @Operating:
 * @Description:
 */
public class BookServiceImpl implements BookService {

    private BookDAO bookDAO = null;
    @Override
    public List<Book> getBookList() {
        return bookDAO.getBookList();
    }

    @Override
    public Book getBookById(Integer id) {
        return bookDAO.getBookById(id);
    }
}
