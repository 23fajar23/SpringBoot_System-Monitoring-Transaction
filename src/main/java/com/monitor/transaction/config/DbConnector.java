package com.monitor.transaction.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public static Connection getConnect(){
        Connection conn = null;
        try {
            String host = System.getenv("DB_HOST");
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");
            conn = DriverManager.getConnection(host,username,password);
        }catch (SQLException e){
            System.out.println("Error connection to database" + e.getMessage());
        }

        return conn;
    }
}
