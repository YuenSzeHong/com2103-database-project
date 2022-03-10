package com2103.lmsProject.config;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBconn {

    private final String DB_URL = "jdbc:mysql://localhost:3306/hsu?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASS = "szehong26";

    private Connection connection;

    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null)
            init();
        return connection;
    }
}
