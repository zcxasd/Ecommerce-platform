package com.neuedu.dao.impl;

import com.neuedu.common.JDBCUTILS;
import com.neuedu.dao.IUserDao;
import com.neuedu.pojo.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    // 登录
    @Override
    public UserInfo login(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "select id, username, password, email, phone, question, answer, role from neuedu_user where username=? and password=?";
            preparedStatement = connection.prepareStatement(sql);
           // 占位符赋值
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
           // 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                int userid = resultSet.getInt("id");
                //System.out.println("userid="+userid);
                String _username = resultSet.getString("username");
                String _password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                int role = resultSet.getInt("role");

                UserInfo userInfo = new UserInfo();
                userInfo.setId(userid);
                userInfo.setUsername(_username);
                userInfo.setPassword(_password);
                userInfo.setEmail(email);
                userInfo.setPhone(phone);
                userInfo.setQuestion(question);
                userInfo.setAnswer(answer);
                userInfo.setRole(role);

                return userInfo;
            }
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 修改token
    @Override
    public int updateTokenById(int userid, String token) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "update neuedu_user set token=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,token);
            preparedStatement.setInt(2,userid);
            // 执行修改
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    // 根据token查询用户信息
    @Override
    public UserInfo findUserInfoByToken(String token) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "select id, username, password, email, phone, question, answer, role from neuedu_user where token=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,token);
            // 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                int userid = resultSet.getInt("id");
                //System.out.println("userid="+userid);
                String _username = resultSet.getString("username");
                String _password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                int role = resultSet.getInt("role");

                UserInfo userInfo = new UserInfo();
                userInfo.setId(userid);
                userInfo.setUsername(_username);
                userInfo.setPassword(_password);
                userInfo.setEmail(email);
                userInfo.setPhone(phone);
                userInfo.setQuestion(question);
                userInfo.setAnswer(answer);
                userInfo.setRole(role);

                return userInfo;
            }
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 判断用户名是否存在
    @Override
    public int checkUserName(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "select count(1) from neuedu_user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,username);
            // 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                int count = resultSet.getInt(1);
                return count;
            }
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    // 判断邮箱是否存在
    @Override
    public int checkEmail(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "select count(1) from neuedu_user where email=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,email);
            // 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                int count = resultSet.getInt(1);
                return count;
            }
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    // 注册
    @Override
    public int register(UserInfo userInfo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "insert into neuedu_user (username, password, email, phone, question, answer, role, create_time, update_time) values (?,?,?,?,?,?,?,now(),now())";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,userInfo.getUsername());
            preparedStatement.setString(2,userInfo.getPassword());
            preparedStatement.setString(3,userInfo.getEmail());
            preparedStatement.setString(4,userInfo.getPhone());
            preparedStatement.setString(5,userInfo.getQuestion());
            preparedStatement.setString(6,userInfo.getAnswer());
            preparedStatement.setInt(7,userInfo.getRole());
            // 执行修改
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    // 根据用户名查找用户问题
    @Override
    public String findQuestionByUsername(String username) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "select question from neuedu_user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,username);
            // 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public int checkAnswer(String username, String question, String answer) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "select count(1) from neuedu_user where username=? and question=? and answer=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,question);
            preparedStatement.setString(3,answer);
            // 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    public int updatePassword(String username, String newPassword) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUTILS.getConnection();
            String sql = "update neuedu_user set password=? where username=?";
            preparedStatement = connection.prepareStatement(sql);
            // 占位符赋值
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,username);
            // 执行修改
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUTILS.close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    public List<UserInfo> findAll() {
        return null;
    }

    @Override
    public List<UserInfo> findAllByUsername(String username) {
        return null;
    }

    @Override
    public UserInfo findByOption(UserInfo userInfo) {
        return null;
    }

    @Override
    public int updateUser(UserInfo userInfo) {
        return 0;
    }

    @Override
    public List<UserInfo> findByIds(List<Integer> ids) {
        return null;
    }
}
