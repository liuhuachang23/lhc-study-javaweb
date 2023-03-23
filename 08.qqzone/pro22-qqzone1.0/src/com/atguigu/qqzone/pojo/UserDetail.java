package com.atguigu.qqzone.pojo;

import java.util.Date;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/25 15:10 星期六
 * @Operating:
 * @Description: 用户详细详细
 */
public class UserDetail {

    private Integer id;             //id
    private String readName;        //姓名
    private String tel;             //电话
    private String email;           //邮箱
    private Date birth;             //生日
    private String star;            //星座

    public UserDetail() {
    }

    public UserDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReadName() {
        return readName;
    }

    public void setReadName(String readName) {
        this.readName = readName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
