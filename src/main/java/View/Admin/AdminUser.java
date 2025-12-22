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
public class AdminUser extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminUser.class.getName());

    private SidebarPanel sidebarPanel;
    private NavbarPanel navbarPanel;
    private JPanel mainContentPanel;

    /**
     * Creates new form AdminUser
     */
    public AdminUser() {
        initComponents();
        initializeUserPage();
    }

    private void initializeUserPage() {
        getContentPane().setLayout(new BorderLayout());

        setTitle("PharmaStock Pro - User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));

        // Sidebar
        sidebarPanel = new SidebarPanel();
        sidebarPanel.setActiveButton("User");
        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        // Right wrapper
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(new Color(217, 217, 217)); // As per Figma

        // Navbar
        navbarPanel = new NavbarPanel();
        rightWrapper.add(navbarPanel, BorderLayout.NORTH);

        // Main content
        mainContentPanel = createUserContent();
        rightWrapper.add(mainContentPanel, BorderLayout.CENTER);

        getContentPane().add(rightWrapper, BorderLayout.CENTER);

        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private JPanel createUserContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

        // Header
        JLabel headerLabel = new JLabel("User", SwingConstants.LEFT);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Main vertical layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(217, 217, 217));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // === Search + Role Combo + Add New ===
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(new Color(217, 217, 217));

        // Left: Search + Role Combo
        JPanel leftFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftFilters.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)
        ));
        leftFilters.add(searchField);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Roles", "All", "Admin", "Cashier"});
        roleCombo.setPreferredSize(new Dimension(180, 45));
        leftFilters.add(roleCombo);

        filterPanel.add(leftFilters, BorderLayout.WEST);

        // Right: Add New Button
        JButton addNewBtn = new JButton("+ Add New");
        addNewBtn.setBackground(new Color(14, 40, 107));
        addNewBtn.setForeground(Color.WHITE);
        addNewBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        addNewBtn.setPreferredSize(new Dimension(150, 45));
        addNewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel rightBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtn.setBackground(new Color(217, 217, 217));
        rightBtn.add(addNewBtn);
        filterPanel.add(rightBtn, BorderLayout.EAST);

        content.add(filterPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Table ===
        String[] columns = {"Name", "Email", "Phone Number", "Role", "Action"};
        Object[][] data = {
            {"Nitya Yadav", "nityayadav09@gmail.com", "9864892021", "Admin", ""},
            {"Aditya Uprety", "upretyaditya@gmail.com", "9864899091", "Cashier", ""},
            {"Kiran Dahal", "kirandahal03@gmail.com", "9864892021", "Cashier", ""},
            {"Raj Pandit", "panditraj@gmail.com", "9864892021", "Cashier", ""},
            {"Alwin Maharaj", "alwin09@gmail.com", "9864892021", "Cashier", ""},
            {"Nitya Yadav", "nityayadav09@gmail.com", "9864892021", "Admin", ""},
            {"Aditya Uprety", "upretyaditya@gmail.com", "9864899091", "Cashier", ""},
            {"Kiran Dahal", "kirandahal03@gmail.com", "9864892021", "Cashier", ""},
            {"Raj Pandit", "panditraj@gmail.com", "9864892021", "Cashier", ""},
            {"Alwin Maharaj", "alwin09@gmail.com", "9864892021", "Cashier", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Action column
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(55);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

        // Role color
        table.getColumn("Role").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel((String) value, SwingConstants.CENTER);
            label.setOpaque(true);
            if ("Admin".equals(value)) {
                label.setBackground(new Color(41, 128, 185, 50));
                label.setForeground(new Color(41, 128, 185));
            } else if ("Cashier".equals(value)) {
                label.setBackground(new Color(39, 174, 96, 50));
                label.setForeground(new Color(39, 174, 96));
            }
            return label;
        });

        // Action column: View + Edit + Delete buttons
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setToolTipText("View");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);

            JButton editBtn = new JButton("✎");
            editBtn.setToolTipText("Edit");
            editBtn.setForeground(new Color(39, 174, 96));
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);

            JButton deleteBtn = new JButton("🗑");
            deleteBtn.setToolTipText("Delete");
            deleteBtn.setForeground(new Color(231, 76, 60));
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
            java.util.logging.Logger.getLogger(AdminUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new AdminUser().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
