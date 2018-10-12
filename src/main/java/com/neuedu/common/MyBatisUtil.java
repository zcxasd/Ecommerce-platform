package com.neuedu.common;

import com.neuedu.pojo.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory = null;
    static {
        Reader reader;
        // mybatis配置文件的路径
        String configfile = "mybatis-config.xml";
        try {
            // 获取字符输入流
            reader = Resources.getResourceAsReader(configfile);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
    public static void close(SqlSession sqlSession){
        if (sqlSession!=null){
            sqlSession.close();
        }
    }
}
