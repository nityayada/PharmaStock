/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Cashier;

import View.components.CashNavbarPanel;
import View.components.CashSidebarPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author nityayadav
 */
public class CashAccount extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CashAccount.class.getName());

    /**
     * Creates new form CashAccount
     */
    private CashSidebarPanel cashSidebarPanel;
    private CashNavbarPanel cashNavbarPanel;
    private JPanel mainContentPanel;

    public CashAccount() {
        initComponents();
        initializeCashAccount();
    }

    private void initializeCashAccount() {
        getContentPane().setLayout(new BorderLayout());

        setTitle("PharmaStock Pro - Cashier Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // Sidebar
        cashSidebarPanel = new CashSidebarPanel();
        cashSidebarPanel.setActiveButton("Account");
        getContentPane().add(cashSidebarPanel, BorderLayout.WEST);

        // Right wrapper
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(217, 217, 217));

        // Navbar
        cashNavbarPanel = new CashNavbarPanel();
        rightWrapper.add(cashNavbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createCashAccountContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createCashAccountContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CashAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CashAccount().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
