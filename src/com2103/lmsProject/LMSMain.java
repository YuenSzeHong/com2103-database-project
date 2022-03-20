package com2103.lmsProject;

import com2103.lmsProject.config.DBconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class LMSMain {
    public static void main(String[] args) {
        DBconn db = new DBconn();
        Connection conn = db.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            ResultSetMetaData rsmd = rs.getMetaData();
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                row.append(rsmd.getColumnLabel(i)).append("\t");
            }
            System.out.println(row);
            while (rs.next()) {
                row = new StringBuilder();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.append(rs.getString(i)).append("\t");
                }
                System.out.println(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }
}
