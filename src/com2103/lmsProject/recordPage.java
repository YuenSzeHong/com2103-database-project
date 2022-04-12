package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class recordPage {
    private JTable tblRecord;
    private JPanel recordPanel;
    private JButton showAllRecordButton;
    private JButton showIssuedRecordButton;
    private Connection con;


    public recordPage(Connection con) {
        this.con = con;

        showAllRecordButton.addActionListener(e -> {
            try {

                PreparedStatement ps = con.prepareStatement(
                        "select br.borrower_id, br.book_id, b.title, br.borrow_date, br.return_date from " +
                                "borrow_records br, " +
                                "books b " +
                                "where br.book_id = b.book_id");

                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                tblRecord.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tblRecord.getModel();

                int cols = rsmd.getColumnCount();
                String[] colName = new String[cols];
                for (int i = 0; i < cols; i++)
                    colName[i] = rsmd.getColumnLabel(i + 1);
                model.setColumnIdentifiers(colName);

                while (rs.next()) {
                    String borrower_id = rs.getString(1);
                    String book_id = rs.getString(2);
                    String title = rs.getString(3);
                    String borrow_date = rs.getString(4);
                    String return_date = rs.getString(5);
                    String[] row = {borrower_id, book_id, title, borrow_date, return_date};
                    model.addRow(row);
                }


            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });

        showIssuedRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    LocalDate date = null;
                    FileReader reader = new FileReader("date.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        date = Instant.parse(line).atZone(ZoneOffset.UTC).toLocalDate();
                    }
                    reader.close();

                    PreparedStatement ps = con.prepareStatement(
                            "SELECT u.user_name, (bpf.borrow_period - DATEDIFF(?, b.borrow_date)) AS `period remaining`, b.due_date FROM borrowed_books b, borrow_period_fine bpf, users u\n" +
                                    "WHERE \n" +
                                    "b.due_date < CURDATE() AND b.borrower_id = bpf.user_id AND\n" +
                                    "b.borrower_id = u.user_id AND b.return_date IS NULL\n" +
                                    "ORDER BY `period remaining` DESC;");
                    ps.setDate(1, Date.valueOf(date));
                    System.out.println(ps);
                    ResultSet rs = ps.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    tblRecord.setModel(new DefaultTableModel());
                    DefaultTableModel model = (DefaultTableModel) tblRecord.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i = 0; i < cols; i++)
                        colName[i] = rsmd.getColumnLabel(i + 1);
                    model.setColumnIdentifiers(colName);

                    while (rs.next()) {
                        String user_name = rs.getString(1);
                        String period_remaining = rs.getInt(2) == 0 ? "on Due" : (Math.abs(rs.getInt(2)) + (rs.getInt(2) > 0 ? " days remaining" : " days late"));
                        String due_date = rs.getString(3);
                        String[] row = {user_name, period_remaining, due_date};
                        model.addRow(row);
                    }


                } catch (SQLException sqEx) {
                    System.out.println("Error when querying database: " + sqEx.getMessage());
                } catch (IOException ioEx) {
                    System.out.println("Error when reading file: " + ioEx.getMessage());
                }
            }
        });
    }

    public JPanel getter() {
        return this.recordPanel;
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
        recordPanel = new JPanel();
        recordPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        recordPanel.add(scrollPane1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tblRecord = new JTable();
        scrollPane1.setViewportView(tblRecord);
        showAllRecordButton = new JButton();
        showAllRecordButton.setText("Show All Record");
        recordPanel.add(showAllRecordButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showIssuedRecordButton = new JButton();
        showIssuedRecordButton.setText("Show Issued Record");
        recordPanel.add(showIssuedRecordButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return recordPanel;
    }

}


