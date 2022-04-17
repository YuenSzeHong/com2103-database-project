package com2103.lmsProject;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Locale;

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
        Panel1 = new JPanel();
        Panel1.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("The current date  is: (DD-MM-YYYY)");
        Panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateShown = new JTextField();
        Font dateShownFont = this.$$$getFont$$$(null, -1, 20, dateShown.getFont());
        if (dateShownFont != null) dateShown.setFont(dateShownFont);
        dateShown.setText("");
        Panel1.add(dateShown, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enterButton = new JButton();
        Font enterButtonFont = this.$$$getFont$$$(null, -1, 20, enterButton.getFont());
        if (enterButtonFont != null) enterButton.setFont(enterButtonFont);
        enterButton.setText("Change");
        Panel1.add(enterButton, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        Panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        Panel1.add(spacer2, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        return Panel1;
    }

}


