package com.atguigu.myssm.basedao;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/21 9:45 星期二
 * @Operating:
 * @Description:
 */
public class DAOException extends RuntimeException{
     DAOException(String msg){
        super(msg);
    }
}
