package com.atguigu.qqzone.pojo;

import java.util.Date;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/25 15:09 星期六
 * @Operating:
 * @Description: 回复
 */
public class Reply {
    private Integer id;             //id
    private String content;         //内容
    private Date replyDate;         //回复时间

    private UserBasic author;       //回复：作者 = M：1
    private Topic topic;            //回复：日志 = M：1
    private HostReply hostReply;    //回复：主人回复 = 1：1

    public Reply() {
    }

    public Reply(Integer id) {
        this.id = id;
    }

    public Reply(String content, Date replyDate, UserBasic author, Topic topic) {
        this.content = content;
        this.replyDate = replyDate;
        this.author = author;
        this.topic = topic;
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

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public HostReply getHostReply() {
        return hostReply;
    }

    public void setHostReply(HostReply hostReply) {
        this.hostReply = hostReply;
    }
}
