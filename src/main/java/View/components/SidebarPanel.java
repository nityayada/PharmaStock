/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.*;
import javax.swing.*;

import javax.swing.border.EmptyBorder;

/**
 *
 * @author nityayadav
 */
public class SidebarPanel extends JPanel {

    private JButton dashboardBtn, categoriesBtn, productBtn, transactionBtn, customerBtn, userBtn;

    private final Color DARK_BLUE = new Color(14, 40, 107); // #0E286B
    private final Color WHITE = Color.WHITE;

    public SidebarPanel() {
        setLayout(new BorderLayout());
        setBackground(WHITE);
        setPreferredSize(new Dimension(250, 800));

        // Top Panel - Only Logo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(WHITE);
        topPanel.setBorder(new EmptyBorder(40, 20, 40, 20)); // Padding
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("PharmaStock");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 28)); // Closest built-in to InaiMathi; change to "InaiMathi" if installed
        logoLabel.setForeground(DARK_BLUE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(logoLabel);

        add(topPanel, BorderLayout.NORTH);

        // Main Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(WHITE);
        menuPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // MENU Label (optional - not in your latest description, but in screenshot)
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setForeground(Color.GRAY);
        menuLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Menu Buttons
        dashboardBtn = createMenuButton("Dashboard", "dashboard-icon.png");
        dashboardBtn.setBackground(DARK_BLUE); // Highlighted since on dashboard page
        dashboardBtn.setForeground(WHITE);
        menuPanel.add(dashboardBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        categoriesBtn = createMenuButton("Categories", "categories-icon.png");
        menuPanel.add(categoriesBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        productBtn = createMenuButton("Product", "product-icon.png");
        menuPanel.add(productBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        transactionBtn = createMenuButton("Transaction", "transaction-icon.png");
        menuPanel.add(transactionBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // OTHERS Label
        JLabel othersLabel = new JLabel("OTHERS");
        othersLabel.setForeground(Color.GRAY);
        othersLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        othersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(othersLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        customerBtn = createMenuButton("Customer", "customer-icon.png");
        menuPanel.add(customerBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        userBtn = createMenuButton("User", "user-icon.png");
        menuPanel.add(userBtn);

        menuPanel.add(Box.createVerticalGlue()); // Push to bottom if needed

        add(menuPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(getClass().getResource("/resources/images/" + iconPath)));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false); // Transparent background by default
        button.setForeground(WHITE);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Size close to 12pt feel
        button.setIconTextGap(15);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect (turns dark blue)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button != dashboardBtn) { // Don't change highlighted one
                    button.setForeground(DARK_BLUE);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != dashboardBtn) {
                    button.setForeground(WHITE);
                }
            }
        });

        return button;
    }

    // Public method to change highlighted button when navigating pages
    public void setActiveButton(String active) {
        // Reset all
        JButton[] buttons = {dashboardBtn, categoriesBtn, productBtn, transactionBtn, customerBtn, userBtn};
        for (JButton btn : buttons) {
            btn.setBackground(null);
            btn.setForeground(WHITE);
        }

        // Highlight selected
        switch (active) {
            case "Dashboard" -> {
                dashboardBtn.setBackground(DARK_BLUE);
                dashboardBtn.setForeground(WHITE);
            }
            case "Categories" -> {
                categoriesBtn.setBackground(DARK_BLUE);
                categoriesBtn.setForeground(WHITE);
            }
            case "Product" -> {
                productBtn.setBackground(DARK_BLUE);
                productBtn.setForeground(WHITE);
            }
            case "Transaction" -> {
                transactionBtn.setBackground(DARK_BLUE);
                transactionBtn.setForeground(WHITE);
            }
            // Add more if needed
        }
    }

    // Add listeners (call from controller)
    public void addDashboardListener(java.awt.event.ActionListener listener) {
        dashboardBtn.addActionListener(listener);
    }

    public void addCategoriesListener(java.awt.event.ActionListener listener) {
        categoriesBtn.addActionListener(listener);
    }

    public void addProductListener(java.awt.event.ActionListener listener) {
        productBtn.addActionListener(listener);
    }

    public void addTransactionListener(java.awt.event.ActionListener listener) {
        transactionBtn.addActionListener(listener);
    }

    public void addCustomerListener(java.awt.event.ActionListener listener) {
        customerBtn.addActionListener(listener);
    }

    public void addUserListener(java.awt.event.ActionListener listener) {
        userBtn.addActionListener(listener);
    }
}
