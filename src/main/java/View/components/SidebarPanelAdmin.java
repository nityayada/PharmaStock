package View.components;

import javax.swing.*;
import java.awt.*;

public class SidebarPanelAdmin extends JPanel {

    // Menu items as instance variables (so we can access them)
    private JPanel dashboardPanel;
    private JPanel productPanel;
    private JPanel transactionPanel;
    private JPanel customerPanel;

    public SidebarPanelAdmin() {
        // Set fixed size
        setPreferredSize(new Dimension(250, 700));
        setMinimumSize(new Dimension(250, 700));
        setMaximumSize(new Dimension(250, 700));

        // Set background color (make it very visible)
        setBackground(new Color(25, 25, 112)); // Dark blue

        // Use BorderLayout
        setLayout(new BorderLayout());

        // Add visible border for debugging
        setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2)); // Remove later

        // Build the sidebar
        buildSidebar();
    }

    private void buildSidebar() {
        // Create main container
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(25, 25, 112));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("PharmaStock");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // MENU section
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuLabel.setForeground(new Color(180, 180, 220));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Create menu items
        dashboardPanel = createMenuItem("Dashboard", "88", true);
        productPanel = createMenuItem("Product", null, false);
        transactionPanel = createMenuItem("Transaction", null, false);

        // OTHERS section
        JLabel othersLabel = new JLabel("OTHERS");
        othersLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        othersLabel.setForeground(new Color(180, 180, 220));
        othersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        othersLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));

        customerPanel = createMenuItem("Customer", null, false);

        // Add all components
        container.add(title);
        container.add(menuLabel);
        container.add(Box.createVerticalStrut(5));
        container.add(dashboardPanel);
        container.add(Box.createVerticalStrut(10));
        container.add(productPanel);
        container.add(Box.createVerticalStrut(10));
        container.add(transactionPanel);
        container.add(Box.createVerticalStrut(20));
        container.add(othersLabel);
        container.add(Box.createVerticalStrut(5));
        container.add(customerPanel);
        container.add(Box.createVerticalGlue());

        add(container, BorderLayout.NORTH);
    }

    private JPanel createMenuItem(String text, String badge, boolean isActive) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(isActive ? new Color(65, 105, 225) : new Color(25, 25, 112));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Make it visually distinct
        if (isActive) {
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                    BorderFactory.createEmptyBorder(10, 13, 10, 13)
            ));
        }

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(isActive ? Color.WHITE : new Color(200, 200, 220));

        if (badge != null) {
            JLabel badgeLabel = new JLabel(badge);
            badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            badgeLabel.setForeground(Color.YELLOW);
            panel.add(badgeLabel, BorderLayout.EAST);
        }

        panel.add(label, BorderLayout.WEST);

        return panel;
    }

    // Getter methods for menu items (for adding click listeners)
    public JPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public JPanel getProductPanel() {
        return productPanel;
    }

    public JPanel getTransactionPanel() {
        return transactionPanel;
    }

    public JPanel getCustomerPanel() {
        return customerPanel;
    }
}
