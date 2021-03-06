package com2103.lmsProject;

import com2103.lmsProject.config.DBconn;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;


public class LMSMain {
    public static void main(String[] args) {

        DBconn db = new DBconn();
        Connection conn = db.getConnection();

        SwingUtilities.invokeLater(() -> {
            homePage gui = new homePage(conn);
            JFrame frame = new JFrame();
            frame.setTitle("COM2103 Library Management System");
            frame.setPreferredSize(new Dimension(1300,720));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(gui.getter());
            frame.pack();
            frame.setVisible(true);
        });


        /* try {
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
*/

    }
}
