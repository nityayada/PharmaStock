/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author nityayadav
 */
public class CashSidebarPanel extends JPanel {

    private final Color SIDEBAR_BG = new Color(44, 62, 80); // Slightly lighter dark gray for cashier
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color HIGHLIGHT_BG = new Color(52, 152, 219); // Friendly blue
    private final Color HOVER_BG = new Color(73, 80, 87);

    // Fields for buttons
    private JButton dashboardBtn, productBtn, orderBtn, accountBtn;

    public CashSidebarPanel() {
        setLayout(new BorderLayout());
        setBackground(SIDEBAR_BG);
        setPreferredSize(new Dimension(240, 400));
        setBorder(new EmptyBorder(20, 0, 20, 0));

        // Top Logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SIDEBAR_BG);
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JLabel logoLabel = new JLabel("PharmaStock");
        logoLabel.setFont(new Font("InaiMathi", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        // Safe icon loading
        if (!java.beans.Beans.isDesignTime()) {
            java.net.URL iconUrl = getClass().getClassLoader().getResource("images/logo-icon.png");
            if (iconUrl != null) {
                logoLabel.setIcon(new ImageIcon(iconUrl));
                logoLabel.setIconTextGap(10);
            }
        }
        logoPanel.add(logoLabel);
        add(logoPanel, BorderLayout.NORTH);

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(30, 20, 20, 20));

        // MENU Label
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setForeground(new Color(173, 181, 189));
        menuLabel.setFont(new Font("InaiMathi", Font.BOLD, 12));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Dashboard (default active for cashier dashboard)
        dashboardBtn = createMenuItem("Dashboard", "dashboard-icon.png", false);
        menuPanel.add(dashboardBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        productBtn = createMenuItem("Product", "products-icon.png", false);
        menuPanel.add(productBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        orderBtn = createMenuItem("Order", "order-icon.png", false);
        menuPanel.add(orderBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        accountBtn = createMenuItem("Account", "account-icon.png", false);
        menuPanel.add(accountBtn);

        menuPanel.add(Box.createVerticalGlue());
        add(menuPanel, BorderLayout.CENTER);
    }

    private JButton createMenuItem(String text, String iconPath, boolean isActive) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        button.setIconTextGap(15);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon loading
        if (!java.beans.Beans.isDesignTime()) {
            java.net.URL iconUrl = getClass().getClassLoader().getResource("images/" + iconPath);
            if (iconUrl != null) {
                button.setIcon(new ImageIcon(iconUrl));
            }
        }

        if (isActive) {
            button.setBackground(HIGHLIGHT_BG);
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
        } else {
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(HOVER_BG);
                    button.setOpaque(true);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(null);
                    button.setOpaque(false);
                }
            });
        }

        return button;
    }

    // Public method to highlight active menu from pages
    public void setActiveButton(String buttonName) {
        JButton[] allButtons = {dashboardBtn, productBtn, orderBtn, accountBtn};
        for (JButton btn : allButtons) {
            if (btn != null) {
                btn.setBackground(null);
                btn.setOpaque(false);
                btn.setForeground(TEXT_COLOR);
            }
        }

        JButton target = null;
        switch (buttonName) {
            case "Dashboard" ->
                target = dashboardBtn;
            case "Product" ->
                target = productBtn;
            case "Order" ->
                target = orderBtn;
            case "Account" ->
                target = accountBtn;
        }

        if (target != null) {
            target.setBackground(HIGHLIGHT_BG);
            target.setOpaque(true);
            target.setForeground(Color.WHITE);
        }
    }
}
