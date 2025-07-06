package com2103.lmsProject;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class usersPage extends JFrame {

    private JTable tbl_user;
    private JButton searchButton;
    private JTextField textField1;
    private JPanel panel1;
    private JComboBox rankCombo;
    private JTextField textField3;
    private JButton submitButton;
    private HashMap<String, Integer> rankList;
    private final Connection con;


    public usersPage(Connection con) {


        this.con = con;

        initRankCombo();

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

        submitButton.addActionListener(e -> {
            try {
                String studId = textField3.getText();
                int rank_id = rankList.get(rankCombo.getSelectedItem());
                System.out.println(studId);
                System.out.println(rank_id + 1);
                PreparedStatement ps = con.prepareStatement("UPDATE users SET rule_id = ? WHERE user_id = ?;");
                ps.setInt(1, rank_id);
                ps.setString(2, studId);
                ps.execute();
            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });
        rankCombo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                try {
                    PreparedStatement ps = con.prepareStatement("select * from borrow_rule");
                    ResultSet rs = ps.executeQuery();

                    rankList = new HashMap<>();

                    while (rs.next()) {
                        rankList.put(rs.getString(2), rs.getInt(1));
                    }
                    rankCombo.setModel(new DefaultComboBoxModel(rankList.keySet().toArray()));
                } catch (Exception exception) {
                    System.out.println("Error: " + exception.getMessage());
                }
                super.focusGained(e);
            }
        });
    }

    private void initRankCombo() {
        try {
            PreparedStatement ps = con.prepareStatement("select * from borrow_rule");
            ResultSet rs = ps.executeQuery();

            rankList = new HashMap<>();

            while (rs.next()) {
                rankList.put(rs.getString(2), rs.getInt(1));
            }
            rankCombo.setModel(new DefaultComboBoxModel(rankList.keySet().toArray()));
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }


    public JPanel getter() {
        return this.panel1;
    }


}