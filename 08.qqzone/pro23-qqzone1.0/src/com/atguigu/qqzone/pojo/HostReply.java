package com.atguigu.qqzone.pojo;

import java.util.Date;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/25 15:11 星期六
 * @Operating:
 * @Description: 主人回复
 */
public class HostReply {
    private Integer id;             //id
    private String content;         //内容
    private Date hostReplyDate;     //主人回复日期

    private UserBasic author;       //主人回复：作者 = M；1
    private Reply reply;            //主人回复：回复 = 1：1

    public HostReply() {
    }

    public HostReply(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getHostReplyDate() {
        return hostReplyDate;
    }

    public void setHostReplyDate(Date hostReplyDate) {
        this.hostReplyDate = hostReplyDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }
}
