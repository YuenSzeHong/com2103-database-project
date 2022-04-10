package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class usersPage extends JFrame {

    private JTable tbl_user;
    private JButton searchButton;
    private JTextField textField1;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JTextField textField3;
    private JButton submitButton;
    private Connection con;


    public usersPage(Connection con) {


        this.con = con;


        searchButton.addActionListener(e -> {
            try {
                String search = textField1.getText();

                PreparedStatement ps = con.prepareStatement(
                        "select u.user_id, u.user_name, r.rank_name, r.borrow_period, r.daily_fine from " +
                                "borrow_rule r, " +
                                "users u " +
                                "where u.rule_id = r.rule_id and " +
                                "u.user_id LIKE ?");
                ps.setString(1, "%" + search + "%");

                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                tbl_user.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tbl_user.getModel();

                int cols = rsmd.getColumnCount();
                String[] colName = new String[cols];
                for (int i = 0; i < cols; i++)
                    colName[i] = rsmd.getColumnLabel(i + 1);
                model.setColumnIdentifiers(colName);

                while (rs.next()) {
                    String userId = rs.getString(1);
                    String userName = rs.getString(2);
                    String ruleId = rs.getString(3);
                    int borrowPeriod = rs.getInt(4);
                    float dailyFine = rs.getFloat(5);
                    String[] row = {userId, userName, ruleId, String.valueOf(borrowPeriod), String.format("%.2f", dailyFine)};
                    model.addRow(row);
                }


            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String studId = textField3.getText();
                    String rkName = String.valueOf(comboBox1.getSelectedIndex());
                    System.out.println(studId);
                    System.out.println(rkName);
                    PreparedStatement ps = con.prepareStatement("UPDATE users SET rule_id = ? WHERE user_id = ?;");
                    ps.setString(1, rkName);
                    ps.setString(2, studId);
                    ps.execute();
                } catch (Exception exception) {
                    System.out.println("Error: " + exception.getMessage());
                }
            }
        });
    }


    public JPanel getter() {
        return this.panel1;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
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
        panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Enter user's ID");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        panel1.add(searchButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        textField1.setText("");
        panel1.add(textField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tbl_user = new JTable();
        scrollPane1.setViewportView(tbl_user);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Edit User Rank", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label2 = new JLabel();
        label2.setText("User ID");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Rank");
        panel3.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        defaultComboBoxModel1.addElement("default");
        defaultComboBoxModel1.addElement("punishment");
        defaultComboBoxModel1.addElement("VIP");
        comboBox1.setModel(defaultComboBoxModel1);
        panel3.add(comboBox1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        submitButton = new JButton();
        submitButton.setText("Submit");
        panel3.add(submitButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        textField3.setText("");
        panel3.add(textField3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
