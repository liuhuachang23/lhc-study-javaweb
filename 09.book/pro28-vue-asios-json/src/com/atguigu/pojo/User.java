package com.atguigu.pojo;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/22 22:04 星期五
 * @Operating:
 * @Description:
 */
public class User {
    private String uname;
    private String pwd;

    public User() {
    }

    public User(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
