package com.atguigu.qqzone.controller;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/5 14:20 星期二
 * @Operating:
 * @Description:
 */
public class ReplyController {

    private ReplyService replyService = null;


    public String addReply(String content, Integer topicId, HttpSession session) {

        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        Reply reply = new Reply(content, new Date(), userBasic, new Topic(topicId));
        replyService.addReply(reply);

        //添加完了，这里需要重定向，重新请求更新detail页面
        return "redirect:topic.do?operate=topicDetail&id="+ topicId;
    }

    public String delReply(Integer replyId, Integer topicId){
        replyService.delReply(replyId);

        //删除完了，这里需要重定向，重新请求更新detail页面
        return "redirect:topic.do?operate=topicDetail&id="+ topicId;

    }
}
