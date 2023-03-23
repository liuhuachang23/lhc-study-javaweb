package com.atguigu.qqzone.dao;

import com.atguigu.qqzone.pojo.HostReply;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/30 11:00 星期四
 * @Operating:
 * @Description:
 */
public interface HostReplyDAO {
    //根据Reply的id查询关联的HostReply实体
    HostReply getHostReplyByReplyId(Integer replyId);
    //根据id删除hostReply
    void delHostReply(Integer id);
}
