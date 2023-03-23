package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/25 15:51 星期六
 * @Operating:
 * @Description:
 */
public interface UserBasicDAO {
    //根据账号和密码获取指定用户信息
    UserBasic getUserBasic(String loginId, String pwd);
    //获取指定用户的好友列表
    List<UserBasic> getUserBasicList(UserBasic userBasic);
    //根据id查询用户信息
    UserBasic getUserBasicById(Integer id);
}
