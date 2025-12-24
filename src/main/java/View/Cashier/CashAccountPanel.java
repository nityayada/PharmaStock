/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Cashier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author nityayadav
 */
public class CashAccountPanel extends JPanel {

    public CashAccountPanel() {
        setLayout(new BorderLayout());
        add(createAccountContent(), BorderLayout.CENTER);
    }

    public JPanel getContentPanel() {
        return this;
    }

    private JPanel createAccountContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        // Header
        JLabel headerLabel = new JLabel("My Account", SwingConstants.CENTER);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(44, 62, 80));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Center profile card
        JPanel profileCard = new JPanel();
        profileCard.setLayout(new GridBagLayout());
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(60, 80, 60, 80)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Profile Picture (placeholder)
        JLabel profilePic = new JLabel("👤");
        profilePic.setFont(new Font("InaiMathi", Font.PLAIN, 80));
        profilePic.setForeground(new Color(52, 152, 219));
        gbc.gridy = 0;
        profileCard.add(profilePic, gbc);

        // Name
        JLabel nameLabel = new JLabel("Silvia Sharma");
        nameLabel.setFont(new Font("InaiMathi", Font.BOLD, 28));
        nameLabel.setForeground(new Color(44, 62, 80));
        gbc.gridy++;
        profileCard.add(nameLabel, gbc);

        // Role
        JLabel roleLabel = new JLabel("Cashier");
        roleLabel.setFont(new Font("InaiMathi", Font.PLAIN, 18));
        roleLabel.setForeground(new Color(46, 204, 113));
        gbc.gridy++;
        profileCard.add(roleLabel, gbc);

        // Email
        JLabel emailLabel = new JLabel("silvia.sharma@pharmastock.com");
        emailLabel.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        emailLabel.setForeground(Color.GRAY);
        gbc.gridy++;
        profileCard.add(emailLabel, gbc);

        // Phone
        JLabel phoneLabel = new JLabel("+977 986 489 2021");
        phoneLabel.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        phoneLabel.setForeground(Color.GRAY);
        gbc.gridy++;
        profileCard.add(phoneLabel, gbc);

        // Join Date
        JLabel joinLabel = new JLabel("Joined: 15th March 2024");
        joinLabel.setFont(new Font("InaiMathi", Font.PLAIN, 14));
        joinLabel.setForeground(Color.GRAY);
        gbc.gridy++;
        profileCard.add(joinLabel, gbc);

        // Today's Stats (simple)
        gbc.gridy += 2;
        gbc.insets = new Insets(30, 0, 10, 0);

        JLabel statsTitle = new JLabel("Today's Performance");
        statsTitle.setFont(new Font("InaiMathi", Font.BOLD, 20));
        profileCard.add(statsTitle, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel salesToday = new JLabel("Sales Today: Rs. 12,450");
        salesToday.setFont(new Font("InaiMathi", Font.PLAIN, 18));
        salesToday.setForeground(new Color(52, 152, 219));
        profileCard.add(salesToday, gbc);

        JLabel ordersToday = new JLabel("Orders Processed: 28");
        ordersToday.setFont(new Font("InaiMathi", Font.PLAIN, 18));
        ordersToday.setForeground(new Color(46, 204, 113));
        gbc.gridy++;
        profileCard.add(ordersToday, gbc);

        // Edit & Logout Buttons
        gbc.gridy += 2;
        gbc.insets = new Insets(40, 0, 0, 0);

        JButton editBtn = new JButton("Edit Profile");
        editBtn.setBackground(new Color(52, 152, 219));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        editBtn.setPreferredSize(new Dimension(200, 50));
        profileCard.add(editBtn, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);

        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        logoutBtn.setPreferredSize(new Dimension(200, 50));
        profileCard.add(logoutBtn, gbc);

        panel.add(profileCard, BorderLayout.CENTER);

        return panel;
    }
}
