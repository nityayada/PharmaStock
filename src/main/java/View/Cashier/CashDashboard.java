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
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author nityayadav
 */
public class CashDashboard extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CashDashboard.class.getName());
    private CashSidebarPanel cashSidebarPanel;
    private CashNavbarPanel cashNavbarPanel;
    private JPanel mainContentPanel;

    /**
     * Creates new form CashDashboard
     */
    public CashDashboard() {
        initComponents();
        initializeCashDashboard();
    }

    private void initializeCashDashboard() {
        getContentPane().setLayout(new BorderLayout());

        setTitle("PharmaStock Pro - Cashier Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // 1. Cashier Sidebar (full height on left)
        cashSidebarPanel = new CashSidebarPanel();
        cashSidebarPanel.setActiveButton("Dashboard"); // Highlight Dashboard
        getContentPane().add(cashSidebarPanel, BorderLayout.WEST);

        // 2. Right wrapper: Navbar + Content
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(217, 217, 217)); // Light & clean for cashier

        // Navbar
        cashNavbarPanel = new CashNavbarPanel();
        rightWrapper.add(cashNavbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createCashDashboardContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createCashDashboardContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

        // Header
        JLabel headerLabel = new JLabel("Cashier Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(44, 62, 80));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Scrollable content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(217, 217, 217));
        content.setBorder(BorderFactory.createEmptyBorder(20, 60, 60, 60));

        // === Top 4 Cards (keep as is) ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 25, 25));
        cardsRow.setBackground(new Color(217, 217, 217));

        cardsRow.add(createSimpleCard("Total Products", "6,553", new Color(52, 152, 219)));
        cardsRow.add(createSimpleCard("Total Customers", "7,986", new Color(46, 204, 113)));
        cardsRow.add(createSimpleCard("Total Transactions", "5,120", new Color(155, 89, 182)));
        cardsRow.add(createSimpleCard("Total Sales", "28,786", new Color(230, 126, 34)));

        content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));

        // === Lower Section: Recent/Popular Products Grid + Quick Order Summary ===
        JPanel lowerSection = new JPanel(new GridLayout(1, 2, 30, 0));
        lowerSection.setBackground(new Color(217, 217, 217));

        // Left: Popular Products Grid
        JPanel productsSection = new JPanel(new BorderLayout());
        productsSection.setBackground(Color.WHITE);
        productsSection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel productsTitle = new JLabel("Popular Products Today");
        productsTitle.setFont(new Font("InaiMathi", Font.BOLD, 20));
        productsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        productsSection.add(productsTitle, BorderLayout.NORTH);

        JPanel productsGrid = new JPanel(new GridLayout(2, 3, 15, 15));
        productsGrid.setBackground(Color.WHITE);

        // Sample products (you can replace with real images later)
        productsGrid.add(createProductCard("Vitamin C", "Rs. 850", "vitamin-icon.png"));
        productsGrid.add(createProductCard("Paracetamol", "Rs. 120", "paracetamol-icon.png"));
        productsGrid.add(createProductCard("Cough Syrup", "Rs. 450", "cough-icon.png"));
        productsGrid.add(createProductCard("Fish Oil", "Rs. 1,200", "fishoil-icon.png"));
        productsGrid.add(createProductCard("Baby Oil", "Rs. 300", "babyoil-icon.png"));
        productsGrid.add(createProductCard("Aloe Vera Gel", "Rs. 600", "aloe-icon.png"));

        productsSection.add(productsGrid, BorderLayout.CENTER);
        lowerSection.add(productsSection);

        // Right: Quick Order Summary (simple card)
        JPanel orderSummary = new JPanel(new BorderLayout());
        orderSummary.setBackground(Color.WHITE);
        orderSummary.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel summaryTitle = new JLabel("Today's Quick Summary");
        summaryTitle.setFont(new Font("InaiMathi", Font.BOLD, 20));
        summaryTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        orderSummary.add(summaryTitle, BorderLayout.NORTH);

        JPanel summaryContent = new JPanel(new GridLayout(4, 2, 10, 10));
        summaryContent.setBackground(Color.WHITE);

        summaryContent.add(new JLabel("Pending Orders:"));
        summaryContent.add(new JLabel("12", SwingConstants.RIGHT));

        summaryContent.add(new JLabel("Today's Sales:"));
        summaryContent.add(new JLabel("Rs. 15,420", SwingConstants.RIGHT));

        summaryContent.add(new JLabel("Cash in Drawer:"));
        summaryContent.add(new JLabel("Rs. 8,500", SwingConstants.RIGHT));

        summaryContent.add(new JLabel("Low Stock Alerts:"));
        summaryContent.add(new JLabel("3 items", SwingConstants.RIGHT));

        orderSummary.add(summaryContent, BorderLayout.CENTER);
        lowerSection.add(orderSummary);

        content.add(lowerSection);

        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    // Reusable small product card
    private JPanel createProductCard(String name, String price, String iconPath) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel icon = new JLabel();
        if (!java.beans.Beans.isDesignTime()) {
            java.net.URL url = getClass().getClassLoader().getResource("images/" + iconPath);
            if (url != null) {
                icon.setIcon(new ImageIcon(url));
            }
        }
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(icon, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        card.add(nameLabel, BorderLayout.NORTH);

        JLabel priceLabel = new JLabel(price, SwingConstants.CENTER);
        priceLabel.setFont(new Font("InaiMathi", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(52, 152, 219));
        card.add(priceLabel, BorderLayout.SOUTH);

        return card;
    }

    // Reusable simple card (already in your code)
    private JPanel createSimpleCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("InaiMathi", Font.BOLD, 36));
        valueLabel.setForeground(color);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new CashDashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
