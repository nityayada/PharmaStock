/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

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

/**
 *
 * @author nityayadav
 */
public class AdminDashboardPanel extends JPanel {

    public AdminDashboardPanel() {
        setLayout(new BorderLayout());
    }

    public JPanel getContentPanel() {
        return createDashboardContent();
    }

    private JPanel createDashboardContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 242, 245));

        // Header
        JLabel headerLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

//        // Your existing cards, charts, placeholders, etc. here...
//        // Example placeholder
//        JLabel placeholder = new JLabel("Admin Dashboard Content Goes Here", SwingConstants.CENTER);
//        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 24));
//        placeholder.setForeground(Color.GRAY);
//        panel.add(placeholder, BorderLayout.CENTER);
// Main scrollable content
        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.setBackground(new Color(217, 217, 217));
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // === TOP ROW: 4 Small Cards ===
        JPanel topRow = new JPanel(new GridLayout(1, 4, 20, 20));
        topRow.setBackground(new Color(217, 217, 217));

        topRow.add(createIconCard("Total Customers", "500", "customers-icon.png", new Color(41, 128, 185)));
        topRow.add(createIconCard("Total Revenue", "Rs.10,000", "revenue-icon.png", new Color(39, 174, 96)));
        topRow.add(createIconCard("Expiry Alerts", "5", "alert-icon.png", new Color(142, 68, 173)));
        topRow.add(createIconCard("Medicines Available", "600", "medicines-icon.png", new Color(230, 126, 34)));

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
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        JLabel titleLabel = new JLabel("Product");
        titleLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        JLabel linkLabel = new JLabel("Go to Configuration →");
        linkLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        linkLabel.setForeground(new Color(170, 170, 170)); // gray

        productCard.add(titleLabel);
        productCard.add(linkLabel);

        JLabel prod600 = new JLabel("600");
        prod600.setFont(new Font("InaiMathi", Font.BOLD, 40));
        prod600.setForeground(Color.BLACK);
        productCard.add(prod600);

        JLabel cat30 = new JLabel("30");
        cat30.setFont(new Font("InaiMathi", Font.BOLD, 40));
        cat30.setForeground(Color.BLACK);
        productCard.add(cat30);

        productCard.add(new JLabel("Total no of Medicines"));
        productCard.add(new JLabel("Medicine Categories"));
        middleRow.add(productCard);

        // Quick Report Card
        JPanel quickReportCard = new JPanel(new GridLayout(3, 2, 20, 2));
        quickReportCard.setBackground(Color.WHITE);
        quickReportCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        JLabel QuickLabel = new JLabel("Quick Report");
        QuickLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        QuickLabel.setForeground(Color.BLACK);

        JLabel DateLabel = new JLabel("January 2022");
        DateLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        DateLabel.setForeground(new Color(170, 170, 170)); // gray

        quickReportCard.add(QuickLabel);
        quickReportCard.add(DateLabel);

        JLabel sold700 = new JLabel("700");
        sold700.setFont(new Font("InaiMathi", Font.BOLD, 40));
        sold700.setForeground(Color.BLACK);
        quickReportCard.add(sold700);

        JLabel inv500 = new JLabel("500");
        inv500.setFont(new Font("InaiMathi", Font.BOLD, 40));
        inv500.setForeground(Color.BLACK);
        quickReportCard.add(inv500);

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
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JLabel expiryTitle = new JLabel("Near Expiry Medicines");
        expiryTitle.setFont(new Font("InaiMathi", Font.BOLD, 18));
        expiryCard.add(expiryTitle, BorderLayout.NORTH);

        JPanel expiryList = new JPanel(new GridLayout(2, 1, 0, 10));
        expiryList.add(new JLabel("1. Vitamin-C        Expires: 15 Dec 2025 (28 days)"));
        expiryList.add(new JLabel("2. Cough Syrup    Expires: 22 Dec 2025 (35 days)"));
        expiryCard.add(expiryList, BorderLayout.CENTER);

        bottomRow.add(expiryCard);

        // Customers Card
        JPanel customersCard = new JPanel(new GridLayout(3, 2, 20, 2));
        customersCard.setBackground(Color.WHITE);
        customersCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        JLabel CusLabel = new JLabel("Customers");
        CusLabel.setFont(new Font("InaiMathi", Font.BOLD, 18));
        CusLabel.setForeground(Color.BLACK);

        JLabel CustPageLabel = new JLabel("Go to Customer Page →");
        CustPageLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        CustPageLabel.setForeground(new Color(170, 170, 170)); // gray

        customersCard.add(CusLabel);
        customersCard.add(CustPageLabel);

        JLabel cust500 = new JLabel("500");
        cust500.setFont(new Font("InaiMathi", Font.BOLD, 40));
        cust500.setForeground(Color.BLACK);
        customersCard.add(cust500);

        JLabel paraLabel = new JLabel("Paracetamol");
        paraLabel.setFont(new Font("InaiMathi", Font.BOLD, 24));
        customersCard.add(paraLabel);

        customersCard.add(new JLabel("Total no. of Customers"));
        customersCard.add(new JLabel("Frequently bought item"));

        bottomRow.add(customersCard);

        contentWrapper.add(bottomRow);

        // Scroll pane for future content
        JScrollPane scrollPane = new JScrollPane(contentWrapper);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(217, 217, 217));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    // Reusable card with icon

    private JPanel createIconCard(String title, String value, String iconPath, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

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

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        valueLabel.setForeground(color);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }
}
