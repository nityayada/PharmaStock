/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author nityayadav
 */
public class CashNavbarPanel extends JPanel {

    private JTextField searchField;

    public CashNavbarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));
        setPreferredSize(new Dimension(0, 70));
        setBorder(new EmptyBorder(10, 30, 10, 30));

        // Left: Welcome Label (VERTICAL)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(null);

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setFont(new Font("InaiMathi", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.GRAY);

        JLabel adminLabel = new JLabel("Cashier");
        adminLabel.setFont(new Font("InaiMathi", Font.BOLD, 22));
        adminLabel.setForeground(Color.BLACK);

//      Align labels to the left
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        adminLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

//       Optional spacing between labels
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        leftPanel.add(adminLabel);

        add(leftPanel, BorderLayout.WEST);

    }

    public JTextField getSearchField() {
        return searchField;
    }

    private static class RoundedBorder extends javax.swing.border.AbstractBorder {

        private final Color color;
        private final int radius;

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
