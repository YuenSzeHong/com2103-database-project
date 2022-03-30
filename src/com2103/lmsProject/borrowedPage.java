package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class borrowedPage extends JFrame {

    private JButton searchButton;
    private JTable tbl_book;
    private JTextField textField2;
    private JTextField textField1;
    private JButton searchButton2;
    private JPanel panel1;
    private JButton Showall;
    private Connection con;

    public borrowedPage(Connection con) {

        this.con = con;


        searchButton.addActionListener(e -> {

            try {
                String searchValue = textField1.getText();
                textField1.setText(null);
                textField2.setText(null);

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT * from borrow_records where book_id LIKE ? ORDER BY book_id;");
                ps.setString(1, searchValue);
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
                    int id = rs.getInt(1);
                    int book_id = rs.getInt(2);
                    Date borrow_date = rs.getDate(3);
                    Date return_date = rs.getDate(4);
                    String borrower_id = rs.getString(5);

                    String[] row = {String.valueOf(id), String.valueOf(book_id), String.valueOf(borrow_date), String.valueOf(return_date), borrower_id};
                    model.addRow(row);
                }


            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });

        searchButton2.addActionListener(e -> {

            try {
                String searchValue = textField2.getText();
                textField1.setText(null);
                textField2.setText(null);

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT * from borrow_records where borrower_id LIKE ? ORDER BY borrower_id;");
                ps.setString(1, searchValue);
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
                    String borrower_id = rs.getString(1);
                    String title = rs.getString(2);
                    String borrow_date = rs.getString(3);
                    String return_date = rs.getString(4);
                    String due_date = rs.getString(5);

                    String[] row = {borrower_id, title, borrow_date, return_date, due_date};
                    model.addRow(row);
                }


            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });

        Showall.addActionListener(e -> {
            textField1.setText(null);
            textField2.setText(null);
            try {
                String searchValue = "0";

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT * from borrow_records where borrower_id LIKE ? ORDER BY id;");
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
                    String borrower_id = rs.getString(1);
                    String title = rs.getString(2);
                    String borrow_date = rs.getString(3);
                    String return_date = rs.getString(4);
                    String due_date = rs.getString(5);

                    String[] row = {borrower_id, title, borrow_date, return_date, due_date};
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
        panel1.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Enter book ID OR");
        panel1.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        panel1.add(searchButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tbl_book = new JTable();
        scrollPane1.setViewportView(tbl_book);
        textField1 = new JTextField();
        textField1.setText("");
        panel1.add(textField1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Enter borrower ID");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton2 = new JButton();
        searchButton2.setText("Search");
        panel1.add(searchButton2, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        panel1.add(textField2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Showall = new JButton();
        Showall.setText("Show All");
        panel1.add(Showall, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}