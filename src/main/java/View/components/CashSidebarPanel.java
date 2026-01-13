/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

//import View.Cashier.CashMainFrame;
import View.WelcomeView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
    private WelcomeView parentFrame;

    // Fields for buttons
    private JButton dashboardBtn, productBtn, orderBtn, accountBtn;

    public CashSidebarPanel(WelcomeView parentFrame) {
        this.parentFrame = parentFrame;
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
        logoPanel.add(logoLabel);

        logoPanel.add(Box.createVerticalGlue()); // Push items up
        add(logoPanel, BorderLayout.NORTH); //add the logopanel to the parent container 

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
        dashboardBtn = createMenuItem("Dashboard", false);
        menuPanel.add(dashboardBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        productBtn = createMenuItem("Product", false);
        menuPanel.add(productBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        orderBtn = createMenuItem("Order", false);
        menuPanel.add(orderBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        accountBtn = createMenuItem("Account", false);
        menuPanel.add(accountBtn);

        menuPanel.add(Box.createVerticalGlue());
        add(menuPanel, BorderLayout.CENTER);

        // Logout button at the bottom
        JButton logoutBtn = createMenuItem("Log Out", false);
        logoutBtn.setForeground(new Color(231, 76, 60)); // Red color for logout

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space above
        menuPanel.add(logoutBtn);

        dashboardBtn.addActionListener(e -> {
            parentFrame.switchPage("Dashboard");
            setActiveButton("Dashboard");
        });

        productBtn.addActionListener(e -> {
            parentFrame.switchPage("Product");
            setActiveButton("Product");
        });

        orderBtn.addActionListener(e -> {
            parentFrame.switchPage("Order");
            setActiveButton("Order");
        });

        accountBtn.addActionListener(e -> {
            parentFrame.switchPage("Account");
            setActiveButton("Account");
        });

        logoutBtn.addActionListener(e -> logout());
    }

    private JButton createMenuItem(String text, boolean isActive) {
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

    private void navigateTo(String pageName) {
        SwingUtilities.getWindowAncestor(this).dispose(); // Close current frame
        try {
            Class<?> pageClass = Class.forName("View.Cashier." + pageName);
            JFrame newPage = (JFrame) pageClass.getDeclaredConstructor().newInstance();
            newPage.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error opening page: " + ex.getMessage(), "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(parentFrame,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            parentFrame.showLoginScreen(); //important call
        }
    }
}
