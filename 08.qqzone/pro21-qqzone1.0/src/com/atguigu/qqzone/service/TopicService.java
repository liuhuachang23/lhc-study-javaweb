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

}
