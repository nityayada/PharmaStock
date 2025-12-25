/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Cashier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nityayadav
 */
public class CashOrderPanel extends JPanel {

    public CashOrderPanel() {
        setLayout(new BorderLayout());
        add(createCashOrderContent(), BorderLayout.CENTER);
    }

    public JPanel getContentPanel() {
        return this;
    }

    private JPanel createCashOrderContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        // Header
        JLabel headerLabel = new JLabel("New Order", SwingConstants.LEFT);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(44, 62, 80));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Split pane: Left (Products) | Right (Cart)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.65); // 65% left for products
        splitPane.setResizeWeight(0.65);
        splitPane.setBorder(null);

        // Left: Product Search & Grid
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(217, 217, 217));

        // Search Bar
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        searchBarPanel.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search product by name or ID");
        searchField.setPreferredSize(new Dimension(500, 45));
        searchField.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        searchBarPanel.add(searchField);

        leftPanel.add(searchBarPanel, BorderLayout.NORTH);

        // Product Grid (simple 3-column grid of product cards)
        JPanel productGrid = new JPanel(new GridLayout(0, 3, 20, 20));
        productGrid.setBackground(new Color(217, 217, 217));
        productGrid.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Sample products
        productGrid.add(createProductCard("Paracetamol 500mg", "Rs. 120", "pain-icon.png"));
        productGrid.add(createProductCard("Vitamin C 1000mg", "Rs. 850", "vitamin-icon.png"));
        productGrid.add(createProductCard("Cough Syrup 100ml", "Rs. 450", "cough-icon.png"));
        productGrid.add(createProductCard("Baby Oil 60ml", "Rs. 200", "babyoil-icon.png"));
        productGrid.add(createProductCard("Aloe Vera Gel", "Rs. 600", "aloe-icon.png"));
        productGrid.add(createProductCard("Fish Oil Omega 3", "Rs. 1,200", "fishoil-icon.png"));

        JScrollPane productScroll = new JScrollPane(productGrid);
        productScroll.setBorder(null);
        leftPanel.add(productScroll, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        // Right: Cart / Order Summary
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel cartTitle = new JLabel("Order Summary");
        cartTitle.setFont(new Font("InaiMathi", Font.BOLD, 22));
        cartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cartPanel.add(cartTitle, BorderLayout.NORTH);

        // Cart Table (items added)
        String[] cartColumns = {"Product", "Qty", "Price", "Subtotal"};
        Object[][] cartData = {}; // Empty initially

        DefaultTableModel cartModel = new DefaultTableModel(cartData, cartColumns);
        JTable cartTable = new JTable(cartModel);
        cartTable.setRowHeight(45);
        cartTable.getTableHeader().setBackground(new Color(220, 220, 220));

        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBorder(null);
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Summary Totals
        JPanel totalsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        totalsPanel.setBackground(Color.WHITE);
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        totalsPanel.add(new JLabel("Sub Total:"));
        totalsPanel.add(new JLabel("Rs. 0.00", SwingConstants.RIGHT));

        totalsPanel.add(new JLabel("Tax (10%):"));
        totalsPanel.add(new JLabel("Rs. 0.00", SwingConstants.RIGHT));

        totalsPanel.add(new JLabel("Discount:"));
        totalsPanel.add(new JLabel("Rs. 0.00", SwingConstants.RIGHT));

        totalsPanel.add(new JLabel("Total:"));
        JLabel totalLabel = new JLabel("Rs. 0.00", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        totalLabel.setForeground(new Color(52, 152, 219));
        totalsPanel.add(totalLabel);

        cartPanel.add(totalsPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(cartPanel);

        panel.add(splitPane, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomButtons.setBackground(new Color(217, 217, 217));

        JButton clearBtn = new JButton("Clear Cart");
        clearBtn.setPreferredSize(new Dimension(150, 45));
        bottomButtons.add(clearBtn);

        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setBackground(new Color(14, 40, 107));
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        placeOrderBtn.setPreferredSize(new Dimension(200, 50));
        bottomButtons.add(placeOrderBtn);

        panel.add(bottomButtons, BorderLayout.SOUTH);

        return panel;
    }

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
}
