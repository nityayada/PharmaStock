/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import controller.UserController;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import model.User;
import java.util.List;
import javax.swing.table.TableCellEditor;

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

        JComboBox<String> roleCombo = new JComboBox<>(new String[] { "Roles", "All", "Admin", "Cashier" });
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
        String[] columns = { "Name", "Email", "Phone Number", "Role", "Action" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Action
            }
        };

        table = new JTable(model);
        table.getColumn("Action").setCellEditor(new ActionEditor());

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
            model.addRow(new Object[] {
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
            JPanel panel = new JPanel(new BorderLayout(20, 20));
            // Image
            JLabel imageLabel = new JLabel();
            imageLabel.setPreferredSize(new Dimension(120, 120));
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (u.getImagePath() != null && !u.getImagePath().isEmpty()) {
                ImageIcon icon = loadImage(u.getImagePath());
                if (icon != null) {
                    Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                } else {
                    imageLabel.setText("No Image");
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                }
            } else {
                imageLabel.setText("No Image");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
            panel.add(imageLabel, BorderLayout.WEST);

            // Details
            String details = "<html><body style='width: 200px'>"
                    + "<h2>" + u.getName() + "</h2>"
                    + "<p><b>Email:</b> " + u.getEmail() + "</p>"
                    + "<p><b>Phone:</b> " + u.getPhoneNumber() + "</p>"
                    + "<p><b>Role:</b> " + u.getRole() + "</p>"
                    + "</body></html>";

            JLabel detailsLabel = new JLabel(details);
            panel.add(detailsLabel, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this, panel, "User Details", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void editUser(int row) {
        String email = model.getValueAt(row, 1).toString();
        User u = userController.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (u == null) {
            return;
        }

        JTextField nameField = new JTextField(u.getName());
        JTextField emailField = new JTextField(u.getEmail());
        JTextField phoneField = new JTextField(u.getPhoneNumber());
        JComboBox<String> roleBox = new JComboBox<>(new String[] { "Admin", "Cashier" });
        roleBox.setSelectedItem(u.getRole());

        // Image Upload
        JLabel imagePathLabel = new JLabel(u.getImagePath() == null ? "No file selected" : u.getImagePath());
        JButton browseBtn = new JButton("Browse");
        final String[] selectedImagePath = { u.getImagePath() };

        browseBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
                    "Images", "jpg", "png", "jpeg");
            fc.setFileFilter(filter);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedImagePath[0] = fc.getSelectedFile().getAbsolutePath();
                imagePathLabel.setText(selectedImagePath[0]);
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        panel.add(new JLabel("Image (Optional):"));
        JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imgPanel.add(browseBtn);
        panel.add(imgPanel);

        panel.add(new JLabel("")); // Spacer
        panel.add(imagePathLabel);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Edit User",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            String name = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || newEmail.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newEmail.toLowerCase().endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Email must be a valid @gmail.com address!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            u.setName(name);
            u.setEmail(newEmail);
            u.setPhoneNumber(phone);
            u.setRole(roleBox.getSelectedItem().toString());
            u.setImagePath(selectedImagePath[0]);

            userController.updateUser(u);
            updateTable(userController.getAllUsers());
        }
    }

    // Delete user
    private void deleteUser(int row) {
        String email = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete user " + email + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userController.deleteUser(email); // Add this method to UserController
            updateTable(userController.getAllUsers());
        }
    }

    // Add new user (simple dialog)
    private void addNewUser() {

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[] { "Admin", "Cashier" });

        // Image Upload
        JLabel imagePathLabel = new JLabel("No file selected");
        JButton browseBtn = new JButton("Browse");
        final String[] selectedImagePath = { null };

        browseBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
                    "Images", "jpg", "png", "jpeg");
            fc.setFileFilter(filter);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedImagePath[0] = fc.getSelectedFile().getAbsolutePath();
                imagePathLabel.setText(selectedImagePath[0]);
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        panel.add(new JLabel("Image (Optional):"));
        JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imgPanel.add(browseBtn);
        panel.add(imgPanel);

        panel.add(new JLabel("")); // Spacer
        panel.add(imagePathLabel);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add New User",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = roleBox.getSelectedItem().toString();

            // Validation
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Phone validation
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Email validation
            if (!email.toLowerCase().endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Email must be a valid @gmail.com address!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(
                    "U" + (userController.getAllUsers().size() + 1),
                    name,
                    email,
                    phone,
                    password,
                    role,
                    selectedImagePath[0]);

            userController.addUser(newUser);
            updateTable(userController.getAllUsers());

            JOptionPane.showMessageDialog(
                    this,
                    "User added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JPanel createIconCard(String title, String value, String iconPath, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)));

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

    private class ActionEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel;
        private int row;

        public ActionEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            panel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            JButton editBtn = new JButton("✎");
            JButton deleteBtn = new JButton("🗑");

            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);

            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);

            deleteBtn.setBorderPainted(false);
            deleteBtn.setContentAreaFilled(false);

            viewBtn.addActionListener(e -> {
                fireEditingStopped();
                viewUser(row);
            });

            editBtn.addActionListener(e -> {
                fireEditingStopped();
                editUser(row);
            });

            deleteBtn.addActionListener(e -> {
                fireEditingStopped();
                deleteUser(row);
            });

            panel.add(viewBtn);
            panel.add(editBtn);
            panel.add(deleteBtn);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    private ImageIcon loadImage(String path) {
        if (path == null || path.trim().isEmpty()) {
            return null;
        }
        // 1. Try absolute/local file system path first
        java.io.File f = new java.io.File(path);
        if (f.exists()) {
            return new ImageIcon(path);
        }
        // 2. Try as resource
        java.net.URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        }
        return null;
    }
}
