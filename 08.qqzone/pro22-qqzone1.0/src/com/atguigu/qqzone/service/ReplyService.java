package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 16:42 星期一
 * @Operating:
 * @Description:
 */
public interface ReplyService {
    //根据topic的id获取关联的所有回复
    List<Reply> getReplyListByTopicId(Integer id);
    //添加回复
    void addReply(Reply reply);
    //删除指定id的回复
    void delReply(Integer replyId);
    //删除指定日志的所有
    void delReplyList(Topic topic);
}
