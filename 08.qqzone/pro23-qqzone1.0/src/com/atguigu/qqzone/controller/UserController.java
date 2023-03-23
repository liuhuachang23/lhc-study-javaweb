package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.TopicService;
import com.atguigu.qqzone.service.UserBasicService;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 14:11 星期四
 * @Operating:
 * @Description:
 */
public class UserController {

    private UserBasicService userBasicService = null;
    private TopicService topicService = null;

    public String login(String loginId, String pwd, HttpSession session){
        //1. 登录验证
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if (userBasic != null){
            //2. 获取好友列表
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            //3. 获取日志列表
            List<Topic> topicList = topicService.getTopicList(userBasic);
            //4. 将获取到的数据，设置到UserBasic(POJO类)的属性中
            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);
            //5. 将UserBasic保存到session作用域中 （这个保存的是登陆者的信息）
            session.setAttribute("userBasic", userBasic);
            //  再保存一份到session域中，用于判断是否是自己空间还是好友空间（这个保存的是当前进入的好友空间）
            session.setAttribute("friend", userBasic);
            //6. 视图处理 (跳转到index.html页面)
            return "index";
        } else {
            //验证失败，就回到login.html页面
            return "login";
        }
    }

    public String friend(Integer id, HttpSession session){
        //根据id获取指定的用户信息
        UserBasic currFriend = userBasicService.getUserBasicById(id);
        //查询该用户的日志列表
        List<Topic> topicList = topicService.getTopicList(currFriend);
        //将该用户的日志列表设置给该用户
        currFriend.setTopicList(topicList);
        //将该用户 覆盖到 key为friend的session域中
        session.setAttribute("friend", currFriend);
        //视图处理
        return "index";
    }
}
