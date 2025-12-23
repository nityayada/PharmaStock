/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Admin;

import View.components.SidebarPanel;
import View.components.NavbarPanel;
import controller.ProductController;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import model.Product;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class AdminProduct extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminProduct.class.getName());

    private SidebarPanel sidebarPanel;
    private NavbarPanel navbarPanel;
    private JPanel mainContentPanel;
    private ProductController productController;
    private JTable table;
    private DefaultTableModel model;

    public AdminProduct() {
        initComponents();
        productController = new ProductController();
        initializeProductPage();
    }

    private void initializeProductPage() {
        getContentPane().setLayout(new BorderLayout());
        setTitle("PharmaStock - Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // Sidebar
        sidebarPanel = new SidebarPanel();
        sidebarPanel.setActiveButton("Products");
        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        // Right wrapper
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(240, 242, 245));

        // Navbar
        navbarPanel = new NavbarPanel();
        rightWrapper.add(navbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createProductContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);
        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createProductContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

        // Header
        JLabel headerLabel = new JLabel("Product", SwingConstants.LEFT);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Main vertical layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(217, 217, 217));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // === Top 3 Cards (connected to ProductController) ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 20, 20));
        cardsRow.setBackground(new Color(217, 217, 217));

        cardsRow.add(createIconCard("Total Product", String.valueOf(productController.getTotalProducts()), "product-icon.png", new Color(142, 68, 173)));
        cardsRow.add(createIconCard("Low Stock Product", String.valueOf(productController.getLowStockProducts().size()), "lowstock-icon.png", new Color(230, 126, 34)));
        cardsRow.add(createIconCard("Out of Stock Product", String.valueOf(productController.getOutOfStockProducts().size()), "outofstock-icon.png", new Color(231, 76, 60)));

        content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));

        // === Search + Add Button ===
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(new Color(217, 217, 217));

        // Left: Search
        JPanel leftFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftFilters.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.addActionListener(e -> updateTable(productController.searchProducts(searchField.getText())));
        leftFilters.add(searchField);

        filterPanel.add(leftFilters, BorderLayout.WEST);

        // Right: Add New Button
        JButton addNewBtn = new JButton("+ Add New");
        addNewBtn.setBackground(new Color(14, 40, 107));
        addNewBtn.setForeground(Color.WHITE);
        addNewBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        addNewBtn.setPreferredSize(new Dimension(150, 40));
        addNewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addNewBtn.addActionListener(e -> addNewProduct());
        JPanel rightBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtn.setBackground(new Color(217, 217, 217));
        rightBtn.add(addNewBtn);
        filterPanel.add(rightBtn, BorderLayout.EAST);

        content.add(filterPanel);
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
        table.setRowHeight(50);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

        // Load initial data from ProductController
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

        // Action column with View/Edit/Delete buttons
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setToolTipText("View");
            viewBtn.addActionListener(e -> viewProduct(row));

            JButton editBtn = new JButton("✎");
            editBtn.setForeground(new Color(39, 174, 96));
            editBtn.setToolTipText("Edit");
            editBtn.addActionListener(e -> editProduct(row));

            JButton deleteBtn = new JButton("🗑");
            deleteBtn.setForeground(new Color(231, 76, 60));
            deleteBtn.setToolTipText("Delete");
            deleteBtn.addActionListener(e -> deleteProduct(row));

            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);
            deleteBtn.setBorderPainted(false);
            deleteBtn.setContentAreaFilled(false);

            actionPanel.add(viewBtn);
            actionPanel.add(editBtn);
            actionPanel.add(deleteBtn);
            return actionPanel;
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        content.add(scrollPane);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    // Load/update table with products
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

    // Edit product (simple dialog)
    private void editProduct(int row) {
        String id = (String) model.getValueAt(row, 0);
        Product p = productController.getAllProducts().stream()
                .filter(prod -> prod.getProductId().equals(id))
                .findFirst()
                .orElse(null);

        if (p != null) {
            String newName = JOptionPane.showInputDialog(this, "New Name:", p.getName());
            if (newName != null) {
                p.setName(newName);
                productController.updateProduct(id, p);
                updateTable(productController.getAllProducts());
                updateCards(); // Refresh cards
            }
        }
    }

    // Delete product
    private void deleteProduct(int row) {
        String id = (String) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productController.deleteProduct(id);
            updateTable(productController.getAllProducts());
            updateCards();
        }
    }

    // Add new product (simple dialog)
    private void addNewProduct() {
        String id = JOptionPane.showInputDialog(this, "Product ID:");
        String name = JOptionPane.showInputDialog(this, "Name:");
        String qtyStr = JOptionPane.showInputDialog(this, "Quantity:");
        String priceStr = JOptionPane.showInputDialog(this, "Price:");

        if (id != null && name != null && qtyStr != null && priceStr != null) {
            try {
                int qty = Integer.parseInt(qtyStr);
                double price = Double.parseDouble(priceStr);
                Product newProduct = new Product(id, name, qty, price);
                productController.addProduct(newProduct);
                updateTable(productController.getAllProducts());
                updateCards();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity or price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Update top cards with real data
    private void updateCards() {
        // Assuming your cardsRow is accessible or recreate it
        // For simplicity, you can call this after any change
        // In real code, store card labels as fields and update them
        JOptionPane.showMessageDialog(this, "Cards updated with new data!"); // Placeholder - implement real update
    }

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
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> new AdminProduct().setVisible(true));
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AdminProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new AdminProduct().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
