package com.neuedu;

import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoMyBatisImpl;
import com.neuedu.pojo.UserInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyBatisTest {

    @Test
    public void testLogin(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        UserInfo userInfo = userDao.login("xiaoming","8d5e957f297893487bd98fa830fa6413");
        System.out.println(userInfo);
    }
    @Test
    public void testRegister(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        // String username, String password, String email, String phone, String question, String answer, int role
        UserInfo userInfo = new UserInfo("xiaozhang","xiaozhang","asdxx@qq.com","789456","xxx","xx",1);
        System.out.println(userDao.register(userInfo));
    }

    @Test
    public void testCheckUserName(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        System.out.println(userDao.checkUserName("xiaoming"));
    }

    @Test
    public void testFindAll(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        System.out.println(userDao.findAll());
    }

    @Test
    public void testFindAllByUsername(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        System.out.println(userDao.findAllByUsername("xiaoming"));
    }

    @Test
    public void testFindByOption(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("xiaoming");
        userInfo.setId(4);
        userInfo.setEmail("789@qq.com");
        System.out.println(userDao.findByOption(userInfo));
    }

    @Test
    public void testUpdateUser(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        UserInfo userInfo = new UserInfo();
        //userInfo.setUsername("xiaoming");
        userInfo.setId(4);
        userInfo.setEmail("5789@qq.com");
        System.out.println(userDao.updateUser(userInfo));
    }

    @Test
    public void testFindByIds(){
        IUserDao userDao = new UserDaoMyBatisImpl();
        List<Integer> ids= new ArrayList<>();
        ids.add(1);
        ids.add(4);
        System.out.println(userDao.findByIds(ids));
    }
}