package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class borrowedPage extends JFrame {

    private JButton searchBkIDButton;
    private JTable tbl_book;
    private JTextField borrowerIDFIeld;
    private JTextField bookIDField;
    private JButton searchBorrIDButton;
    private JPanel panel1;
    private JButton Showall;
    private JButton borrowButton;
    private JButton returnButton;
    private FileReader reader;
    private File file;
    private Connection con;

    public borrowedPage(Connection con) {

        file = new File("date.txt");
        try {
            file.createNewFile();
            reader = new FileReader(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error when creating file");
            e.printStackTrace();
        }
        this.con = con;

        searchBkIDButton.addActionListener(e -> {

            try {
                String searchValue = bookIDField.getText();
                bookIDField.setText(null);
                borrowerIDFIeld.setText(null);

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT * from borrow_records where book_id LIKE ? ORDER BY book_id;");
                ps.setString(1, "%" + searchValue + "%");
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                //Reset table when click searchBkIDButton((yourTableNameHere).setModel()), and build table (yourTableNameHere).getModel()
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

        searchBorrIDButton.addActionListener(e -> {

            try {
                String searchValue = borrowerIDFIeld.getText();
                bookIDField.setText(null);
                borrowerIDFIeld.setText(null);

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT * from borrow_records where borrower_id LIKE ? ORDER BY borrower_id;");
                ps.setString(1, "%" + searchValue + "%");
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                //Reset table when click searchBkIDButton((yourTableNameHere).setModel()), and build table (yourTableNameHere).getModel()
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
            bookIDField.setText(null);
            borrowerIDFIeld.setText(null);
            try {
                String searchValue = "0";

                //use preparedStatement instead of createStatement
                PreparedStatement ps = con.prepareStatement("SELECT * from borrow_records where borrower_id LIKE ? ORDER BY id;");
                ps.setString(1, "%" + searchValue + "%");
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                //Reset table when click searchBkIDButton((yourTableNameHere).setModel()), and build table (yourTableNameHere).getModel()
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
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String borrower_id = borrowerIDFIeld.getText();
                int book_id = Integer.parseInt(bookIDField.getText());
                borrowBook(borrower_id, book_id);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String borrower_id = borrowerIDFIeld.getText();
                int book_id = Integer.parseInt(bookIDField.getText());
                returnBook(borrower_id, book_id);
            }
        });
    }

    private void returnBook(String borrower_id, int book_id) {
        LocalDate date = null;
        try {
            date = getLocalDate(date);
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE borrow_records SET return_date = ? " +
                            "WHERE borrower_id = ? " +
                            "AND book_id = ? " +
                            "AND return_date IS NULL;");
            ps.setDate(1, Date.valueOf(date));
            ps.setString(2, borrower_id);
            ps.setInt(3, book_id);
            if (ps.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null, "Book not borrowed");
                return;
            }
            JOptionPane.showMessageDialog(null, "Return Successful");
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private void borrowBook(String borrower_id, int book_id) {
        LocalDate date = null;
        try {
            date = getLocalDate(date);
            PreparedStatement ps = con.prepareStatement("INSERT INTO borrow_records(borrower_id, book_id, borrow_date) VALUES (?, ?, ?);");
            ps.setString(1, borrower_id);
            ps.setInt(2, book_id);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book Borrowed, return date is " + getReturnDate(borrower_id, book_id));
        } catch (Exception exception) {
            System.out.println("Error in Book Borrow: " + exception.getMessage());
        }
    }

    private LocalDate getLocalDate(LocalDate date) throws IOException {
        reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            date = Instant.parse(line).atZone(ZoneOffset.UTC).toLocalDate();
        }
        reader.close();
        return date;
    }

    private String getReturnDate(String borrower_id, int book_id) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT DATE_ADD(borrow_date, INTERVAL (" +
                            "SELECT borrow_period FROM borrow_rule brl, users u WHERE br.borrower_id = u.user_id AND brl.rule_id = u.rule_id" +
                            ") DAY) " +
                            "FROM borrow_records br WHERE borrower_id = ? AND book_id = ?");
            ps.setString(1, borrower_id);
            ps.setInt(2, book_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDate(1).toString();
            }
        } catch (Exception exception) {
            System.out.println("Error in getting return date: " + exception.getMessage());
        }
        return null;
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
        panel1.setLayout(new GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Enter book ID");
        panel1.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchBkIDButton = new JButton();
        searchBkIDButton.setText("Search");
        panel1.add(searchBkIDButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(3, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tbl_book = new JTable();
        scrollPane1.setViewportView(tbl_book);
        bookIDField = new JTextField();
        bookIDField.setText("");
        panel1.add(bookIDField, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Enter borrower ID");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchBorrIDButton = new JButton();
        searchBorrIDButton.setText("Search");
        panel1.add(searchBorrIDButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        borrowerIDFIeld = new JTextField();
        panel1.add(borrowerIDFIeld, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Showall = new JButton();
        Showall.setText("Show All");
        panel1.add(Showall, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        borrowButton = new JButton();
        borrowButton.setText("Borrow");
        panel1.add(borrowButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        returnButton = new JButton();
        returnButton.setText("Return");
        panel1.add(returnButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}