package com.atguigu.myssm.basedao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/20 20:26 星期一
 * @Operating:
 * @Description: 创建新连接、获取获取threadLocal中存放的连接、关闭连接
 */
public class ConnUtil {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/bookdb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String USER = "root";
    public static final String PWD = "lhc";

    //创建新连接
    private static Connection creatConn() {
        try {
            //1.加载驱动
            Class.forName(DRIVER);
            //2.通过驱动管理器获取连接对象
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取 threadLocal中 存放的连接
    public static Connection getConn() {
        Connection conn = threadLocal.get();    //获取 threadLocal中 存放的连接
        if (conn == null) {
            conn = creatConn();                 //如果获取不到就创建新的
            threadLocal.set(conn);              //再存放到threadLocal中
        }
        return threadLocal.get();
    }

    //关闭连接，并滞空threadLocal
    public static void closeConn() throws SQLException {

        Connection conn = threadLocal.get();    //获取 threadLocal中 存放的连接
        if (conn == null){                      //没有就直接返回
            return;
        }

        if (conn.isClosed()){
            conn.close();                       //有就关闭
            threadLocal.set(null);              //并且把 treadLocal滞空
        }

    }
}