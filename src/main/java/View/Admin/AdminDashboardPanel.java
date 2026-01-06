/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import controller.CustomerController;
import controller.ProductController;
import controller.TransactionController;
import model.Product;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class AdminDashboardPanel extends JPanel {

    // Controllers
    private ProductController productController;
    private TransactionController transactionController;
    private CustomerController customerController;

    // Dynamic Labels
    private JLabel lblTotalCustomers;
    private JLabel lblTotalRevenue;
    private JLabel lblExpiryAlerts;
    private JLabel lblMedicinesAvailable;

    private JLabel lblTotalProductsCard; // In Product Card
    private JLabel lblLowStockCard; // In Product Card

    private JLabel lblQtySoldCard; // In Quick Report Card
    private JLabel lblInvoicesCard; // In Quick Report Card

    private JPanel pnlExpiryList; // Panel to hold dynamic expiry list

    private JLabel lblTotalCustomersBottom; // In Customers Card

    public AdminDashboardPanel() {
        // Initialize Controllers
        productController = new ProductController();
        transactionController = new TransactionController();
        customerController = new CustomerController();

        setLayout(new BorderLayout());

        // Timer to refresh data every 5 seconds (5000 ms)
        Timer timer = new Timer(5000, e -> refreshData());
        timer.start();
    }

    public JPanel getContentPanel() {
        JPanel content = createDashboardContent();
        refreshData(); // Initial load
        return content;
    }

    private JPanel createDashboardContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

        // Header
        JLabel headerLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.setBackground(new Color(217, 217, 217));
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // === TOP ROW: 4 Small Cards ===
        JPanel topRow = new JPanel(new GridLayout(1, 4, 20, 20));
        topRow.setBackground(new Color(217, 217, 217));

        // Initialize labels
        lblTotalCustomers = new JLabel("0");
        lblTotalRevenue = new JLabel("Rs. 0");
        lblExpiryAlerts = new JLabel("0");
        lblMedicinesAvailable = new JLabel("0");

        topRow.add(createIconCard("Total Customers", lblTotalCustomers, "customers-icon.png", new Color(41, 128, 185)));
        topRow.add(createIconCard("Total Revenue", lblTotalRevenue, "revenue-icon.png", new Color(39, 174, 96)));
        topRow.add(createIconCard("Expiry Alerts", lblExpiryAlerts, "alert-icon.png", new Color(142, 68, 173)));
        topRow.add(createIconCard("Medicines Available", lblMedicinesAvailable, "medicines-icon.png",
                new Color(230, 126, 34)));

        contentWrapper.add(topRow);
        contentWrapper.add(Box.createRigidArea(new Dimension(0, 40)));

        // === MIDDLE ROW: Product + Quick Report ===
        JPanel middleRow = new JPanel(new GridLayout(1, 2, 20, 20));
        middleRow.setBackground(new Color(217, 217, 217));

        // Product Card
        JPanel productCard = new JPanel(new GridLayout(3, 2, 20, 2));
        productCard.setBackground(Color.WHITE);
        productCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JLabel titleLabel = new JLabel("Product");
        titleLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        JLabel linkLabel = new JLabel("Go to Configuration \u2192");
        linkLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        linkLabel.setForeground(new Color(170, 170, 170)); // gray

        productCard.add(titleLabel);
        productCard.add(linkLabel);

        lblTotalProductsCard = new JLabel("0");
        lblTotalProductsCard.setFont(new Font("InaiMathi", Font.BOLD, 40));
        lblTotalProductsCard.setForeground(Color.BLACK);
        productCard.add(lblTotalProductsCard);

        lblLowStockCard = new JLabel("0");
        lblLowStockCard.setFont(new Font("InaiMathi", Font.BOLD, 40));
        lblLowStockCard.setForeground(Color.BLACK);
        productCard.add(lblLowStockCard);

        productCard.add(new JLabel("Total no of Medicines"));
        productCard.add(new JLabel("Low Stock Items")); // Changed from Categories
        middleRow.add(productCard);

        // Quick Report Card
        JPanel quickReportCard = new JPanel(new GridLayout(3, 2, 20, 2));
        quickReportCard.setBackground(Color.WHITE);
        quickReportCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JLabel QuickLabel = new JLabel("Quick Report");
        QuickLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        QuickLabel.setForeground(Color.BLACK);

        JLabel DateLabel = new JLabel(""); // Cleared, could be set to current month
        DateLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        DateLabel.setForeground(new Color(170, 170, 170)); // gray

        quickReportCard.add(QuickLabel);
        quickReportCard.add(DateLabel);

        lblQtySoldCard = new JLabel("0");
        lblQtySoldCard.setFont(new Font("InaiMathi", Font.BOLD, 40));
        lblQtySoldCard.setForeground(Color.BLACK);
        quickReportCard.add(lblQtySoldCard);

        lblInvoicesCard = new JLabel("0");
        lblInvoicesCard.setFont(new Font("InaiMathi", Font.BOLD, 40));
        lblInvoicesCard.setForeground(Color.BLACK);
        quickReportCard.add(lblInvoicesCard);

        quickReportCard.add(new JLabel("Qty of Medicines Sold"));
        quickReportCard.add(new JLabel("Invoices Generated"));

        middleRow.add(quickReportCard);

        contentWrapper.add(middleRow);
        contentWrapper.add(Box.createRigidArea(new Dimension(0, 40)));

        // === BOTTOM ROW: Near Expiry + Customers ===
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 20, 20));
        bottomRow.setBackground(new Color(217, 217, 217));

        // Near Expiry
        JPanel expiryCard = new JPanel(new BorderLayout());
        expiryCard.setBackground(Color.WHITE);
        expiryCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));

        JLabel expiryTitle = new JLabel("Near Expiry Medicines (30 Days)");
        expiryTitle.setFont(new Font("InaiMathi", Font.BOLD, 18));
        expiryCard.add(expiryTitle, BorderLayout.NORTH);

        pnlExpiryList = new JPanel(new GridLayout(2, 1, 0, 10)); // Container for list
        pnlExpiryList.setBackground(Color.WHITE);
        expiryCard.add(pnlExpiryList, BorderLayout.CENTER);

        bottomRow.add(expiryCard);

        // Customers Card
        JPanel customersCard = new JPanel(new GridLayout(3, 2, 20, 2));
        customersCard.setBackground(Color.WHITE);
        customersCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JLabel CusLabel = new JLabel("Customers");
        CusLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        CusLabel.setForeground(Color.BLACK);

        JLabel CustPageLabel = new JLabel("Go to Customer Page \u2192");
        CustPageLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        CustPageLabel.setForeground(new Color(170, 170, 170)); // gray

        customersCard.add(CusLabel);
        customersCard.add(CustPageLabel);

        lblTotalCustomersBottom = new JLabel("0");
        lblTotalCustomersBottom.setFont(new Font("InaiMathi", Font.BOLD, 40));
        lblTotalCustomersBottom.setForeground(Color.BLACK);
        customersCard.add(lblTotalCustomersBottom);

        // Placeholder for second col in this row
        customersCard.add(new JLabel(""));

        customersCard.add(new JLabel("Total no. of Customers"));
        customersCard.add(new JLabel("")); // Empty placeholder

        bottomRow.add(customersCard);

        contentWrapper.add(bottomRow);

        // Scroll pane for future content
        JScrollPane scrollPane = new JScrollPane(contentWrapper);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(217, 217, 217));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Reusable card with icon, overloaded to accept JLabel
    private JPanel createIconCard(String title, JLabel valueLabel, String iconPath, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel iconLabel = new JLabel();
        if (!java.beans.Beans.isDesignTime()) {
            java.net.URL url = getClass().getClassLoader().getResource("images/" + iconPath);
            if (url != null) {
                iconLabel.setIcon(new ImageIcon(url));
            }
        }
        card.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.GRAY);
        textPanel.add(titleLabel);

        valueLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        valueLabel.setForeground(color);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private void refreshData() {
        try {
            // Fetch data
            int totalCustomers = customerController.getTotalCustomers();
            double totalRevenue = transactionController.getTotalSales();
            int totalProducts = productController.getTotalProducts();
            int lowStockCount = productController.getLowStockProducts().size();
            int expiryCount = productController.getNearExpiryProducts(30).size();
            int totalTx = transactionController.getTotalTransactions();
            int totalQtySold = transactionController.getTotalQuantitySold();

            // Format Revenue
            String revenueStr = "Rs. " + String.format("%.2f", totalRevenue);

            // Update Labels
            lblTotalCustomers.setText(String.valueOf(totalCustomers));
            lblTotalRevenue.setText(revenueStr);
            lblExpiryAlerts.setText(String.valueOf(expiryCount));
            lblMedicinesAvailable.setText(String.valueOf(totalProducts));

            lblTotalProductsCard.setText(String.valueOf(totalProducts));
            lblLowStockCard.setText(String.valueOf(lowStockCount));

            lblQtySoldCard.setText(String.valueOf(totalQtySold));
            lblInvoicesCard.setText(String.valueOf(totalTx));

            lblTotalCustomersBottom.setText(String.valueOf(totalCustomers));

            // Update Expiry List
            pnlExpiryList.removeAll();
            List<Product> nearExpiry = productController.getNearExpiryProducts(30);
            // Show top 2
            int count = 0;
            for (Product p : nearExpiry) {
                if (count >= 2) {
                    break;
                }
                JLabel item = new JLabel((count + 1) + ". " + p.getName() + " (" + p.getExpiryDate() + ")");
                pnlExpiryList.add(item);
                count++;
            }
            if (count == 0) {
                pnlExpiryList.add(new JLabel("No near expiry items."));
            }

            pnlExpiryList.revalidate();
            pnlExpiryList.repaint();

        } catch (Exception e) {
            System.err.println("Error refreshing dashboard: " + e.getMessage());
        }
    }
}
