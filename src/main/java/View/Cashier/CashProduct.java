/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Cashier;

import View.components.CashNavbarPanel;
import View.components.CashSidebarPanel;
import controller.ProductController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import model.Product;

/**
 *
 * @author nityayadav
 */
public class CashProduct extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CashProduct.class.getName());
    private CashSidebarPanel cashSidebarPanel;
    private CashNavbarPanel cashNavbarPanel;
    private JPanel mainContentPanel;
    private ProductController productController;
    private JTable table;
    private DefaultTableModel model;

    public CashProduct() {
        initComponents();
        productController = new ProductController();
        initializeCashProductPage();
    }

    private void initializeCashProductPage() {
        getContentPane().setLayout(new BorderLayout());

        setTitle("PharmaStock - Cashier Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // Sidebar
        cashSidebarPanel = new CashSidebarPanel();
        cashSidebarPanel.setActiveButton("Product");
        getContentPane().add(cashSidebarPanel, BorderLayout.WEST);

        // Right wrapper
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(245, 245, 245));

        // Navbar
        cashNavbarPanel = new CashNavbarPanel();
        rightWrapper.add(cashNavbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createCashProductContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createCashProductContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // Header
        JLabel headerLabel = new JLabel("Product", SwingConstants.LEFT);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(44, 62, 80));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Main vertical layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(245, 245, 245));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // === Top 3 Cards (connected to ProductController) ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 25, 20));
        cardsRow.setBackground(new Color(245, 245, 245));

        cardsRow.add(createIconCard("Total Products", String.valueOf(productController.getTotalProducts()), "product-icon.png", new Color(52, 152, 219)));
        cardsRow.add(createIconCard("Low Stock Product", String.valueOf(productController.getLowStockProducts().size()), "lowstock-icon.png", new Color(230, 126, 34)));
        cardsRow.add(createIconCard("Out of Stock Product", String.valueOf(productController.getOutOfStockProducts().size()), "outofstock-icon.png", new Color(231, 76, 60)));

        content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));

        // === Search Bar ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        searchPanel.setBackground(new Color(245, 245, 245));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        searchField.addActionListener(e -> updateTable(productController.searchProducts(searchField.getText()))); // Real search
        searchPanel.add(searchField);

        content.add(searchPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Table (connected to ProductController) ===
        String[] columns = {"Product ID", "Product Name", "Items", "Price (Rs)", "Status", "Action"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Action
            }
        };

        table = new JTable(model);
        table.setRowHeight(55);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

        // Load initial real data
        updateTable(productController.getAllProducts());

        // Status color
        table.getColumn("Status").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel((String) value, SwingConstants.CENTER);
            label.setOpaque(true);
            if ("Available".equals(value)) {
                label.setBackground(new Color(39, 174, 96, 50));
                label.setForeground(new Color(39, 174, 96));
            } else if ("Low Stock".equals(value)) {
                label.setBackground(new Color(230, 126, 34, 50));
                label.setForeground(new Color(230, 126, 34));
            } else if ("Out of Stock".equals(value)) {
                label.setBackground(new Color(231, 76, 60, 50));
                label.setForeground(new Color(231, 76, 60));
            }
            return label;
        });

        // Action column: View button only (cashier-friendly)
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setToolTipText("View Product");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);
            viewBtn.addActionListener(e -> viewProduct(row));

            actionPanel.add(viewBtn);
            return actionPanel;
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        content.add(scrollPane);

        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    // Load/update table with real products
    private void updateTable(List<Product> productList) {
        model.setRowCount(0); // Clear table
        for (Product p : productList) {
            model.addRow(new Object[]{
                p.getProductId(),
                p.getName(),
                p.getQuantity(),
                p.getPrice(),
                p.getStatus(),
                "" // Action placeholder
            });
        }
    }

    // View product details (simple dialog)
    private void viewProduct(int row) {
        String id = (String) model.getValueAt(row, 0);
        Product p = productController.getAllProducts().stream()
                .filter(prod -> prod.getProductId().equals(id))
                .findFirst()
                .orElse(null);

        if (p != null) {
            JOptionPane.showMessageDialog(this,
                    "Product ID: " + p.getProductId() + "\n"
                    + "Name: " + p.getName() + "\n"
                    + "Items: " + p.getQuantity() + "\n"
                    + "Price: Rs. " + p.getPrice() + "\n"
                    + "Status: " + p.getStatus(),
                    "Product Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JPanel createIconCard(String title, String value, String iconPath, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
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
        valueLabel.setFont(new Font("InaiMathi", Font.BOLD, 36));
        valueLabel.setForeground(color);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

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
            java.util.logging.Logger.getLogger(CashProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CashProduct().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
