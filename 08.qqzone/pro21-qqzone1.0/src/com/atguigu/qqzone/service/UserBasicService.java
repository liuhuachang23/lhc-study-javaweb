package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 11:21 星期四
 * @Operating:
 * @Description:
 */
public interface UserBasicService {
    //登录验证
    UserBasic login(String loginId, String pwd);
    //获取好友列表
    List<UserBasic> getFriendList(UserBasic userBasic);

}
