package com.neuedu;

import com.neuedu.cache.TokenCache;
import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoImpl;
import com.neuedu.pojo.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class UserDaoImplTest {

    IUserDao userDao;

    @Before
    public void before(){
        System.out.println("====before====");
        userDao=new UserDaoImpl();
    }

    @Test
    public void testLogin(){
        UserInfo userInfo = userDao.login("admin","admin");
        System.out.println(userInfo);
    }

    @Test
    public void testUpdateTokenById(){
       System.out.println(userDao.updateTokenById(1,"abc"));
    }

    @Test
    public void testFindUserInfoByToken(){
        System.out.println(userDao.findUserInfoByToken("681759802342e422ed9531457a335ef7"));
    }

    @Test
    public void testCheckUserName(){
        //System.out.println(userDao.checkUserName("admin"));
        System.out.println(userDao.checkEmail("xxx@126.com"));
    }

    @Test
    public void testFindQuestionByUsername(){
        System.out.println(userDao.findQuestionByUsername("admin"));
    }

    @Test
    public void testCheckAnswer(){
        System.out.println(userDao.checkAnswer("root","xxx","xxxx"));
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void testCache(){
        TokenCache.set("hello","word");
        System.out.println(TokenCache.get("hello1").equals("null"));
        //System.out.println(TokenCache.get("token_root"));
    }

    @Test
    public void testUpdatePassword(){
        System.out.println(userDao.updatePassword("root","123456"));
    }

    @After
    public void destory(){
        System.out.println("====after====");
        userDao=null;
    }
}
