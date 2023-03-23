package com.atguigu.qqzone.service;

import com.atguigu.qqzone.pojo.HostReply;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 16:59 星期一
 * @Operating:
 * @Description:
 */
public interface HostReplyService {

    //根据id查询hostReply
    HostReply getHostReplyByReplyId(Integer id);
    //根据id删除hostReply
    void delHostReply(Integer id);
}
