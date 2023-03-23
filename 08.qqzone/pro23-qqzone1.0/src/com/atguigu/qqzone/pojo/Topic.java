package com.atguigu.qqzone.pojo;

import java.util.Date;
import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/25 15:11 星期六
 * @Operating:
 * @Description: 日志
 */
public class Topic {
    private Integer id;             //id
    private String title;           //标题
    private String content;         //内容
    private Date topicDate;         //发表时间

    private UserBasic author;       //日志：作者 = M：1
    private List<Reply> replyList;  //日志：回复 = 1:N

    public Topic() {
    }

    public Topic(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(Date topicDate) {
        this.topicDate = topicDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
