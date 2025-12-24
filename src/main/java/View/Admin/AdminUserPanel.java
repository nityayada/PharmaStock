/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import View.components.SidebarPanel;
import View.components.NavbarPanel;
import controller.UserController;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import model.User;
import java.util.List;

/**
 *
 * @author nityayadav
 */
public class AdminUserPanel extends JPanel {

    private UserController userController;
    private JTable table;
    private DefaultTableModel model;

    public AdminUserPanel() {
        setLayout(new BorderLayout());
        userController = new UserController();
    }

    public JPanel getContentPanel() {
        return createUserContent();
    }

    private JPanel createUserContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

//        // Your existing header, filters, table code here...
//        // Example
//        JTable table = new JTable(new DefaultTableModel(new String[]{"Name", "Email"}, 0));
//        panel.add(new JScrollPane(table), BorderLayout.CENTER);
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
        searchField.addActionListener(e -> updateTable(userController.searchUsers(searchField.getText())));
        leftFilters.add(searchField);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Roles", "All", "Admin", "Cashier"});
        roleCombo.setPreferredSize(new Dimension(180, 45));
        roleCombo.addActionListener(e -> {
            String role = (String) roleCombo.getSelectedItem();
            if (role.equals("All")) {
                updateTable(userController.getAllUsers());
            } else {
                updateTable(userController.getUsersByRole(role));
            }
        });
        leftFilters.add(roleCombo);

        filterPanel.add(leftFilters, BorderLayout.WEST);

        // Right: Add New Button
        JButton addNewBtn = new JButton("+ Add New");
        addNewBtn.setBackground(new Color(14, 40, 107));
        addNewBtn.setForeground(Color.WHITE);
        addNewBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        addNewBtn.setPreferredSize(new Dimension(150, 45));
        addNewBtn.addActionListener(e -> addNewUser());
        JPanel rightBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtn.setBackground(new Color(217, 217, 217));
        rightBtn.add(addNewBtn);
        filterPanel.add(rightBtn, BorderLayout.EAST);

        content.add(filterPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Table ===
        String[] columns = {"Name", "Email", "Phone Number", "Role", "Action"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Action
            }
        };

        table = new JTable(model);
        table.setRowHeight(55);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

        // Load initial real data
        updateTable(userController.getAllUsers());

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

        // Action column: View + Edit + Delete
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setToolTipText("View");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);
            viewBtn.addActionListener(e -> viewUser(row));

            JButton editBtn = new JButton("✎");
            editBtn.setToolTipText("Edit");
            editBtn.setForeground(new Color(39, 174, 96));
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);
            editBtn.addActionListener(e -> editUser(row));

            JButton deleteBtn = new JButton("🗑");
            deleteBtn.setToolTipText("Delete");
            deleteBtn.setForeground(new Color(231, 76, 60));
            deleteBtn.setBorderPainted(false);
            deleteBtn.setContentAreaFilled(false);
            deleteBtn.addActionListener(e -> deleteUser(row));

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

    // Other methods...
    // Load/update table with real users
    private void updateTable(List<User> userList) {
        model.setRowCount(0);
        for (User u : userList) {
            model.addRow(new Object[]{
                u.getName(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getRole(),
                "" // Action placeholder
            });
        }
    }

    // View user details
    private void viewUser(int row) {
        String email = (String) model.getValueAt(row, 1);
        User u = userController.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (u != null) {
            JOptionPane.showMessageDialog(this,
                    "Name: " + u.getName() + "\n"
                    + "Email: " + u.getEmail() + "\n"
                    + "Phone: " + u.getPhoneNumber() + "\n"
                    + "Role: " + u.getRole(),
                    "User Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Edit user (simple dialog)
    private void editUser(int row) {
        String email = (String) model.getValueAt(row, 1);
        User u = userController.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (u != null) {
            String newName = JOptionPane.showInputDialog(this, "New Name:", u.getName());
            String newEmail = JOptionPane.showInputDialog(this, "New Email:", u.getEmail());
            String newPhone = JOptionPane.showInputDialog(this, "New Phone:", u.getPhoneNumber());

            if (newName != null && newEmail != null && newPhone != null) {
                u.setName(newName);
                u.setEmail(newEmail);
                u.setPhoneNumber(newPhone);
                userController.updateUser(u); // Add this method to UserController
                updateTable(userController.getAllUsers());
            }
        }
    }

    // Delete user
    private void deleteUser(int row) {
        String email = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete user " + email + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userController.deleteUser(email); // Add this method to UserController
            updateTable(userController.getAllUsers());
        }
    }

    // Add new user (simple dialog)
    private void addNewUser() {
        String name = JOptionPane.showInputDialog(this, "Name:");
        String email = JOptionPane.showInputDialog(this, "Email:");
        String phone = JOptionPane.showInputDialog(this, "Phone:");
        String role = (String) JOptionPane.showInputDialog(this, "Role:", "Role", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Admin", "Cashier"}, "Cashier");
        String password = JOptionPane.showInputDialog(this, "Password:");

        if (name != null && email != null && phone != null && role != null && password != null) {
            User newUser = new User("U" + (userController.getAllUsers().size() + 1), name, email, phone, password, role);
            userController.addUser(newUser);
            updateTable(userController.getAllUsers());
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
}
