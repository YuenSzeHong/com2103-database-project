package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Objects;

public class borrowedPage extends JFrame {

    private JButton searchBkIDButton;
    private JTable tbl_book;
    private JTextField borrowerIDFIeld;
    private JTextField bookIDField;
    private JButton searchBorrIDButton;
    private JPanel panel1;
    private JButton SearchShowAllButton;
    private JButton borrowButton;
    private JButton returnButton;
    private JButton showIssueButton;
    private JButton renewalButton;
    private FileReader reader;
    private final File file;
    private final Connection con;

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


        SearchShowAllButton.addActionListener(e -> {


            try {

                String bookID = bookIDField.getText();
                String borrowerID = borrowerIDFIeld.getText();
                if (UserIDInvalid(borrowerID)) {
                    return;
                }

                LocalDate date = null;
                FileReader reader = new FileReader("date.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    date = Instant.parse(line).atZone(ZoneOffset.UTC).toLocalDate();
                }
                if (date == null) return;
                reader.close();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT " +
                                "b.borrower_id, " +
                                "u.user_name, " +
                                "bk.book_id, " +
                                "b.title, " +
                                "b.borrow_date, " +
                                "b.return_date, " +
                                "(bpf.borrow_period - DATEDIFF(IF(b.return_date,b.return_date,?), b.borrow_date)) AS `period remaining`, " +
                                "b.due_date " +
                                "FROM borrowed_books b, borrow_period_fine bpf, users u, books bk\n" +
                                "WHERE \n" +
                                "b.borrower_id = bpf.user_id AND\n" +
                                "b.borrower_id = u.user_id AND b.title = bk.title AND\n" +
                                "b.borrower_id LIKE ? " +
                                (!bookID.isEmpty() ? "AND bk.book_id = ? " : "") +
                                "ORDER BY b.borrow_date DESC;");
                ps.setDate(1, Date.valueOf(date));

                ps.setString(2, "%" + borrowerID + "%");
                if (!bookID.isEmpty()) {
                    ps.setInt(3, Integer.parseInt(bookID));
                }
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                tbl_book.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tbl_book.getModel();

                int cols = rsmd.getColumnCount();
                System.out.println(cols);
                String[] colName = new String[cols];
                for (int i = 0; i < cols; i++)
                    colName[i] = rsmd.getColumnLabel(i + 1);
                model.setColumnIdentifiers(colName);

                while (rs.next()) {
                    String borrow_id = rs.getString(1);
                    String user_name = rs.getString(2);
                    String book_id = String.valueOf(rs.getInt(3));
                    String book_title = rs.getString(4);
                    String borrow_date = String.valueOf(rs.getDate(5));
                    String return_date = String.valueOf(rs.getDate(6));
                    String period_remaining = rs.getInt(7) == 0 ? "on Due" : (Math.abs(rs.getInt(7)) + (rs.getInt(7) > 0 ? " days remaining" : " days late"));
                    String due_date = String.valueOf(rs.getDate(8));
                    String[] row = {borrow_id, user_name, book_id, book_title, borrow_date, return_date, period_remaining, due_date};
                    model.addRow(row);
                }


            } catch (SQLException sqEx) {
                System.out.println("Error when querying database: " + sqEx.getMessage());
            } catch (IOException ioEx) {
                System.out.println("Error when reading file: " + ioEx.getMessage());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "BookID is in number format!");
            }
        });
        borrowButton.addActionListener(e -> {
            try {
                String borrower_id = borrowerIDFIeld.getText();
                int book_id = Integer.parseInt(bookIDField.getText());
                borrowBook(borrower_id, book_id);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "BookID is in number format!");
            }
        });
        returnButton.addActionListener(e -> {
            try {
                String borrower_id = borrowerIDFIeld.getText();
                int book_id = Integer.parseInt(bookIDField.getText());
                returnBook(borrower_id, book_id);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "BookID is in number format!");
            }
        });
        showIssueButton.addActionListener(e -> {
            try {
                reader.close();

                LocalDate date = null;
                FileReader reader = new FileReader("date.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    date = Instant.parse(line).atZone(ZoneOffset.UTC).toLocalDate();
                }
                if (date == null) return;
                reader.close();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT " +
                                "b.borrower_id, u.user_name," +
                                "bk.book_id, b.title, " +
                                "b.borrow_date, b.return_date, " +
                                "(bpf.borrow_period - DATEDIFF(IF(b.return_date,b.return_date,?), b.borrow_date)) AS `period remaining`, " +
                                "b.due_date " +
                                "FROM borrowed_books b, borrow_period_fine bpf, users u, books bk\n" +
                                "WHERE \n" +
                                "b.due_date < ? AND\n" +
                                "b.borrower_id = bpf.user_id AND\n" +
                                "b.borrower_id = u.user_id \n" +
                                "ORDER BY b.borrow_date DESC;");
                ps.setDate(1, Date.valueOf(date));
                ps.setDate(2, Date.valueOf(date));

                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                tbl_book.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tbl_book.getModel();

                int cols = rsmd.getColumnCount();
                String[] colName = new String[cols];
                for (int i = 0; i < cols; i++)
                    colName[i] = rsmd.getColumnLabel(i + 1);
                model.setColumnIdentifiers(colName);

                while (rs.next()) {
                    String borrower_id = rs.getString(1);
                    String user_name = rs.getString(2);
                    String book_id = rs.getString(3);
                    String book_title = rs.getString(4);
                    String borrow_date = String.valueOf(rs.getDate(5));
                    String return_date = Objects.equals(String.valueOf(rs.getDate(6)), "null") ? "not returned" : String.valueOf(rs.getDate(6));
                    String period_remaining = rs.getInt(7) == 0 ? "on Due" : (Math.abs(rs.getInt(7)) + (rs.getInt(7) > 0 ? " days remaining" : " days late"));
                    String due_date = String.valueOf(rs.getDate(8));
                    String[] row = {borrower_id, user_name, book_id, book_title, borrow_date, return_date, period_remaining, due_date};
                    model.addRow(row);
                }


            } catch (SQLException sqEx) {
                System.out.println("Error when querying database: " + sqEx.getMessage());
            } catch (IOException ioEx) {
                System.out.println("Error when reading file: " + ioEx.getMessage());
            }
        });
        renewalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String borrower_id = borrowerIDFIeld.getText();
                    int book_id = Integer.parseInt(bookIDField.getText());
                    if (borrower_id.isEmpty() || book_id <= 0) {
                        JOptionPane.showMessageDialog(null, "Please enter valid borrower ID and book ID");
                    }
                    renewBook(borrower_id, book_id);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "BookID is in number format!");
                }
            }
        });
    }

    boolean UserIDInvalid(String userID) {
        if (userID.isEmpty()) {
            return false;
        }
        if (userID.length() == 6 && userID.charAt(0) == 'U') {
            return false;
        }
        JOptionPane.showMessageDialog(null, "Invalid user ID, Syntax: \"U000123\"");
        return true;
    }

    private void returnBook(String borrower_id, int book_id) {
        try {
            LocalDate date = getLocalDate();
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

    private boolean isBookBorrowed(int book_id) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM borrow_records WHERE book_id = ? AND return_date IS NULL;");
            ps.setInt(1, book_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException sqEx) {
            System.out.println("Error when querying database: " + sqEx.getMessage());
        }
        return false;
    }

    private boolean exceedBorrowLimit(String borrower_id) {
        try {
            PreparedStatement ps = con.prepareStatement("select count(*) from borrow_records where return_date is null and borrower_id = ?;");
            ps.setString(1, borrower_id);
            PreparedStatement ps2 = con.prepareStatement("select borrow_limit from borrow_period_fine where user_id = ?;");
            ps2.setString(1, borrower_id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int noBookBorrow = rs.getInt(1);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            int bookBorrowLimit = rs2.getInt(1);
            System.out.println(noBookBorrow);
            System.out.println(bookBorrowLimit);
            return noBookBorrow >= bookBorrowLimit;

        } catch (SQLException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        return true;
    }

    private void borrowBook(String borrower_id, int book_id) {
        try {
            LocalDate date = getLocalDate();
            if (exceedBorrowLimit(borrower_id)) {
                JOptionPane.showMessageDialog(null, "You have exceeded your borrow limit");
                return;
            }
            if (isBookBorrowed(book_id)) {
                JOptionPane.showMessageDialog(null, "This book is borrowed");
                return;
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO borrow_records(borrower_id, book_id, borrow_date) VALUES (?, ?, ?);");
            ps.setString(1, borrower_id);
            ps.setInt(2, book_id);
            ps.setDate(3, Date.valueOf(date));
            System.out.println(ps);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book Borrowed, return date is " + getReturnDate(borrower_id, book_id));
        } catch (Exception exception) {
            System.out.println("Error in Book Borrow: " + exception.getMessage());
        }
    }

    private void renewBook(String borrower_id, int book_id) {
        try {
            int currRenewalNo = getCurrentRenewalNo(borrower_id, book_id);
            int maxRenewalNo = getMaxRenewalNo(borrower_id, book_id);
            if (currRenewalNo == -1) {
                JOptionPane.showMessageDialog(null, "Book not borrowed");
                return;
            }
            if (maxRenewalNo == -1) {
                JOptionPane.showMessageDialog(null, "error in max renewal no");
                return;
            }
            if (currRenewalNo >= maxRenewalNo) {
                JOptionPane.showMessageDialog(null, "You have exceeded your renewal limit "+maxRenewalNo);
            }
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE borrow_records SET return_date = ?, " +
                            "no_of_renewals = ?" +
                            "WHERE borrower_id = ? " +
                            "AND book_id = ? " +
                            "AND return_date IS NULL;");
            ps.setDate(1, Date.valueOf(getLocalDate()));
            ps.setInt(2, currRenewalNo + 1);
            ps.setString(3, borrower_id);
            ps.setInt(4, book_id);
            if (ps.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null, "Book not borrowed");
                return;
            }
            JOptionPane.showMessageDialog(null, "Renew Successful");
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private int getMaxRenewalNo(String borrower_id, int book_id) {
        try {
            PreparedStatement ps = con.prepareStatement("select renewal_limit from borrow_period_fine where user_id = ?;");
            ps.setString(1, borrower_id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        return -1;
    }

    private int getCurrentRenewalNo(String borrower_id, int book_id) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT no_of_renewals FROM borrow_records WHERE borrower_id = ? AND book_id = ? AND return_date IS NULL;");
            ps.setString(1, borrower_id);
            ps.setInt(2, book_id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException sqEx) {
            System.out.println("Error when querying database: " + sqEx.getMessage());
        }
        return -1;
    }

    private LocalDate getLocalDate() throws IOException {
        LocalDate date = null;
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
            rs.next();
            return rs.getDate(1).toString();
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
        panel1.setLayout(new GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Enter book ID");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, -1), null, 0, false));
        tbl_book = new JTable();
        tbl_book.setAutoCreateRowSorter(true);
        Font tbl_bookFont = this.$$$getFont$$$(null, -1, 18, tbl_book.getFont());
        if (tbl_bookFont != null) tbl_book.setFont(tbl_bookFont);
        scrollPane1.setViewportView(tbl_book);
        bookIDField = new JTextField();
        Font bookIDFieldFont = this.$$$getFont$$$(null, -1, 20, bookIDField.getFont());
        if (bookIDFieldFont != null) bookIDField.setFont(bookIDFieldFont);
        bookIDField.setText("");
        panel1.add(bookIDField, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 20, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Enter borrower ID");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        borrowerIDFIeld = new JTextField();
        Font borrowerIDFIeldFont = this.$$$getFont$$$(null, -1, 20, borrowerIDFIeld.getFont());
        if (borrowerIDFIeldFont != null) borrowerIDFIeld.setFont(borrowerIDFIeldFont);
        panel1.add(borrowerIDFIeld, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        SearchShowAllButton = new JButton();
        Font SearchShowAllButtonFont = this.$$$getFont$$$(null, -1, 20, SearchShowAllButton.getFont());
        if (SearchShowAllButtonFont != null) SearchShowAllButton.setFont(SearchShowAllButtonFont);
        SearchShowAllButton.setText("Search-Show All");
        panel1.add(SearchShowAllButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showIssueButton = new JButton();
        Font showIssueButtonFont = this.$$$getFont$$$(null, -1, 20, showIssueButton.getFont());
        if (showIssueButtonFont != null) showIssueButton.setFont(showIssueButtonFont);
        showIssueButton.setHorizontalAlignment(0);
        showIssueButton.setText("Show issue record");
        panel1.add(showIssueButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        borrowButton = new JButton();
        Font borrowButtonFont = this.$$$getFont$$$(null, -1, 20, borrowButton.getFont());
        if (borrowButtonFont != null) borrowButton.setFont(borrowButtonFont);
        borrowButton.setText("Borrow");
        panel1.add(borrowButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        returnButton = new JButton();
        Font returnButtonFont = this.$$$getFont$$$(null, -1, 20, returnButton.getFont());
        if (returnButtonFont != null) returnButton.setFont(returnButtonFont);
        returnButton.setText("Return");
        panel1.add(returnButton, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}