/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Admin;

import View.components.SidebarPanel;
import View.components.NavbarPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nityayadav
 */
public class AdminProduct extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminProduct.class.getName());
    private SidebarPanel sidebarPanel;
    private NavbarPanel navbarPanel;
    private JPanel mainContentPanel;

    /**
     * Creates new form AdminProduct
     */
    public AdminProduct() {
        initComponents();
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

        revalidate(); // layout updates means ensures components are positioned correctly.
        repaint(); // visual refresh means ensures the updated UI is visible.
        setLocationRelativeTo(null); // help to place the page into the center 
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

        // === Top 4 Cards ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 20, 20));
        cardsRow.setBackground(new Color(217, 217, 217));

        cardsRow.add(createIconCard("Total Categories", "11", "categories-icon.png", new Color(41, 128, 185)));
        cardsRow.add(createIconCard("Total Types", "5", "types-icon.png", new Color(39, 174, 96)));
        cardsRow.add(createIconCard("Total Product", "600", "product-icon.png", new Color(142, 68, 173)));
        cardsRow.add(createIconCard("Out of Stock Product", "0", "outofstock-icon.png", new Color(231, 76, 60)));

        content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));

        // === Search + Filters + Add Button ===
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(new Color(217, 217, 217));

        // Left: Search + Combos
        JPanel leftFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftFilters.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(300, 40));
        leftFilters.add(searchField);

        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Categories", "All"});
        categoryCombo.setPreferredSize(new Dimension(180, 40));
        leftFilters.add(categoryCombo);

        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Types", "All"});
        typeCombo.setPreferredSize(new Dimension(180, 40));
        leftFilters.add(typeCombo);

        filterPanel.add(leftFilters, BorderLayout.WEST);

        // Right: Add New Button
        JButton addNewBtn = new JButton("+ Add New");
        addNewBtn.setBackground(new Color(14, 40, 107));
        addNewBtn.setForeground(Color.WHITE);
        addNewBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        addNewBtn.setPreferredSize(new Dimension(150, 40));
        addNewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel rightBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtn.setBackground(new Color(217, 217, 217));
        rightBtn.add(addNewBtn);
        filterPanel.add(rightBtn, BorderLayout.EAST);

        content.add(filterPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Table ===
        String[] columns = {"Product ID", "Product Name", "Category", "Items", "Price (Rs)", "Status", "Action"};
        Object[][] data = {
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2,000", "Available", ""},
            {"P0685", "Blackmores Vit C 1000mg", "Vitamin & Health", "540", "8,000", "Available", ""},
            {"P0998", "Nature Republic Aloe Vera", "Skincare", "48", "5,000", "Available", ""},
            {"P0389", "Minyak Telon My Baby 60ml", "Baby Care", "20", "200", "Low Stock", ""},
            {"P0322", "Vitacid 0.025% 15gr", "Skincare", "0", "0", "Empty", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Available", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Available", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Empty", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Empty", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Available", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Low Stock", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Low Stock", ""},
            {"P0321", "Paracetamol Kalbe 500mg", "Pain Relief", "789", "2000", "Available", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only Action column
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(50);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

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
            } else if ("Empty".equals(value)) {
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
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);

            JButton editBtn = new JButton("✎");
            editBtn.setForeground(new Color(39, 174, 96));
            editBtn.setToolTipText("Edit");
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);

            JButton deleteBtn = new JButton("🗑");
            deleteBtn.setForeground(new Color(231, 76, 60));
            deleteBtn.setToolTipText("Delete");
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
