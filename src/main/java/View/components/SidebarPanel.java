package View.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SidebarPanel extends JPanel {

    private final Color SIDEBAR_BG = new Color(52, 58, 64); // Dark gray
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color HIGHLIGHT_BG = new Color(0, 123, 255); // Bootstrap blue for active
    private final Color HOVER_BG = new Color(73, 80, 87);

    public SidebarPanel() {
        setLayout(new BorderLayout());
        setBackground(SIDEBAR_BG);
        setPreferredSize(new Dimension(260, 800));
        setBorder(new EmptyBorder(20, 0, 20, 0));

        // Top Logo Section
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SIDEBAR_BG);
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JLabel logoLabel = new JLabel("PharmaStock");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
//        logoLabel.setIcon(new ImageIcon(getClass().getResource("/resources/images/logo-icon.png"))); // Optional logo icon
//       Design-time safe image loading
        if (!java.beans.Beans.isDesignTime()) {
            java.net.URL iconUrl
                    = getClass().getClassLoader().getResource("images/logo-icon.png");

            if (iconUrl != null) {
                logoLabel.setIcon(new ImageIcon(iconUrl));
                logoLabel.setIconTextGap(10);
            } else {
                System.err.println("Logo icon not found: images/logo-icon.png");
            }
        }
        logoPanel.add(logoLabel);
        add(logoPanel, BorderLayout.NORTH);

        // Menu Items Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(30, 20, 20, 20));

        // MENU Label
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setForeground(new Color(173, 181, 189));
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Dashboard - Highlighted
        JButton dashboardBtn = createMenuItem("Dashboard", "dashboard-icon.png", true);
        menuPanel.add(dashboardBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Products
        JButton productsBtn = createMenuItem("Products", "products-icon.png", false);
        menuPanel.add(productsBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Categories
        JButton categoriesBtn = createMenuItem("Categories", "categories-icon.png", false);
        menuPanel.add(categoriesBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Transactions
        JButton transactionsBtn = createMenuItem("Transactions", "transactions-icon.png", false);
        menuPanel.add(transactionsBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // OTHERS Label
        JLabel othersLabel = new JLabel("OTHERS");
        othersLabel.setForeground(new Color(173, 181, 189));
        othersLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        othersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(othersLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Customer
        JButton Customerbtn = createMenuItem("Customer", "settings-icon.png", false);
        menuPanel.add(Customerbtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // User
        JButton UserBtn = createMenuItem("User", "User-icon.png", false);
        menuPanel.add(UserBtn);

        menuPanel.add(Box.createVerticalGlue()); // Push items up

        add(menuPanel, BorderLayout.CENTER);
    }

    private JButton createMenuItem(String text, String iconPath, boolean isActive) {
        JButton button = new JButton(text);
//        button.setIcon(new ImageIcon(getClass().getResource("/resources/images/" + iconPath)));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setIconTextGap(15);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isActive) {
            button.setBackground(HIGHLIGHT_BG);
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
        } else {
            // Hover effect
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
}
