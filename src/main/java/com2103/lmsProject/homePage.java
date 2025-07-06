package com2103.lmsProject;


import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class homePage extends JFrame {

    private JPanel homePanel;
    private JButton button1;
    private JTabbedPane tabbedPanel;
    private JLabel dateText;
    private Instant currentDate;
    private final File config;
    private BufferedReader br;
    private PrintWriter writer;

    public homePage(Connection connection) {

        /*create your Jform and add it your tabbedPanel here
          about addTab() method: addTab("yourTabbedPanelNameHere", yourJformFileName.getter())
          build getter() method in your (YourJformName).java file.
         */

        config = new File("date.txt");
        try {
            config.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating date.txt");
            System.exit(1);
        }
        try {
            br = new BufferedReader(new FileReader(config));
        } catch (FileNotFoundException e) {
            System.out.println("Error in reading date.txt");
            System.exit(1);
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                currentDate = Instant.parse(line);
            }
            if (currentDate == null) {
                currentDate = Instant.now();
                writer = new PrintWriter(config);
                writer.println(currentDate);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error in reading date.txt");
            System.exit(1);
        }


        dateText.setText("Today is " + currentDate.atZone(ZoneOffset.UTC).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("en", "US"))));

        usersPage users = new usersPage(connection);
        tabbedPanel.addTab("User", users.getter());

        book books = new book(connection);
        tabbedPanel.addTab("Book", books.getter());

        borrowedPage borrowedPages = new borrowedPage(connection);
        tabbedPanel.addTab("Book borrowing", borrowedPages.getter());

        datePage datePages = new datePage(this);
        tabbedPanel.addTab("Date setting", datePages.getter());

        rankPage rankPages = new rankPage(connection);
        tabbedPanel.addTab("Rank setting", rankPages.getter());
    }

    public void setCurrentDate(Instant currentDate) {
        this.currentDate = currentDate;
        System.out.println(currentDate);
        dateText.setText("Today is " + this.currentDate.atZone(ZoneOffset.UTC).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("en", "US"))));
    }

    public JPanel getter() {
        return this.homePanel;
    }


}