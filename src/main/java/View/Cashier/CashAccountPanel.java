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
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to main panel

        // Main container with centered content
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(new Color(248, 250, 252));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 250, 252));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JLabel headerLabel = new JLabel("My Account", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        headerLabel.setForeground(new Color(30, 41, 59));

        JLabel welcomeLabel = new JLabel("Welcome back, Silvia!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeLabel.setForeground(new Color(100, 116, 139));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(welcomeLabel, BorderLayout.SOUTH);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        container.add(headerPanel, gbc);

        // Profile Card
        JPanel profileCard = createProfileCard();
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        container.add(profileCard, gbc);

        panel.add(container, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProfileCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(50, 80, 50, 80) // Increased padding
        ));
        card.setMinimumSize(new Dimension(700, 700)); // Set minimum size

        // Profile header with avatar
        JPanel profileHeader = new JPanel(new BorderLayout(30, 0));
        profileHeader.setBackground(Color.WHITE);
        profileHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        // Avatar circle
        JPanel avatarPanel = new JPanel();
        avatarPanel.setPreferredSize(new Dimension(140, 140));
        avatarPanel.setBackground(new Color(236, 253, 245));
        avatarPanel.setBorder(BorderFactory.createLineBorder(new Color(167, 243, 208), 3));
        avatarPanel.setLayout(new GridBagLayout());

        JLabel profilePic = new JLabel("👤");
        profilePic.setFont(new Font("Segoe UI", Font.PLAIN, 70));
        profilePic.setForeground(new Color(16, 185, 129));
        avatarPanel.add(profilePic);

        // Profile info
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel nameLabel = new JLabel("Silvia Sharma");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nameLabel.setForeground(new Color(30, 41, 59));
        gbc.gridy = 0;
        infoPanel.add(nameLabel, gbc);

        JLabel roleLabel = new JLabel("Cashier");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        roleLabel.setForeground(new Color(16, 185, 129));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        gbc.gridy++;
        infoPanel.add(roleLabel, gbc);

        profileHeader.add(avatarPanel, BorderLayout.WEST);
        profileHeader.add(infoPanel, BorderLayout.CENTER);

        card.add(profileHeader, BorderLayout.NORTH);

        // Contact info
        JPanel contactPanel = new JPanel(new GridBagLayout());
        contactPanel.setBackground(Color.WHITE);
        contactPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20)); // Added padding

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email with icon
        JPanel emailPanel = new JPanel(new BorderLayout(15, 0));
        emailPanel.setBackground(Color.WHITE);
        emailPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel emailIcon = new JLabel("📧");
        emailIcon.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        JLabel emailLabel = new JLabel("silvia.sharma@pharmastock.com");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        emailLabel.setForeground(new Color(100, 116, 139));
        emailPanel.add(emailIcon, BorderLayout.WEST);
        emailPanel.add(emailLabel, BorderLayout.CENTER);
        gbc.gridy = 0;
        contactPanel.add(emailPanel, gbc);

        // Phone with icon
        JPanel phonePanel = new JPanel(new BorderLayout(15, 0));
        phonePanel.setBackground(Color.WHITE);
        phonePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel phoneIcon = new JLabel("📱");
        phoneIcon.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        JLabel phoneLabel = new JLabel("+977 986 489 2021");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        phoneLabel.setForeground(new Color(100, 116, 139));
        phonePanel.add(phoneIcon, BorderLayout.WEST);
        phonePanel.add(phoneLabel, BorderLayout.CENTER);
        gbc.gridy++;
        contactPanel.add(phonePanel, gbc);

        // Join date with icon
        JPanel joinPanel = new JPanel(new BorderLayout(15, 0));
        joinPanel.setBackground(Color.WHITE);
        joinPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel joinIcon = new JLabel("📅");
        joinIcon.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        JLabel joinLabel = new JLabel("Joined: 15th March 2024");
        joinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        joinLabel.setForeground(new Color(100, 116, 139));
        joinPanel.add(joinIcon, BorderLayout.WEST);
        joinPanel.add(joinLabel, BorderLayout.CENTER);
        gbc.gridy++;
        contactPanel.add(joinPanel, gbc);

        card.add(contactPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 0, 50)); // Added padding

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton editBtn = new JButton("Edit Profile");
        stylePrimaryButton(editBtn);
        gbc.gridy = 0;
        buttonPanel.add(editBtn, gbc);

        JButton logoutBtn = new JButton("Log Out");
        styleDangerButton(logoutBtn);
        gbc.gridy++;
        buttonPanel.add(logoutBtn, gbc);

        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private void stylePrimaryButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(59, 130, 246));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(59, 130, 246), 1),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setMinimumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(37, 99, 235));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(37, 99, 235), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(59, 130, 246));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(59, 130, 246), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)
                ));
            }
        });
    }

    private void styleDangerButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(239, 68, 68));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(239, 68, 68), 1),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setMinimumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 38, 38));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 38, 38), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(239, 68, 68));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(239, 68, 68), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)
                ));
            }
        });
    }
}
