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
public class AdminTransaction extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminTransaction.class.getName());
    private SidebarPanel sidebarPanel;
    private NavbarPanel navbarPanel;
    private JPanel mainContentPanel;

    /**
     * Creates new form AdminTransaction
     */
    public AdminTransaction() {
        initComponents();
        initializeTransactionPage();
    }

    private void initializeTransactionPage() {
        getContentPane().setLayout(new BorderLayout());

        setTitle("PharmaStock Pro - Transaction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // Sidebar
        sidebarPanel = new SidebarPanel();
        sidebarPanel.setActiveButton("Transactions");
        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        // Right wrapper
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(240, 242, 245));

        // Navbar
        navbarPanel = new NavbarPanel();
        rightWrapper.add(navbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createTransactionContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createTransactionContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 242, 245));

        // Header
        JLabel headerLabel = new JLabel("Transaction", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Main vertical layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(240, 242, 245));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // === Top 2 Cards ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 2, 30, 20));
        cardsRow.setBackground(new Color(240, 242, 245));
        cardsRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        cardsRow.add(createIconCard("Total Transaction", "500", "transaction-icon.png", new Color(41, 128, 185)));
        cardsRow.add(createIconCard("Total Sale", "23,000", "sale-icon.png", new Color(39, 174, 96)));

        content.add(cardsRow);

        // === Search Bar ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        searchPanel.setBackground(new Color(240, 242, 245));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)
        ));

        searchPanel.add(searchField);
        content.add(searchPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Table ===
        String[] columns = {"Transaction ID", "Customer ID", "Date", "Time", "Amount (Rs)", "Action"};
        Object[][] data = {
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""},
            {"TRX988", "C324", "01-01-2025", "09:42", "32,000", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Action column
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(55);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Action column: View + Delete buttons
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setToolTipText("View");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);

            JButton deleteBtn = new JButton("🗑");
            deleteBtn.setToolTipText("Delete");
            deleteBtn.setForeground(new Color(231, 76, 60));
            deleteBtn.setBorderPainted(false);
            deleteBtn.setContentAreaFilled(false);

            actionPanel.add(viewBtn);
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
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
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
            java.util.logging.Logger.getLogger(AdminTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new AdminTransaction().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
