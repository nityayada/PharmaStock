/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Cashier;

import controller.ProductController;
import controller.TransactionController;
import model.Product;
import model.Transaction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class CashOrderPanel extends JPanel {

    private ProductController productController;
    private TransactionController transactionController;
    private controller.CustomerController customerController;

    // Cart Data: ProductID -> Quantity
    private Map<String, Integer> cartItems = new HashMap<>();

    private DefaultTableModel cartModel;
    private JLabel subTotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;
    private JPanel productGrid;

    public CashOrderPanel() {
        // Initialize controllers (assuming they are stateless/singleton via internal
        // static lists)
        productController = new ProductController();
        transactionController = new TransactionController();
        customerController = new controller.CustomerController();

        setLayout(new BorderLayout());
        add(createCashOrderContent(), BorderLayout.CENTER);

        // Load initial products
        refreshProductGrid();
    }

    public JPanel getContentPanel() {
        return this;
    }

    // Refresh grid (useful if products change)
    public void refreshProductGrid() {
        productGrid.removeAll();
        for (Product p : productController.getAllProducts()) {
            if ("Available".equals(p.getStatus()) || "Low Stock".equals(p.getStatus())) {
                productGrid.add(createProductCard(p));
            }
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private JPanel createCashOrderContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

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
        // Add listener for search
        searchField.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            java.util.List<Product> results = productController.searchProducts(keyword);
            refreshProductGrid(results);
        });

        // Add KeyListener for real-time search (optional but nice)
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String keyword = searchField.getText().trim();
                java.util.List<Product> results = productController.searchProducts(keyword);
                refreshProductGrid(results);
            }
        });

        searchBarPanel.add(searchField);

        leftPanel.add(searchBarPanel, BorderLayout.NORTH);

        // Product Grid
        productGrid = new JPanel(new GridLayout(0, 3, 20, 20));
        productGrid.setBackground(new Color(217, 217, 217));
        productGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane productScroll = new JScrollPane(productGrid);
        productScroll.setBorder(null);
        leftPanel.add(productScroll, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        // ... (rest of layout)

        // ... (Right Panel handling) => No changes needed

        // Return splitPane for layout not the whole method yet

        // Re-assembling the SplitPane part to ensure context validity
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel cartTitle = new JLabel("Order Summary");
        cartTitle.setFont(new Font("InaiMathi", Font.BOLD, 22));
        cartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        cartPanel.add(cartTitle, BorderLayout.NORTH);

        // Cart Table
        String[] cartColumns = { "Product", "Qty", "Price", "Subtotal" };
        cartModel = new DefaultTableModel(new Object[][] {}, cartColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable cartTable = new JTable(cartModel);
        cartTable.setRowHeight(35);
        cartTable.getTableHeader().setBackground(new Color(220, 220, 220));

        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBorder(null);
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Summary Totals
        JPanel totalsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        totalsPanel.setBackground(Color.WHITE);
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        totalsPanel.add(new JLabel("Sub Total:"));
        subTotalLabel = new JLabel("Rs. 0.00", SwingConstants.RIGHT);
        totalsPanel.add(subTotalLabel);

        totalsPanel.add(new JLabel("Tax (10%):"));
        taxLabel = new JLabel("Rs. 0.00", SwingConstants.RIGHT);
        totalsPanel.add(taxLabel);

        totalsPanel.add(new JLabel("Discount:"));
        totalsPanel.add(new JLabel("Rs. 0.00", SwingConstants.RIGHT));

        totalsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("Rs. 0.00", SwingConstants.RIGHT);
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
        clearBtn.addActionListener(e -> clearCart());
        bottomButtons.add(clearBtn);

        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setBackground(new Color(14, 40, 107));
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setFont(new Font("InaiMathi", Font.BOLD, 16));
        placeOrderBtn.setPreferredSize(new Dimension(200, 50));
        placeOrderBtn.addActionListener(e -> placeOrder());
        bottomButtons.add(placeOrderBtn);

        panel.add(bottomButtons, BorderLayout.SOUTH);

        return panel;
    }

    // Overload refreshProductGrid to accept list
    public void refreshProductGrid(java.util.List<Product> products) {
        productGrid.removeAll();
        for (Product p : products) {
            if ("Available".equals(p.getStatus()) || "Low Stock".equals(p.getStatus())) {
                productGrid.add(createProductCard(p));
            }
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.setPreferredSize(new Dimension(150, 200)); // Increased height slightly

        // Add Click Listener to Card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addToCart(product);
            }
        });

        JLabel icon = new JLabel();
        icon.setPreferredSize(new Dimension(100, 100)); // Fixed size for icon area

        // Load image or placeholder with scaling
        if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
            try {
                // Try absolute path first
                ImageIcon imgIcon = null;
                java.io.File f = new java.io.File(product.getImagePath());
                if (f.exists()) {
                    imgIcon = new ImageIcon(product.getImagePath());
                } else {
                    // Try resource
                    java.net.URL url = getClass().getResource(product.getImagePath());
                    if (url != null) {
                        imgIcon = new ImageIcon(url);
                    }
                }

                if (imgIcon != null) {
                    // Scale image smoothly
                    java.awt.Image img = imgIcon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    icon.setIcon(new ImageIcon(img));
                } else {
                    icon.setText("[No Image]");
                }
            } catch (Exception ex) {
                icon.setText("[Err]");
            }
        } else {
            icon.setText("[No Image]");
        }

        icon.setHorizontalAlignment(SwingConstants.CENTER);
        // Wrap icon in a panel to center inside the specific dimension
        JPanel iconPanel = new JPanel(new java.awt.GridBagLayout());
        iconPanel.setBackground(Color.WHITE);
        iconPanel.add(icon);
        card.add(iconPanel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel("<html><center>" + product.getName() + "</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("InaiMathi", Font.BOLD, 14));
        // Limit height of name
        nameLabel.setPreferredSize(new Dimension(140, 40));
        card.add(nameLabel, BorderLayout.NORTH);

        JLabel priceLabel = new JLabel("Rs. " + product.getPrice(), SwingConstants.CENTER);
        priceLabel.setFont(new Font("InaiMathi", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(52, 152, 219));

        JLabel stockLabel = new JLabel("Qty: " + product.getQuantity(), SwingConstants.CENTER);
        stockLabel.setFont(new Font("InaiMathi", Font.PLAIN, 10));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        bottomPanel.add(priceLabel, BorderLayout.CENTER);
        bottomPanel.add(stockLabel, BorderLayout.SOUTH);

        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void addToCart(Product product) {
        String pid = product.getProductId();
        int currentQtyInCart = cartItems.getOrDefault(pid, 0);

        if (currentQtyInCart + 1 > product.getQuantity()) {
            JOptionPane.showMessageDialog(this, "Insufficient stock for " + product.getName(), "Stock Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        cartItems.put(pid, currentQtyInCart + 1);
        updateCartTable();
    }

    private void updateCartTable() {
        cartModel.setRowCount(0);
        double subtotal = 0;

        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Product p = productController.getProduct(entry.getKey());
            if (p != null) {
                int qty = entry.getValue();
                double lineTotal = p.getPrice() * qty;
                subtotal += lineTotal;

                cartModel.addRow(new Object[] {
                        p.getName(),
                        qty,
                        p.getPrice(),
                        lineTotal
                });
            }
        }

        double tax = subtotal * 0.10;
        double total = subtotal + tax;

        subTotalLabel.setText(String.format("Rs. %.2f", subtotal));
        taxLabel.setText(String.format("Rs. %.2f", tax));
        totalLabel.setText(String.format("Rs. %.2f", total));
    }

    private void clearCart() {
        cartItems.clear();
        updateCartTable();
    }

    private void placeOrder() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 0. Get Customer Details
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] message = {
                "Customer Name (Required):", nameField,
                "Phone Number:", phoneField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Enter Customer Details",
                JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return; // User cancelled
        }

        String custName = nameField.getText().trim();
        String custPhone = phoneField.getText().trim();
        String custEmail = emailField.getText().trim();

        // 1. Validate Name
        if (custName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer Name is required!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validate Phone (10 digits, numeric)
        if (!custPhone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits and numeric!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validate Email (Optional, but if present must be @gmail.com)
        if (!custEmail.isEmpty()) {
            if (!custEmail.toLowerCase().endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Email must be a valid @gmail.com address!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            // 4. Generate IDs (Format: TRX### and C###)
            int randomTrx = (int) (100 + Math.random() * 900); // 100 to 999
            int randomCust = (int) (100 + Math.random() * 900);

            String txId = "TRX" + randomTrx;
            String custId = "C" + randomCust;

            // 2. Create & Save Customer
            model.Customer customer = new model.Customer(custId, custName, custPhone, custEmail);
            customerController.addCustomer(customer);

            // 3. Calculate Total
            double totalAmount = 0;
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                Product p = productController.getProduct(entry.getKey());
                totalAmount += p.getPrice() * entry.getValue();
            }
            double finalAmount = totalAmount * 1.10; // +10% tax

            // 4. Create Transaction
            Transaction trx = new Transaction(txId, custId, LocalDate.now(), LocalTime.now(), finalAmount);

            // 5. Reduce Stock & Add to Transaction
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                productController.sellProduct(entry.getKey(), entry.getValue());
                trx.addProduct(entry.getKey());
            }

            // 6. Save Transaction
            transactionController.addTransaction(trx);

            JOptionPane.showMessageDialog(this, "Order placed successfully!\nTransaction ID: " + txId);

            clearCart();
            refreshProductGrid(); // Update stock display immediately

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error placing order: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
