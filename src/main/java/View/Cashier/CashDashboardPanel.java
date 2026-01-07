/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Cashier;

import controller.CustomerController;
import controller.ProductController;
import controller.TransactionController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author nityayadav
 */
public class CashDashboardPanel extends JPanel {

    // Controllers
    private ProductController productController;
    private TransactionController transactionController;
    private CustomerController customerController;

    // Dynamic Labels
    private JLabel lblTotalProducts;
    private JLabel lblTotalCustomers;
    private JLabel lblTotalTransactions;
    private JLabel lblTotalSales;

    // Dynamic Panels
    private JPanel productsGrid;

    // Summary Labels
    private JLabel lblPendingOrders;
    private JLabel lblTodaySales;
    private JLabel lblCashInDrawer;
    private JLabel lblLowStockAlerts;

    public CashDashboardPanel() {
        // Initialize Controllers
        productController = new ProductController();
        transactionController = new TransactionController();
        customerController = new CustomerController();

        setLayout(new BorderLayout());
        add(createCashDashboardContent(), BorderLayout.CENTER);

        // Timer to refresh data every 5 seconds
        Timer timer = new Timer(5000, e -> refreshData());
        timer.start();

        // Initial Refresh
        refreshData();
    }

    public JPanel getContentPanel() {
        return this;
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

        // === Top 4 Cards ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 25, 25));
        cardsRow.setBackground(new Color(217, 217, 217));

        // Initialize Top Labels
        lblTotalProducts = new JLabel("0");
        lblTotalCustomers = new JLabel("0");
        lblTotalTransactions = new JLabel("0");
        lblTotalSales = new JLabel("Rs. 0");

        cardsRow.add(createSimpleCard("Total Products", lblTotalProducts, new Color(52, 152, 219)));
        cardsRow.add(createSimpleCard("Total Customers", lblTotalCustomers, new Color(46, 204, 113)));
        cardsRow.add(createSimpleCard("Total Transactions", lblTotalTransactions, new Color(155, 89, 182)));
        cardsRow.add(createSimpleCard("Total Sales", lblTotalSales, new Color(230, 126, 34)));

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
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel productsTitle = new JLabel("Popular Products Today");
        productsTitle.setFont(new Font("InaiMathi", Font.BOLD, 20));
        productsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        productsSection.add(productsTitle, BorderLayout.NORTH);

        productsGrid = new JPanel(new GridLayout(2, 3, 15, 15));
        productsGrid.setBackground(Color.WHITE);

        productsSection.add(productsGrid, BorderLayout.CENTER);
        lowerSection.add(productsSection);

        // Right: Quick Order Summary
        JPanel orderSummary = new JPanel(new BorderLayout());
        orderSummary.setBackground(Color.WHITE);
        orderSummary.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel summaryTitle = new JLabel("Today's Quick Summary");
        summaryTitle.setFont(new Font("InaiMathi", Font.BOLD, 20));
        summaryTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        orderSummary.add(summaryTitle, BorderLayout.NORTH);

        JPanel summaryContent = new JPanel(new GridLayout(4, 2, 10, 10));
        summaryContent.setBackground(Color.WHITE);

        // Initialize Summary Labels
        lblPendingOrders = new JLabel("0", SwingConstants.RIGHT);
        lblTodaySales = new JLabel("Rs. 0", SwingConstants.RIGHT);
        lblCashInDrawer = new JLabel("Rs. 0", SwingConstants.RIGHT);
        lblLowStockAlerts = new JLabel("0 items", SwingConstants.RIGHT);

        summaryContent.add(new JLabel("Pending Orders:"));
        summaryContent.add(lblPendingOrders);

        summaryContent.add(new JLabel("Today's Sales:"));
        summaryContent.add(lblTodaySales);

        summaryContent.add(new JLabel("Cash in Drawer:"));
        summaryContent.add(lblCashInDrawer);

        summaryContent.add(new JLabel("Low Stock Alerts:"));
        summaryContent.add(lblLowStockAlerts);

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
        if (iconPath != null && !iconPath.isEmpty()) {
            // Try loading from classpath first
            java.net.URL url = getClass().getClassLoader().getResource("images/" + iconPath);
            if (url != null) {
                icon.setIcon(new ImageIcon(url));
            } else {
                // Try absolute path (for uploaded images)
                icon.setIcon(new ImageIcon(iconPath));
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

    // UPDATED: Now accepts JLabel instead of String for value
    private JPanel createSimpleCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        titleLabel.setForeground(Color.GRAY);

        // Customize the passed label
        valueLabel.setFont(new Font("InaiMathi", Font.BOLD, 36));
        valueLabel.setForeground(color);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private void refreshData() {
        try {
            // Fetch data
            int totalProducts = productController.getTotalProducts();
            int totalCustomers = customerController.getTotalCustomers();
            int totalTx = transactionController.getTotalTransactions();
            double totalSales = transactionController.getTotalSales();

            // Fetch Summary Data
            double todaySales = transactionController.getTodaySales();
            int lowStockCount = productController.getLowStockProducts().size();

            // Update Top Cards
            lblTotalProducts.setText(String.format("%,d", totalProducts));
            lblTotalCustomers.setText(String.format("%,d", totalCustomers));
            lblTotalTransactions.setText(String.format("%,d", totalTx));
            lblTotalSales.setText("Rs. " + String.format("%,.0f", totalSales));

            // Update Summary
            lblPendingOrders.setText("0"); // No pending logic yet
            lblTodaySales.setText("Rs. " + String.format("%,.2f", todaySales));
            lblCashInDrawer.setText("Rs. " + String.format("%,.2f", todaySales)); // Assuming Cash in Drawer = Today's
                                                                                  // Sales
            lblLowStockAlerts.setText(lowStockCount + " items");

            // Update Popular Products
            productsGrid.removeAll();
            java.util.List<String> topProductIds = transactionController.getTopSellingProductIds(6);

            if (topProductIds.isEmpty()) {
                JLabel noData = new JLabel("No sales today yet.", SwingConstants.CENTER);
                noData.setForeground(Color.GRAY);
                productsGrid.add(noData);
            } else {
                for (String pID : topProductIds) {
                    model.Product p = productController.getProduct(pID);
                    if (p != null) {
                        productsGrid.add(createProductCard(p.getName(), "Rs. " + p.getPrice(), p.getImagePath()));
                    }
                }
            }
            productsGrid.revalidate();
            productsGrid.repaint();

        } catch (Exception e) {
            System.err.println("Error refreshing cashier dashboard: " + e.getMessage());
        }
    }
}
