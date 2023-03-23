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
            //5. 将UserBasic保存到session作用域中
            session.setAttribute("userBasic", userBasic);
            //6. 视图处理 (跳转到index.html页面)
            return "index";
        } else {
            //验证失败，就回到login.html页面
            return "login";
        }
    }
}
