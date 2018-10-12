package com.neuedu.dao.impl;

import com.neuedu.common.MyBatisUtil;
import com.neuedu.dao.IUserDao;
import com.neuedu.pojo.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoMyBatisImpl implements IUserDao {
    // 登录
    @Override
    public UserInfo login(String username, String password) {
       /* Reader reader;
       // mybatis配置文件的路径
        String configfile = "mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSession = null;
        try {
            // 获取字符输入流
            reader = Resources.getResourceAsReader(configfile);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 数据库会话
            sqlSession = sqlSessionFactory.openSession();
            //sqlSession.selectOne();返回object对象
            //sqlSession.selectList();集合
            Map<String,String> map = new HashMap<>();
            map.put("username",username);
            map.put("password",password);
            UserInfo userInfo = sqlSession.selectOne("com.neuedu.dao.IUserDao.login",map);
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (sqlSession!=null){
                sqlSession.close();
            }
        }*/

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        UserInfo userInfo = sqlSession.selectOne("com.neuedu.dao.IUserDao.login",map);
        sqlSession.close();
        return userInfo;
    }

    @Override
    public int updateTokenById(int userid, String token) {
        return 0;
    }

    @Override
    public UserInfo findUserInfoByToken(String token) {
        return null;
    }

    @Override
    public int checkUserName(String username) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        int result = sqlSession.selectOne("com.neuedu.dao.IUserDao.checkUserName",username);
        sqlSession.close();
        return result;
    }

    @Override
    public int checkEmail(String email) {
        return 0;
    }

    // 注册
    @Override
    public int register(UserInfo userInfo) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        int result = sqlSession.insert("com.neuedu.dao.IUserDao.register",userInfo);
        // 手动提交事务
        sqlSession.commit();// insert delete update
        sqlSession.close();
        return result;
    }

    @Override
    public String findQuestionByUsername(String username) {
        return null;
    }

    @Override
    public int checkAnswer(String username, String question, String answer) {
        return 0;
    }

    @Override
    public int updatePassword(String username, String newPassword) {
        return 0;
    }

    @Override
    public List<UserInfo> findAll() {
       SqlSession sqlSession = MyBatisUtil.getSqlSession();
       List<UserInfo> list = sqlSession.selectList("com.neuedu.dao.IUserDao.findAll");
       sqlSession.close();
       return list;
    }

    @Override
    public List<UserInfo> findAllByUsername(String username) {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession.selectList("com.neuedu.dao.IUserDao.findAllByUsername",username);
    }

    @Override
    public UserInfo findByOption(UserInfo userInfo) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        // findByOption
        return sqlSession.selectOne("com.neuedu.dao.IUserDao.findByOption",userInfo);
    }

    @Override
    public int updateUser(UserInfo userInfo) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        int result = sqlSession.update("com.neuedu.dao.IUserDao.updateUser",userInfo);
        sqlSession.commit();
        return result;
    }

    @Override
    public List<UserInfo> findByIds(List<Integer> ids) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        return sqlSession.selectList("com.neuedu.dao.IUserDao.findByIds",ids);
    }
}
