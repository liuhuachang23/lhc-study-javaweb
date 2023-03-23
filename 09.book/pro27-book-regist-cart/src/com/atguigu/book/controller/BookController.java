package com.atguigu.book.controller;

import com.atguigu.book.pojo.Book;
import com.atguigu.book.service.BookService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/10 11:00 星期日
 * @Operating:
 * @Description:
 */
public class BookController {

    private BookService bookService = null;

    public String index(HttpSession session){
        List<Book> bookList = bookService.getBookList();
        session.setAttribute("bookList",bookList);
        return "index";
    }
}
