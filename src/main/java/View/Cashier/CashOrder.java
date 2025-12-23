/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Cashier;

import View.components.CashNavbarPanel;
import View.components.CashSidebarPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public class CashOrder extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CashOrder.class.getName());

    /**
     * Creates new form CashOrder
     */
    private CashSidebarPanel cashSidebarPanel;
    private CashNavbarPanel cashNavbarPanel;
    private JPanel mainContentPanel;

    public CashOrder() {
        initComponents();
        initializeCashOrderPage();
    }

    private void initializeCashOrderPage() {
        getContentPane().setLayout(new BorderLayout());

        setTitle("PharmaStock - Cashier Order");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // Sidebar
        cashSidebarPanel = new CashSidebarPanel();
        cashSidebarPanel.setActiveButton("Order");
        getContentPane().add(cashSidebarPanel, BorderLayout.WEST);

        // Right wrapper
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(217, 217, 217));

        // Navbar
        cashNavbarPanel = new CashNavbarPanel();
        rightWrapper.add(cashNavbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createCashOrderContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createCashOrderContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

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
        placeOrderBtn.setBackground(new Color(46, 204, 113));
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CashOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CashOrder().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
