package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 10:52 星期四
 * @Operating:
 * @Description:
 */
public interface TopicDAO {
    //获取指定用户的所有日志
    List<Topic> getTopTopicList(UserBasic userBasic);
    //添加日志
    void addTopic(Topic topic);
    //删除日志
    void delTopic(Topic topic);
    //获取指定日志信息
    Topic getTopic(Integer id);
}
