package com2103.lmsProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class rankPage {
    private JTextField nameText;
    private JSpinner feeSpinner;
    private JSpinner periodSpinner;
    private JTextField UserIDText;
    private JButton addUserButton;
    private JButton addRankButton;
    private JPanel mainPanel;
    private JButton removeUserButton;
    private JTextArea usersToBeAddedTextArea;
    private JButton assignButton;
    private JButton deleteRankButton;
    private JSpinner renewalSpinner;
    private JLabel renewalLimitText;
    private JTable rankTable;
    private JSpinner borrowLimitSpinner;
    private final HashMap<String, String> usersToBeAdded = new HashMap<>();
    private final Connection connection;

    public rankPage(Connection connection) {
        usersToBeAddedTextArea.setEditable(false);
        feeSpinner.setModel(new SpinnerNumberModel(1.5f, 0, 10.0f, 0.01f));
        periodSpinner.setModel(new SpinnerNumberModel(14, 1, 30, 1));
        renewalSpinner.setModel(new SpinnerNumberModel(2, 0, 10, 1));
        borrowLimitSpinner.setModel(new SpinnerNumberModel(3, 0, 30, 1));
        this.connection = connection;


        updateRankList();

        addUserButton.addActionListener(e -> {
            String UserID = UserIDText.getText();
            if (UserIDInvalid(UserID)) {
                return;
            }
            if (usersToBeAdded.containsKey(UserID)) {
                JOptionPane.showMessageDialog(null, "User already added");
                return;
            }
            try {
                PreparedStatement psmt = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?;");
                psmt.setString(1, UserID);
                psmt.executeQuery();
                ResultSet rs = psmt.getResultSet();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "User does not exist");
                    return;
                }
                usersToBeAdded.put(UserID, rs.getString("user_name"));
                updateUsersToBeAddedTextArea();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        removeUserButton.addActionListener(e -> {
            String UserID = UserIDText.getText();
            if (UserIDInvalid(UserID))
                return;
            if (!usersToBeAdded.containsKey(UserID)) {
                JOptionPane.showMessageDialog(null, "User not in list");
                return;
            }
            usersToBeAdded.remove(UserID);
            updateUsersToBeAddedTextArea();
        });
        addRankButton.addActionListener(e -> {
            if (nameText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a rank name");
            }
            if ((int) periodSpinner.getValue() < 1 || (int) periodSpinner.getValue() > 30) {
                JOptionPane.showMessageDialog(null, "Please enter a valid period");
                return;
            }
            if ((double) feeSpinner.getValue() < 0 || (double) feeSpinner.getValue() > 10) {
                JOptionPane.showMessageDialog(null, "Please enter a valid fee,valid range is 0.01 to 10.00");
                return;
            }
            if ((double) feeSpinner.getValue() == 0) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to set the daily fine to 0?") != 0) {
                    return;
                }
            }
            if ((int) renewalSpinner.getValue() > 10 || (int) renewalSpinner.getValue() < 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid renewal limit, valid range is 0-10");
            }
            if ((int) renewalSpinner.getValue() == 0) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to set unlimited renewal?") != 0) {
                    return;
                }
            }
            if ((int) borrowLimitSpinner.getValue() < 0 || (int) borrowLimitSpinner.getValue() > 30) {
                JOptionPane.showMessageDialog(null, "Please enter a valid borrow limit, valid range is 0-30");
            }
            if ((int) borrowLimitSpinner.getValue() == 0) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to set unlimited borrow?") != 0) {
                    return;
                }
            }
            int rule_id = 0;
            try {
                PreparedStatement psmt = connection.prepareStatement("SELECT rule_id FROM borrow_rule WHERE rank_name = ?;");
                psmt.setString(1, nameText.getText());
                psmt.executeQuery();
                ResultSet rs = psmt.getResultSet();
                if (rs.next()) {
                    rule_id = rs.getInt("rule_id");
                }
            } catch (SQLException ex) {
                System.out.println("Error in getting rule_id, Error: " + ex.getMessage());
            }
            try {
                PreparedStatement psmt = connection.prepareStatement(
                        "REPLACE INTO borrow_rule (rank_name, daily_fine, borrow_period, renewal_limit, borrow_limit) VALUES (?, ?, ?, ?, ?);");
                if (rule_id != 0) {
                    psmt = connection.prepareStatement("UPDATE borrow_rule SET rank_name = ?, daily_fine = ?, borrow_period = ?, renewal_limit = ?, borrow_limit = ? WHERE rule_id = ?;");
                    psmt.setString(1, nameText.getText());
                    psmt.setDouble(2, (double) feeSpinner.getValue());
                    psmt.setInt(3, (int) periodSpinner.getValue());
                    psmt.setInt(4, (int) renewalSpinner.getValue());
                    psmt.setInt(5, (int) borrowLimitSpinner.getValue());
                    psmt.setInt(6, rule_id);
                    System.out.println(psmt);
                } else {
                    psmt.setString(1, nameText.getText());
                    psmt.setDouble(2, (double) feeSpinner.getValue());
                    psmt.setInt(3, (int) periodSpinner.getValue());
                    psmt.setInt(4, (int) renewalSpinner.getValue());
                    psmt.setInt(5, (int) borrowLimitSpinner.getValue());
                }
                psmt.executeUpdate();
                if (usersToBeAdded.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Rank added/updated successfully");
                    updateRankList();
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to add rank");
                System.out.println("Error in adding rank, Error: " + ex.getMessage());
            }

            addUserToRank(usersToBeAdded, rule_id);

            JOptionPane.showMessageDialog(null, "user rank batch updated successfully");

            clearTexts();
        });
        assignButton.addActionListener(e -> {
            int rule_id = 0;
            try {
                PreparedStatement psmt = connection.prepareStatement("SELECT rule_id FROM borrow_rule WHERE rank_name = ?;");
                psmt.setString(1, nameText.getText());
                psmt.executeQuery();
                ResultSet rs = psmt.getResultSet();
                if (rs.next()) {
                    rule_id = rs.getInt("rule_id");
                }
            } catch (SQLException ex) {
                System.out.println("Error in getting rule_id, Error: " + ex.getMessage());
            }
            addUserToRank(usersToBeAdded, rule_id);
        });
        deleteRankButton.addActionListener(e -> {
            if (nameText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a rank name");
                return;
            }
            try {
                PreparedStatement psmt = connection.prepareStatement("DELETE FROM borrow_rule WHERE rank_name = ?;");
                psmt.setString(1, nameText.getText());
                psmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Rank deleted successfully");
                updateRankList();
                clearTexts();
            } catch (SQLException ex) {
                if (ex.getMessage().contains("FOREIGN KEY")) {
                    JOptionPane.showMessageDialog(null, "Rank is currently in use, please remove users from this rank first");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete rank");
                    System.out.println("Error in deleting rank, Error: " + ex.getMessage());
                }
            }
        });
    }

    void updateRankList() {
        try {

            //use preparedStatement instead of createStatement
            PreparedStatement ps = connection.prepareStatement("SELECT b.rank_name, b.daily_fine, b.borrow_period, b.renewal_limit as 'book renewal limit', b.borrow_limit From borrow_rule b");

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            //Reset table when click searchButton((yourTableNameHere).setModel()), and build table (yourTableNameHere).getModel()
            DefaultTableModel model = new DefaultTableModel();

            //get column name from lms database
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnLabel(i + 1);

            }

            model.setColumnIdentifiers(colName);

            //adding each row's value from lms database to your table
            while (rs.next()) {
                String rank_name = rs.getString(1);
                String daily_fine = rs.getString(2);
                String borrow_period = rs.getString(3);
                String renewal_limit = rs.getString(4);
                String borrow_limit = rs.getString(5);
                String[] row = {rank_name, daily_fine, borrow_period, renewal_limit, borrow_limit};
                model.addRow(row);
            }
            rankTable.setModel(model);


        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    boolean UserIDInvalid(String userID) {
        if (userID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid user ID");
            return true;
        }
        if (userID.length() != 6 || userID.charAt(0) != 'U') {
            JOptionPane.showMessageDialog(null, "Invalid user ID, Syntax: \"U000123\"");
            return true;
        }
        return false;
    }

    void updateUsersToBeAddedTextArea() {
        usersToBeAddedTextArea.setText(usersToBeAdded.toString()
                .replaceAll("[{}]", "")
                .replaceAll("=", "\t")
                .replaceAll(", ", "\n"));
    }

    void addUserToRank(HashMap<String, String> usersToBeAdded, int rank_id) {
        for (String userID : usersToBeAdded.keySet()) {
            try {
                PreparedStatement psmt = connection.prepareStatement("UPDATE users SET rule_id = ? WHERE user_id = ?;");
                psmt.setInt(1, rank_id);
                psmt.setString(2, userID);
                System.out.println(psmt);
                psmt.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to add rank");
                System.out.println("Error in updating rank, Error: " + ex.getMessage());
                return;
            }
        }
    }

    void clearTexts() {
        nameText.setText("");
        feeSpinner.setValue(1.5f);
        periodSpinner.setValue(14);
        UserIDText.setText("");
        usersToBeAdded.clear();
        updateUsersToBeAddedTextArea();
    }

    public JPanel getter() {
        return this.mainPanel;
    }

}