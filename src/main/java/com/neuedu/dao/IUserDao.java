package com.neuedu.dao;

import com.neuedu.pojo.UserInfo;

import java.util.List;

public interface IUserDao {

    // 登录
    public UserInfo login(String username,String password);

    // 修改token
    public int updateTokenById(int userid, String token);

    // 根据token查询用户信息
    public UserInfo findUserInfoByToken(String token);

    // 判断用户名是否存在
    public int checkUserName(String username);

    // 判断邮箱是否存在
    public int checkEmail(String email);

    // 注册
    public int register(UserInfo userInfo);

    // 根据用户名查找用户问题
    public String findQuestionByUsername(String username);

    // 校验答案
    public int checkAnswer(String username, String question, String answer);

    // 修改密码
    public int updatePassword(String username,String newPassword);

    // 查询所有
    public List<UserInfo> findAll();

    // 根据用户名查询用户信息，如果没有用户名，查询所有的用户信息
    public List<UserInfo> findAllByUsername(String username);

    // 先按照用户名查询，如果不存在按照id查询，如果id不存在，按照email查询
    public UserInfo findByOption(UserInfo userInfo);

    // 编辑个人信息
    public int updateUser(UserInfo userInfo);

    // 根据ids查询用户集合
    public List<UserInfo> findByIds(List<Integer> ids);
}
