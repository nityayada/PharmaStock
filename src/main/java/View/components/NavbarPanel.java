/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author nityayadav
 */
public class NavbarPanel extends JPanel {

    private JLabel welcomeLabel;
    private JTextField searchField;

    public NavbarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 139)); // Dark blue
        setPreferredSize(new java.awt.Dimension(1200, 60));

        // Logo
        JLabel logoLabel = new JLabel("PharmaStock");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(logoLabel, BorderLayout.WEST);

        // Right side: Welcome + Search
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rightPanel.add(welcomeLabel);

        searchField = new JTextField("Search anything", 20);
        rightPanel.add(searchField);

        add(rightPanel, BorderLayout.EAST);
    }

    // Getters for customization
    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }
}
