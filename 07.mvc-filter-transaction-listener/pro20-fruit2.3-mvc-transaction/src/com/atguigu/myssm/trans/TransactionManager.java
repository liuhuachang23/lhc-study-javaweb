package com.atguigu.myssm.trans;

import com.atguigu.myssm.basedao.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 20:21 星期一
 * @Operating:
 * @Description: 事务管理类
 */
public class TransactionManager {

    //1. 开启事务
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
    }

    //2. 提交事务，并且关闭连接
    public static void commit() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();        //提交事务
        ConnUtil.closeConn(); //关闭连接 并滞空TreadLocal
    }

    //3. 回滚事务
    public static void rollback() throws SQLException {
        ConnUtil.getConn().rollback();
    }


}
