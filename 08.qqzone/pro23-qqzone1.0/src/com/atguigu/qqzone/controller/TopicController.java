package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.TopicService;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 16:26 星期一
 * @Operating:
 * @Description:
 */
public class TopicController {

    private TopicService topicService = null;

    public String topicDetail(Integer id, HttpSession session) {

        //根据id获取指定topic
        Topic topic = topicService.getTopicById(id);

        session.setAttribute("topic", topic);
        return "frames/detail";
    }

    public String delTopic(Integer topicId) {
        topicService.delTopic(topicId);
        //删除完了，这里需要重定向，重新请求 （回到TopicController的getTopicList()来处理）
        return "redirect:topic.do?operate=getTopicList";
    }

    public String getTopicList(HttpSession session) {
        //从session中获取当前用户信息
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");
        //再次获取日志列表
        List<Topic> topicList = topicService.getTopicList(userBasic);
        //设置到userBasic中
        userBasic.setTopicList(topicList);
        //将userBasic覆盖到session中
        session.setAttribute("friend", userBasic);
        return "frames/main";
    }

}
