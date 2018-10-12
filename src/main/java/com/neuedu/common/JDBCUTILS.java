package com.neuedu.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUTILS {

    private static Properties properties = new Properties();

    // 加载驱动
    static {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
            String driver = properties.getProperty("jdbc.driver");
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 获取连接
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(properties.getProperty("jdbc.url"),properties.getProperty("jdbc.username"),properties.getProperty("jdbc.password"));
    }

    // 关闭连接(add update delete)
    public static void close(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        if (connection!=null){
            connection.close();
        }
        if (preparedStatement!=null){
            preparedStatement.close();
        }
    }
    // select
    public static void close(Connection connection, PreparedStatement preparedStatement , ResultSet resultSet) throws SQLException {
        if (connection!=null){
            connection.close();
        }
        if (preparedStatement!=null){
            preparedStatement.close();
        }
        if (resultSet!=null){
            resultSet.close();
        }
    }
}
