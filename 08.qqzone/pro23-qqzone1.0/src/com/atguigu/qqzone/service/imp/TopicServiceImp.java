package com.atguigu.qqzone.service.imp;

import com.atguigu.qqzone.dao.TopicDAO;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.ReplyService;
import com.atguigu.qqzone.service.TopicService;
import com.atguigu.qqzone.service.UserBasicService;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 11:35 星期四
 * @Operating:
 * @Description:
 */
public class TopicServiceImp implements TopicService {
    private TopicDAO topicDAO = null;
    //此处引入的是其他POJO对应的Service接口，而不是DAO接口
    //其他POJO对应的业务逻辑是封装在service层的，我需要调用别人的业务逻辑方法，而不要去深入考虑人家内部的细节
    private ReplyService replyService = null;
    private UserBasicService userBasicService = null;

    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return topicDAO.getTopTopicList(userBasic);
    }


    @Override
    public Topic getTopic(Integer id){
        //根据id查询指定topic
        Topic topic = topicDAO.getTopic(id);
        //获取topic中的作者(这个作者只有id属性赋值了)
        UserBasic author = topic.getAuthor();
        //通过这个作者的id属性，获取作者的完整信息
        author = userBasicService.getUserBasicById(author.getId());
        //设置到topic中
        topic.setAuthor(author);
        return topic;
    }

    @Override
    public Topic getTopicById(Integer id) {

        //调用上面方法
        Topic topic = getTopic(id);
        //根据topic的id查询该topic的所有回复
        List<Reply> replyList = replyService.getReplyListByTopicId(topic.getId());
        //将回复 设置到topic中
        topic.setReplyList(replyList);
        return topic;
    }

    @Override
    public void delTopic(Integer id) {
        //1. 根据id获取指定日志
        Topic topic = topicDAO.getTopic(id);
        if (topic != null){
            //2. 删除日志所有回复
            replyService.delReplyList(topic);
            //3.删除日志
            topicDAO.delTopic(topic);
        }

    }
}
