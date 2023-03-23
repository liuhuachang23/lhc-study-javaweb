package com.atguigu.qqzone.dao.img;


import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.qqzone.dao.HostReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 17:01 星期一
 * @Operating:
 * @Description:
 */
public class HostReplyDAOImp extends BaseDAO<HostReply> implements HostReplyDAO {


    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        return super.load("select * from t_host_reply where reply = ?", replyId);
    }

    @Override
    public void delHostReply(Integer id) {
        super.executeUpdate("delete from t_host_reply where id = ?", id);
    }
}
