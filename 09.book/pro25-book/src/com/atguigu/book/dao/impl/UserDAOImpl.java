package com.atguigu.book.dao.impl;

import com.atguigu.book.dao.UserDAO;
import com.atguigu.book.pojo.User;
import com.atguigu.myssm.basedao.BaseDAO;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/8 21:46 星期五
 * @Operating:
 * @Description:
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public User getUser(String uname, String pwd) {
        return load("select * from t_user where uname like ? and pwd like ?", uname, pwd);
    }
}
