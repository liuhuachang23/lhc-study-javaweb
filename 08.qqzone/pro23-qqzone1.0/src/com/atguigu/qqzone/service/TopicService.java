package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 11:34 星期四
 * @Operating:
 * @Description:
 */
public interface TopicService {
    //查询指定用户的日志列表
    List<Topic> getTopicList(UserBasic userBasic);
    //根据id获取特定topic信息
    Topic getTopicById(Integer id);
    //根据id获取指定的topic信息，包含关联的作者信息
    Topic getTopic(Integer id);
    //删除日志
    void delTopic(Integer id);

}
