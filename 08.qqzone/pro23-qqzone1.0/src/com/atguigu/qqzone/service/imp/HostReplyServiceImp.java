package com.atguigu.qqzone.service.imp;

import com.atguigu.qqzone.dao.HostReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;
import com.atguigu.qqzone.service.HostReplyService;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 17:00 星期一
 * @Operating:
 * @Description:
 */
public class HostReplyServiceImp implements HostReplyService {
    private HostReplyDAO hostReplyDAO = null;

    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        return hostReplyDAO.getHostReplyByReplyId(replyId);
    }

    @Override
    public void delHostReply(Integer id) {
        hostReplyDAO.delHostReply(id);
    }
}
