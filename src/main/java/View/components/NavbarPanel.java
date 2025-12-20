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
public class NavbarPanel extends JPanel {

    private JTextField searchField;

    public NavbarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Light gray like in image
        setPreferredSize(new Dimension(0, 70)); // Fixed height
        setBorder(new EmptyBorder(10, 30, 10, 30)); // Padding left/right

        // Left: Welcome Label
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        leftPanel.setBackground(null);

        JLabel welcomeLabel = new JLabel("Welcome, ");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.GRAY);

        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        adminLabel.setForeground(Color.BLACK);

        leftPanel.add(welcomeLabel);
        leftPanel.add(adminLabel);

        add(leftPanel, BorderLayout.WEST);

        // Right: Search Bar
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setBackground(null);

        // Search Icon
//        JLabel searchIcon = new JLabel(new ImageIcon(getClass().getResource("/resources/images/search-icon.png")));
//        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel searchIcon = new JLabel();
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (!java.beans.Beans.isDesignTime()) {
            java.net.URL iconUrl
                    = getClass().getClassLoader().getResource("images/search-icon.png");

            if (iconUrl != null) {
                searchIcon.setIcon(new ImageIcon(iconUrl));
            } else {
                System.err.println("Search icon not found: images/search-icon.png");
            }
        }
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Search Field
        searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));
        searchField.setBackground(Color.WHITE);

        // Rounded corners (custom painting)
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(220, 220, 220), 20), // 20 = radius
                BorderFactory.createEmptyBorder(0, 40, 0, 15)
        ));

        // Panel to hold icon + field
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setBackground(null);
        searchContainer.add(searchIcon, BorderLayout.WEST);
        searchContainer.add(searchField, BorderLayout.CENTER);

        rightPanel.add(searchContainer);

        add(rightPanel, BorderLayout.EAST);
    }

    // Optional: Getter for search field (to add listener later)
    public JTextField getSearchField() {
        return searchField;
    }

    // Custom rounded border class
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {

        private Color color;
        private int radius;

        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius, radius / 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius;
            insets.top = insets.bottom = radius / 2;
            return insets;
        }
    }
}
