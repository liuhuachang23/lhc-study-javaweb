package com.atguigu.qqzone.pojo;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/25 15:08 星期六
 * @Operating:
 * @Description: 用户（基本信息、登录信息）
 */
public class UserBasic {
    private Integer id;             // id
    private String loginId;         // 账号
    private String nickName;        // 昵称
    private String pwd;             // 密码
    private String headImg;         // 头像

    private UserDetail userDetail;      //用户：用户详细信息 = 1:1
    private List<Topic> topicList;      //用户：日志 = 1:N
    private List<UserBasic> friendList; //用户：用户(好友) = M:N

    public UserBasic() {
    }

    public UserBasic(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<UserBasic> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<UserBasic> friendList) {
        this.friendList = friendList;
    }
}
