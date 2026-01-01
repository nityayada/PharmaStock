/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import controller.ProductController;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import model.Product;
import java.time.LocalDate;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import java.util.List;

/**
 *
 * @author nityayadav
 */
public class AdminProductPanel extends JPanel {

    private JTable table;
    private ProductController productController;
    private DefaultTableModel model;
    private JLabel expiredLabel;
    private JLabel totalProductLabel;
    private JLabel lowStockLabel;
    private JLabel outOfStockLabel;

    public AdminProductPanel() {
        setLayout(new BorderLayout());
        productController = new ProductController();
    }

    public JPanel getContentPanel() {
        return createProductContent();
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
        expiredLabel = new JLabel(); // initialize the label
        //

        // Top 3 Cards
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 20, 20));
        cardsRow.setBackground(new Color(217, 217, 217));
        // cardsRow.add(createIconCard("Total Product",
        // String.valueOf(productController.getTotalProducts()), "product-icon.png", new
        // Color(142, 68, 173)));
        // cardsRow.add(createIconCard("Low Stock Product",
        // String.valueOf(productController.getLowStockProducts().size()),
        // "lowstock-icon.png", new Color(230, 126, 34)));
        // cardsRow.add(createIconCard("Out of Stock Product",
        // String.valueOf(productController.getOutOfStockProducts().size()),
        // "outofstock-icon.png", new Color(231, 76, 60)));
        // cardsRow.add(createIconCard(
        // "Expired Product",
        // String.valueOf(productController.getExpiredProducts().size()),
        // "expired-icon.png", // you can create a new icon image
        // new Color(192, 57, 43),
        // expiredLabel // pass the label reference
        // ));
        // Existing cards
        totalProductLabel = new JLabel();
        lowStockLabel = new JLabel();
        outOfStockLabel = new JLabel();
        expiredLabel = new JLabel();

        cardsRow.add(createIconCard("Total Product",
                String.valueOf(productController.getTotalProducts()),
                "product-icon.png", new Color(142, 68, 173), totalProductLabel));

        cardsRow.add(createIconCard("Low Stock Product",
                String.valueOf(productController.getLowStockProducts().size()),
                "lowstock-icon.png", new Color(230, 126, 34), lowStockLabel));

        cardsRow.add(createIconCard("Out of Stock Product",
                String.valueOf(productController.getOutOfStockProducts().size()),
                "outofstock-icon.png", new Color(231, 76, 60), outOfStockLabel));

        cardsRow.add(createIconCard("Expired Product",
                String.valueOf(productController.getExpiredProducts().size()),
                "expired-icon.png", new Color(192, 57, 43), expiredLabel));

        content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));
        // content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));

        // Search + Add New Button
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(new Color(217, 217, 217));
        JPanel leftFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftFilters.setBackground(new Color(217, 217, 217));
        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.addActionListener(e -> updateTable(productController.searchProducts(searchField.getText())));
        leftFilters.add(searchField);

        // Sort Combo
        String[] sortOptions = { "Sort By...", "Price", "Quantity", "Name" };
        JComboBox<String> sortCombo = new JComboBox<>(sortOptions);
        sortCombo.setPreferredSize(new Dimension(120, 40));
        sortCombo.addActionListener(e -> {
            String selected = (String) sortCombo.getSelectedItem();
            if (!"Sort By...".equals(selected)) {
                productController.sortProducts(selected);
                updateTable(productController.getAllProducts());
            }
        });
        leftFilters.add(sortCombo);

        filterPanel.add(leftFilters, BorderLayout.WEST);
        JButton addNewBtn = new JButton("+ Add New");
        addNewBtn.setBackground(new Color(14, 40, 107));
        addNewBtn.setForeground(Color.WHITE);
        addNewBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        addNewBtn.setPreferredSize(new Dimension(150, 40));
        addNewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addNewBtn.addActionListener(e -> showAddProductDialog());

        JButton undoBtn = new JButton("↩ Undo");
        undoBtn.setBackground(new Color(231, 76, 60)); // Red ish
        undoBtn.setForeground(Color.WHITE);
        undoBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        undoBtn.setPreferredSize(new Dimension(100, 40));
        undoBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        undoBtn.addActionListener(e -> {
            if (productController.undoDelete()) {
                updateTable(productController.getAllProducts());
                updateCards();
                JOptionPane.showMessageDialog(this, "Last deletion undone!");
            } else {
                JOptionPane.showMessageDialog(this, "Nothing to undo!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel rightBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtn.setBackground(new Color(217, 217, 217));
        rightBtn.add(undoBtn);
        rightBtn.add(addNewBtn);
        filterPanel.add(rightBtn, BorderLayout.EAST);
        content.add(filterPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));
        // Table
        String[] columns = { "Product ID", "Product Name", "Items", "Price (Rs)", "Status", "Action" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Action
            }
        };
        table = new JTable(model);
        table.getColumn("Action").setCellRenderer(new ActionRenderer());
        table.getColumn("Action").setCellEditor(new ActionEditor());

        table.setRowHeight(50);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));
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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        content.add(scrollPane);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    // Load/update table
    private void updateTable(List<Product> productList) {
        model.setRowCount(0);
        for (Product p : productList) {
            model.addRow(new Object[] {
                    p.getProductId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getPrice(),
                    p.getStatus(),
                    ""
            });
        }
    }

    // View product
    private void viewProduct(int row) {

        String id = model.getValueAt(row, 0).toString();

        Product p = productController.getAllProducts().stream()
                .filter(prod -> prod.getProductId().equals(id))
                .findFirst()
                .orElse(null);

        if (p == null) {
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text info
        JTextArea info = new JTextArea(
                "Product ID: " + p.getProductId() + "\n"
                        + "Name: " + p.getName() + "\n"
                        + "Quantity: " + p.getQuantity() + "\n"
                        + "Price: Rs. " + p.getPrice() + "\n"
                        + "Status: " + p.getStatus() + "\n"
                        + "Expiry Date: " + p.getExpiryDate());
        info.setEditable(false);
        info.setBackground(null);

        panel.add(info, BorderLayout.CENTER);

        // Image (optional)
        if (p.getImagePath() != null) {
            ImageIcon icon = loadImage(p.getImagePath());
            if (icon != null) {
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                panel.add(new JLabel(new ImageIcon(img)), BorderLayout.EAST);
            }
        }

        JOptionPane.showMessageDialog(
                this,
                panel,
                "Product Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editProduct(int row) {
        String productId = model.getValueAt(row, 0).toString();

        Product p = productController.getAllProducts().stream()
                .filter(prod -> prod.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (p == null) {
            return;
        }

        // Input fields
        JTextField nameField = new JTextField(p.getName());
        JTextField qtyField = new JTextField(String.valueOf(p.getQuantity()));
        JTextField priceField = new JTextField(String.valueOf(p.getPrice()));
        JTextField expiryField = new JTextField(p.getExpiryDate().toString());

        JLabel imageLabel = new JLabel(p.getImagePath() == null ? "No image selected" : p.getImagePath());
        JButton browseBtn = new JButton("Browse");
        final String[] imagePath = { p.getImagePath() };

        browseBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                imagePath[0] = fc.getSelectedFile().getAbsolutePath();
                imageLabel.setText(imagePath[0]);
            }
        });

        // Form layout
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Product ID:"));
        panel.add(new JLabel(productId));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        panel.add(expiryField);
        panel.add(new JLabel("Product Image:"));
        panel.add(browseBtn);
        panel.add(new JLabel(""));
        panel.add(imageLabel);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Parse and validate expiry date
                LocalDate expiry = LocalDate.parse(expiryField.getText().trim());
                if (expiry.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Expiry must be a future date", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate name
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int qty = Integer.parseInt(qtyField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                // Update product through controller
                productController.updateProduct(productId, name, qty, price, expiry, imagePath[0]);

                updateTable(productController.getAllProducts());
                updateCards();

                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Delete product
    private void deleteProduct(int row) {
        String id = (String) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + id + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productController.deleteProduct(id);
            updateTable(productController.getAllProducts());
            updateCards();
        }
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Add New Product", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(245, 245, 245));

        // Title
        JLabel titleLabel = new JLabel("Add New Product", SwingConstants.CENTER);
        titleLabel.setFont(new Font("InaiMathi", Font.BOLD, 24));
        titleLabel.setForeground(new Color(14, 40, 107));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        dialog.add(titleLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(new Color(245, 245, 245));

        // ID
        formPanel.add(new JLabel("Product ID:"));
        JTextField idField = new JTextField();
        formPanel.add(idField);

        // Name
        formPanel.add(new JLabel("Product Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        // Quantity
        formPanel.add(new JLabel("Quantity:"));
        JTextField qtyField = new JTextField();
        formPanel.add(qtyField);

        // Price
        formPanel.add(new JLabel("Price (Rs):"));
        JTextField priceField = new JTextField();
        formPanel.add(priceField);

        // Expiry Date (Mandatory)
        formPanel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        JTextField expiryField = new JTextField();
        formPanel.add(expiryField);

        // Image (Optional)
        formPanel.add(new JLabel("Product Image:"));
        JButton browseBtn = new JButton("Browse");
        formPanel.add(browseBtn);

        final String[] imagePath = { null };
        browseBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                imagePath[0] = fc.getSelectedFile().getAbsolutePath();
            }
        });

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelBtn);

        JButton saveBtn = new JButton("Save Product");
        saveBtn.setBackground(new Color(14, 40, 107));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                // Expiry validation (MANDATORY)
                LocalDate expiry = LocalDate.parse(expiryField.getText().trim());
                if (expiry.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(dialog, "Expiry date must be a future date", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate required fields
                if (id.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Product ID and Name cannot be empty", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Duplicate ID check
                if (productIdExists(id)) {
                    JOptionPane.showMessageDialog(dialog, "Product ID already exists!", "Duplicate Product",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create product object with expiryDate and optional imagePath
                Product product = new Product(id, name, qty, price, expiry, imagePath[0]);

                // Add product via controller
                productController.addProduct(product, expiry, imagePath[0]);

                // Update table and cards
                updateTable(productController.getAllProducts());
                updateCards();
                JOptionPane.showMessageDialog(dialog, "Product added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Quantity and Price must be valid numbers", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid data for all fields", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(saveBtn);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Update top cards
    // private void updateCards() {
    // // Re-create or update card values here
    // // For simplicity, show message (you can improve by storing card labels)
    //// JOptionPane.showMessageDialog(this, "Cards updated with new data!");
    // }
    private void updateCards() {
        totalProductLabel.setText(String.valueOf(productController.getTotalProducts()));
        lowStockLabel.setText(String.valueOf(productController.getLowStockProducts().size()));
        outOfStockLabel.setText(String.valueOf(productController.getOutOfStockProducts().size()));
        expiredLabel.setText(String.valueOf(productController.getExpiredProducts().size()));
    }

    // private JPanel createIconCard(String title, String value, String iconPath,
    // Color color) {
    // JPanel card = new JPanel(new BorderLayout());
    // card.setBackground(Color.WHITE);
    // card.setBorder(BorderFactory.createCompoundBorder(
    // BorderFactory.createLineBorder(new Color(200, 200, 200)),
    // BorderFactory.createEmptyBorder(20, 20, 20, 20)
    // ));
    // JLabel iconLabel = new JLabel();
    // if (!java.beans.Beans.isDesignTime()) {
    // java.net.URL url = getClass().getClassLoader().getResource("images/" +
    // iconPath);
    // if (url != null) {
    // iconLabel.setIcon(new ImageIcon(url));
    // }
    // }
    // card.add(iconLabel, BorderLayout.WEST);
    // JPanel textPanel = new JPanel(new GridLayout(2, 1));
    // textPanel.setBackground(Color.WHITE);
    // JLabel titleLabel = new JLabel(title);
    // titleLabel.setForeground(Color.GRAY);
    // textPanel.add(titleLabel);
    // JLabel valueLabel = new JLabel(value);
    // valueLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
    // valueLabel.setForeground(color);
    // textPanel.add(valueLabel);
    // card.add(textPanel, BorderLayout.CENTER);
    // return card;
    // }
    private JPanel createIconCard(String title, String value, String iconPath, Color color, JLabel valueLabelRef) {
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

        valueLabelRef.setText(value);
        valueLabelRef.setFont(new Font("InaiMathi", Font.BOLD, 32));
        valueLabelRef.setForeground(color);
        textPanel.add(valueLabelRef);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private boolean productIdExists(String id) {
        return productController.getAllProducts()
                .stream()
                .anyMatch(p -> p.getProductId().equalsIgnoreCase(id));
    }

    private class ActionRenderer extends JPanel
            implements javax.swing.table.TableCellRenderer {

        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
            setBackground(Color.WHITE);

            add(createButton("👁"));
            add(createButton("✎"));
            add(createButton("🗑"));
        }

        private JButton createButton(String text) {
            JButton btn = new JButton(text);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            return btn;
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ActionEditor extends AbstractCellEditor
            implements javax.swing.table.TableCellEditor {

        private JPanel panel;
        private int row;

        public ActionEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            panel.setBackground(Color.WHITE);

            JButton viewBtn = createButton("👁");
            JButton editBtn = createButton("✎");
            JButton deleteBtn = createButton("🗑");

            viewBtn.addActionListener(e -> {
                fireEditingStopped();
                viewProduct(row);
            });

            editBtn.addActionListener(e -> {
                fireEditingStopped();
                editProduct(row);
            });

            deleteBtn.addActionListener(e -> {
                fireEditingStopped();
                deleteProduct(row);
            });

            panel.add(viewBtn);
            panel.add(editBtn);
            panel.add(deleteBtn);
        }

        private JButton createButton(String text) {
            JButton btn = new JButton(text);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            return btn;
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected,
                int row, int column) {
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

        // 1. Try absolute/local file system path first (for user-added images)
        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(path);
        }

        // 2. Try as resource (for sample data like "/images/paracetamol.png")
        // Note: getResource requires path starting with / if it's from root of
        // classpath
        java.net.URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        }

        // 3. Fallback: Try with added / if missing
        if (!path.startsWith("/")) {
            imgUrl = getClass().getResource("/" + path);
        }

        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        }

        return null;
    }

}
