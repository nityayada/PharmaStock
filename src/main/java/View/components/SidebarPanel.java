package View.components;

import View.WelcomeView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SidebarPanel extends JPanel {

    private final Color SIDEBAR_BG = new Color(44, 62, 80); // Dark gray
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color HIGHLIGHT_BG = new Color(0, 123, 255); // Bootstrap blue for active
    private final Color HOVER_BG = new Color(73, 80, 87);
    private WelcomeView parentFrame;

    // Add these private fields to access buttons later
    private JButton dashboardBtn, productsBtn, categoriesBtn, transactionsBtn, Customerbtn, UserBtn;

    public SidebarPanel(WelcomeView parentFrame) {
        this.parentFrame = parentFrame; // The instance variable
        setLayout(new BorderLayout());
        setBackground(SIDEBAR_BG);
        setPreferredSize(new Dimension(240, 400));
        setBorder(new EmptyBorder(20, 0, 20, 0));

        // Top Logo Section
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SIDEBAR_BG);
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JLabel logoLabel = new JLabel("PharmaStock");
        logoLabel.setFont(new Font("InaiMathi", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        logoPanel.add(Box.createVerticalGlue()); // Push items up
        add(logoPanel, BorderLayout.NORTH);

        // Menu Items Panel
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

        // Dashboard - now starts inactive (false)
        dashboardBtn = createMenuItem("Dashboard", false);
        menuPanel.add(dashboardBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Products
        productsBtn = createMenuItem("Products", false);
        menuPanel.add(productsBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Transactions
        transactionsBtn = createMenuItem("Transactions", false);
        menuPanel.add(transactionsBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // OTHERS Label
        JLabel othersLabel = new JLabel("OTHERS");
        othersLabel.setForeground(new Color(173, 181, 189));
        othersLabel.setFont(new Font("InaiMathi", Font.BOLD, 12));
        othersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(othersLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Customer
        Customerbtn = createMenuItem("Customer", false);
        menuPanel.add(Customerbtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // User
        UserBtn = createMenuItem("User", false);
        menuPanel.add(UserBtn);

        menuPanel.add(Box.createVerticalGlue()); // Push items up
        add(menuPanel, BorderLayout.CENTER);

        // Logout button at the bottom
        JButton logoutBtn = createMenuItem("Log Out", false);
        logoutBtn.setForeground(new Color(231, 76, 60)); // Red color for logout

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Extra space above logout
        menuPanel.add(logoutBtn);

        // Navigation listeners - use mainFrame.switchPage to switch between the panel
        dashboardBtn.addActionListener(e -> {
            parentFrame.switchPage("Dashboard"); // ← change mainFrame → parentFrame
            setActiveButton("Dashboard");
        });

        productsBtn.addActionListener(e -> {
            parentFrame.switchPage("Products");
            setActiveButton("Products");
        });

        transactionsBtn.addActionListener(e -> {
            parentFrame.switchPage("Transactions");
            setActiveButton("Transactions");
        });

        Customerbtn.addActionListener(e -> {
            parentFrame.switchPage("Customer");
            setActiveButton("Customer");
        });

        UserBtn.addActionListener(e -> {
            parentFrame.switchPage("User");
            setActiveButton("User");
        });

        logoutBtn.addActionListener(e -> logout()); // If you have logout
    }

    private JButton createMenuItem(String text, boolean isActive) {
        JButton button = new JButton(text);

        button.setHorizontalAlignment(SwingConstants.LEFT);// Align text to left
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));// Full width, fixed height
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));// Same for preferred
        button.setFocusPainted(false);// Remove focus border blue outline when clicked
        button.setBorderPainted(false);// Remove default button border
        button.setContentAreaFilled(false);// Remove default background fill
        button.setForeground(TEXT_COLOR); // Set text color likely dark gray/black
        button.setFont(new Font("InaiMathi", Font.PLAIN, 16)); // Custom font, plain style, 16px
        button.setIconTextGap(15);// Space between icon and text if icon added later
        button.setAlignmentX(Component.LEFT_ALIGNMENT);// align button to left in container
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));// change cursor to hand on hover

        if (isActive) {
            button.setBackground(HIGHLIGHT_BG);
            button.setOpaque(true); // Make background visible
            button.setForeground(Color.WHITE); // White text on colored background
        } else {
            // hover effect - only apply to non-active buttons
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    if (!isActive) {
                        button.setBackground(HOVER_BG);
                        button.setOpaque(true);
                    }
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    if (!isActive) {
                        button.setBackground(null);
                        button.setOpaque(false);
                    }
                }
            });
        }

        return button;
    }

    public void setActiveButton(String buttonName) {
        JButton[] allButtons = {dashboardBtn, productsBtn, categoriesBtn, transactionsBtn, Customerbtn, UserBtn};
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
            case "Products" ->
                target = productsBtn;
            case "Transactions" ->
                target = transactionsBtn;
            case "Customer" ->
                target = Customerbtn;
            case "User" ->
                target = UserBtn;
        }

        if (target != null) {
            target.setBackground(HIGHLIGHT_BG);
            target.setOpaque(true);
            target.setForeground(Color.WHITE);
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(parentFrame,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            //tell WelcomeView to go back to login screen
            parentFrame.showLoginScreen();
        }
    }
}
