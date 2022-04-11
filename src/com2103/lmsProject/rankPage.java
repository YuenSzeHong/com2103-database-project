package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
    private final HashMap<String, String> usersToBeAdded = new HashMap<>();
    private Connection connection;

    public rankPage(Connection connection) {
        usersToBeAddedTextArea.setEditable(false);
        feeSpinner.setValue(1.5f);
        feeSpinner.setModel(new SpinnerNumberModel(1.5f, 0, 10.0f, 0.01f));
        periodSpinner.setValue(14);
        periodSpinner.setModel(new SpinnerNumberModel(14, 1, 30, 1));
        this.connection = connection;

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
                        "REPLACE INTO borrow_rule (rank_name, daily_fine, borrow_period) VALUES (?, ?, ?);");
                if (rule_id != 0) {
                    psmt = connection.prepareStatement("UPDATE borrow_rule SET rank_name = ?, daily_fine = ?, borrow_period = ? WHERE rule_id = ?;");
                    psmt.setString(1, nameText.getText());
                    psmt.setDouble(2, (double) feeSpinner.getValue());
                    psmt.setInt(3, (int) periodSpinner.getValue());
                    psmt.setInt(4, rule_id);
                    System.out.println(psmt);
                } else {
                    psmt.setString(1, nameText.getText());
                    psmt.setDouble(2, (double) feeSpinner.getValue());
                    psmt.setInt(3, (int) periodSpinner.getValue());
                }
                psmt.executeUpdate();
                if (usersToBeAdded.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Rank added/updated successfully");
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
        deleteRankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a rank name");
                    return;
                }
                try {
                    PreparedStatement psmt = connection.prepareStatement("DELETE FROM borrow_rule WHERE rank_name = ?;");
                    psmt.setString(1, nameText.getText());
                    psmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Rank deleted successfully");
                    clearTexts();
                } catch (SQLException ex) {
                    if (ex.getMessage().contains("FOREIGN KEY")) {
                        JOptionPane.showMessageDialog(null, "Rank is currently in use, please remove users from this rank first");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete rank");
                        System.out.println("Error in deleting rank, Error: " + ex.getMessage());
                    }
                }
            }
        });
    }

    boolean UserIDInvalid(String userID) {
        if (userID.equals("")) {
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Rank Name");
        mainPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameText = new JTextField();
        mainPanel.add(nameText, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Period");
        mainPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Fees");
        mainPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        feeSpinner = new JSpinner();
        mainPanel.add(feeSpinner, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        periodSpinner = new JSpinner();
        mainPanel.add(periodSpinner, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Search User");
        mainPanel.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        UserIDText = new JTextField();
        mainPanel.add(UserIDText, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addUserButton = new JButton();
        addUserButton.setText("add user");
        mainPanel.add(addUserButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Users to be added:");
        mainPanel.add(label5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeUserButton = new JButton();
        removeUserButton.setText("remove user");
        mainPanel.add(removeUserButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usersToBeAddedTextArea = new JTextArea();
        mainPanel.add(usersToBeAddedTextArea, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        addRankButton = new JButton();
        addRankButton.setText("Add/Update rank and Assign users");
        mainPanel.add(addRankButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        assignButton = new JButton();
        assignButton.setText("Assign Users to Rank");
        mainPanel.add(assignButton, new GridConstraints(7, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteRankButton = new JButton();
        deleteRankButton.setText("Delete Rank");
        mainPanel.add(deleteRankButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    public JPanel getter() {
        return this.mainPanel;
    }

}
