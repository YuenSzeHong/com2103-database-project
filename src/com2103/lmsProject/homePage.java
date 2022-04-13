package com2103.lmsProject;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;


public class homePage extends JFrame {

    private JPanel homePanel;
    private JButton button1;
    private JTabbedPane tabbedPanel;

    public homePage(Connection connection) {

        /*create your Jform and add it your tabbedPanel here
          about addTab() method: addTab("yourTabbedPanelNameHere", yourJformFileName.getter())
          build getter() method in your (YourJformName).java file.
         */

        usersPage users = new usersPage(connection);
        tabbedPanel.addTab("users", users.getter());

        book books = new book(connection);
        tabbedPanel.addTab("books", books.getter());

        borrowedPage borrowedPages = new borrowedPage(connection);
        tabbedPanel.addTab("borrowed book", borrowedPages.getter());

        datePage datePages = new datePage();
        tabbedPanel.addTab("date", datePages.getter());

        rankPage rankPages = new rankPage(connection);
        tabbedPanel.addTab("rank", rankPages.getter());
    }


    public JPanel getter() {
        return this.homePanel;
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
        homePanel = new JPanel();
        homePanel.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Welcome to Library Management System");
        homePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        homePanel.add(panel1, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPanel = new JTabbedPane();
        panel1.add(tabbedPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return homePanel;
    }

}
