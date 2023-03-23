package com.atguigu.book.dao.impl;

import com.atguigu.book.dao.BookDAO;
import com.atguigu.book.pojo.Book;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 10:56 星期日
 * @Operating:
 * @Description:
 */
public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public List<Book> getBookList() {
        return executeQuery("select * from t_book where bookStatus = 0");
    }

    @Override
    public Book getBookById(Integer id) {
        return load("select * from t_book where id = ?", id);
    }
}
