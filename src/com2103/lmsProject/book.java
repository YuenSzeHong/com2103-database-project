package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Connection con;

    public book(Connection con) {
        this.con = con;


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
        searchButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

            }
        });
    }


    public JPanel getter() {
        return this.panel1;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Enter Book's Title");
        panel1.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        panel1.add(textField1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        panel1.add(searchButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tbl_book = new JTable();
        scrollPane1.setViewportView(tbl_book);
        final JLabel label2 = new JLabel();
        label2.setText("Enter Book's ID");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        textField2.setText("");
        panel1.add(textField2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton2 = new JButton();
        searchButton2.setText("Search");
        panel1.add(searchButton2, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
