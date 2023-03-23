package com.atguigu.qqzone.dao.img;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.TopicDAO;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 11:12 星期四
 * @Operating:
 * @Description:
 */
public class TopicDAOImp extends BaseDAO<Topic> implements TopicDAO {

    @Override
    public List<Topic> getTopTopicList(UserBasic userBasic) {
        return super.executeQuery("select * from t_topic where author = ?",userBasic.getId());
    }

    @Override
    public void addTopic(Topic topic) {

    }

    @Override
    public void delTopic(Topic topic) {

    }

    @Override
    public Topic getTopic(Integer id) {
        return null;
    }
}
