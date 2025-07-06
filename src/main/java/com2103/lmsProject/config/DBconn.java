package com2103.lmsProject.config;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBconn {

    private Connection connection;
    public DBconn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_PASS = "lmsroot";
            String DB_USER = "lms";
            String DB_URL = "jdbc:mysql://localhost:3306/lms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            System.out.println("SQL connection Error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
