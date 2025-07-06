package com2103.lmsProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class book {
    private JTable tbl_book;
    private JTextField textField1;
    private JButton searchButton;
    private JPanel panel1;
    private JTextField textField2;
    private JButton searchButton2;

    public book(Connection con) {


        searchButton.addActionListener(e -> {
            try {
                String searchValue = textField1.getText();

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT b.title, b.book_id, b.isbn, g.name AS `genres`, p.name AS `publisher`, a.name AS `author`\n" +
                        "FROM books b, book_genres bg, genres g, publisher p, book_author ba, authors a\n" +
                        "WHERE \n" +
                        "    b.title LIKE ? AND\n" +
                        "    b.book_id = bg.book_id AND \n" +
                        "    bg.genre_id = g.id AND \n" +
                        "    b.publisher_id = p.publisher_id AND \n" +
                        "    b.book_id = ba.book_id AND \n" +
                        "    ba.author_id = a.id\n" +
                        "ORDER BY b.book_id;");
                ps.setString(1, "%" + searchValue + "%");
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                //Reset table when click searchButton((yourTableNameHere).setModel()), and build table (yourTableNameHere).getModel()
                tbl_book.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tbl_book.getModel();

                //get column name from lms database
                int cols = rsmd.getColumnCount();
                String[] colName = new String[cols];
                for (int i = 0; i < cols; i++) {
                    colName[i] = rsmd.getColumnLabel(i + 1);

                }
                model.setColumnIdentifiers(colName);

                //adding each row's value from lms database to your table
                while (rs.next()) {
                    String title = rs.getString(1);
                    String book_id = rs.getString(2);
                    String isbn = rs.getString(3);
                    String genres = rs.getString(4);
                    String publisher = rs.getString(5);
                    String author = rs.getString(6);

                    String[] row = {title, book_id, isbn, genres, publisher, author};
                    model.addRow(row);
                }


            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });
        searchButton2.addActionListener(e -> {
            try {
                String searchValue = textField2.getText();

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT b.title, b.book_id, b.isbn, g.name AS `genres`, p.name AS `publisher`, a.name AS `author`\n" +
                        "FROM books b, book_genres bg, genres g, publisher p, book_author ba, authors a\n" +
                        "WHERE \n" +
                        "    b.book_id LIKE ? AND\n" +
                        "    b.book_id = bg.book_id AND \n" +
                        "    bg.genre_id = g.id AND \n" +
                        "    b.publisher_id = p.publisher_id AND \n" +
                        "    b.book_id = ba.book_id AND \n" +
                        "    ba.author_id = a.id\n" +
                        "ORDER BY b.book_id;");
                ps.setString(1, "%" + searchValue + "%");
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                //Reset table when click searchButton((yourTableNameHere).setModel()), and build table (yourTableNameHere).getModel()
                tbl_book.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tbl_book.getModel();

                //get column name from lms database
                int cols = rsmd.getColumnCount();
                String[] colName = new String[cols];
                for (int i = 0; i < cols; i++) {
                    colName[i] = rsmd.getColumnLabel(i + 1);

                }
                model.setColumnIdentifiers(colName);

                //adding each row's value from lms database to your table
                while (rs.next()) {
                    String title = rs.getString(1);
                    String book_id = rs.getString(2);
                    String isbn = rs.getString(3);
                    String genres = rs.getString(4);
                    String publisher = rs.getString(5);
                    String author = rs.getString(6);

                    String[] row = {title, book_id, isbn, genres, publisher, author};
                    model.addRow(row);
                }


            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }

        });
    }


    public JPanel getter() {
        return this.panel1;
    }


}
