package com.neuedu.pojo;

import java.io.Serializable;

/*
* javabean
* setter/getter
* 无参构造方法
* 有参数构造方法
* 实现序列化接口
* */
public class UserInfo implements Serializable {
    private int id;  // 用户id
    private String username; // 用户名
    private String password; // 用户名密码，MD5加密
    private String email; // 用户email
    private String phone; // 用户phone
    private String question; // 找回密码问题
    private String answer; // 找回密码答案
    private int role; // 角色：0-管理员，1-普通客户
    // 1970=01-01 0:0:0 --当前时间 时间戳 1s=1800ms
    private long create_time; // 创建时间
    private long update_time; // 最后一次更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public UserInfo(int id, String username, String password, String email, String phone, String question, String answer, int role, long create_time, long update_time) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.question = question;
        this.answer = answer;
        this.role = role;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public UserInfo(String username, String password, String email, String phone, String question, String answer, int role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.question = question;
        this.answer = answer;
        this.role = role;
    }

    public UserInfo() {

    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", role=" + role +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
