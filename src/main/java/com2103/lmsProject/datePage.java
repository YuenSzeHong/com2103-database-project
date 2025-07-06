package com2103.lmsProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class datePage extends JFrame {
    private JTextField dateShown;
    private JButton enterButton;
    private JPanel Panel1;
    private BufferedReader br;
    private PrintWriter writer;
    private final File config;
    private Instant currentDate;
    private final homePage instance;


    public datePage(homePage instance) {
        this.instance = instance;
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

        dateShown.setText(currentDate.atZone(ZoneOffset.UTC).toLocalDate().toString());

        enterButton.addActionListener(e -> {
            String date = dateShown.getText();
            try {
                Instant dateToSet = LocalDate.parse(date).atStartOfDay().toInstant(ZoneOffset.UTC);
                this.instance.setCurrentDate(dateToSet);
                writer = new PrintWriter(config);
                writer.print(dateToSet.toString());
                writer.flush();
                writer.close();
                dateShown.setText(date);
            } catch (IllegalArgumentException IAE) {
                JOptionPane.showMessageDialog(null, "Invalid date format");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error in reading date.txt");
            }

        });
    }

    public JPanel getter() {
        return this.Panel1;
    }

}


