package com.atguigu.qqzone.dao.img;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 16:57 星期一
 * @Operating:
 * @Description:
 */
public class ReplyDAOImp extends BaseDAO<Reply> implements ReplyDAO {
    @Override
    public List<Reply> getReplyList(Topic topic) {
        return super.executeQuery("select * from t_reply where topic = ?", topic.getId());
    }

    @Override
    public void addReply(Reply reply) {
        executeUpdate("insert into t_reply values(0,?,?,?,?)",reply.getContent(),reply.getReplyDate(),reply.getAuthor().getId(),reply.getTopic().getId());
    }

    @Override
    public void delReply(Integer id) {
        executeUpdate("delete from t_reply where id = ?", id);
    }

    @Override
    public Reply getReply(Integer id) {
        return load("select * from t_reply where id = ?",id);
    }
}
