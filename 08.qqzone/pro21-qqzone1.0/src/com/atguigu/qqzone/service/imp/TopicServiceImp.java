package com.atguigu.qqzone.service.imp;

import com.atguigu.qqzone.dao.TopicDAO;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.TopicService;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 11:35 星期四
 * @Operating:
 * @Description:
 */
public class TopicServiceImp implements TopicService {
    private TopicDAO topicDAO = null;

    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return topicDAO.getTopTopicList(userBasic);
    }
}
