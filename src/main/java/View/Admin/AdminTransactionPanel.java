/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nityayadav
 */
public class AdminTransactionPanel extends JPanel {

    private controller.TransactionController transactionController;
    private JTable table;
    private DefaultTableModel model;

    public AdminTransactionPanel() {
        setLayout(new BorderLayout());
        transactionController = new controller.TransactionController();
    }

    public JPanel getContentPanel() {
        return createTransactionContent();
    }

    private JPanel createTransactionContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

        // Header
        JLabel headerLabel = new JLabel("Transaction", SwingConstants.LEFT);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Main vertical layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(217, 217, 217));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // === Top 2 Cards ===
        JPanel cardsRow = new JPanel(new GridLayout(1, 2, 30, 20));
        cardsRow.setBackground(new Color(217, 217, 217));
        cardsRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        cardsRow.add(createIconCard("Total Transaction",
                String.valueOf(transactionController.getTotalTransactions()),
                "transaction-icon.png", new Color(41, 128, 185)));

        cardsRow.add(createIconCard("Total Sale",
                String.format("Rs. %.2f", transactionController.getTotalSales()),
                "sale-icon.png", new Color(39, 174, 96)));

        content.add(cardsRow);

        // === Search Bar ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        searchPanel.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search Transaction ID");
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)));

        // Simple search logic
        searchField.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            List<model.Transaction> all = transactionController.getAllTransactions();
            if (!keyword.isEmpty() && !keyword.equals("search transaction id")) {
                List<model.Transaction> filtered = all.stream()
                        .filter(t -> t.getTransactionId().toLowerCase().contains(keyword))
                        .collect(java.util.stream.Collectors.toList());
                updateTable(filtered);
            } else {
                updateTable(all);
            }
        });

        searchPanel.add(searchField);
        content.add(searchPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Table ===
        String[] columns = { "Transaction ID", "Customer ID", "Date", "Time", "Amount (Rs)", "Action" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Action column
            }
        };

        table = new JTable(model);
        table.setRowHeight(55);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

        // Load Data
        updateTable(transactionController.getAllTransactions());

        // Action column: View button ONLY (Delete removed)
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setToolTipText("View Details");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);

            actionPanel.add(viewBtn);
            return actionPanel;
        });

        table.getColumn("Action").setCellEditor(new ActionEditor());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        content.add(scrollPane);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    private void updateTable(List<model.Transaction> transactions) {
        model.setRowCount(0);
        for (model.Transaction t : transactions) {
            model.addRow(new Object[] {
                    t.getTransactionId(),
                    t.getCustomerId(),
                    t.getDate().toString(),
                    t.getTime().withNano(0).toString(),
                    t.getAmount(),
                    ""
            });
        }
    }

    private void viewTransaction(int row) {
        String id = (String) model.getValueAt(row, 0);
        model.Transaction t = transactionController.getAllTransactions().stream()
                .filter(trx -> trx.getTransactionId().equals(id))
                .findFirst()
                .orElse(null);

        if (t == null)
            return;

        StringBuilder details = new StringBuilder();
        details.append("Transaction ID: ").append(t.getTransactionId()).append("\n");
        details.append("Customer ID: ").append(t.getCustomerId()).append("\n");
        details.append("Date: ").append(t.getDate()).append("\n");
        details.append("Time: ").append(t.getTime()).append("\n");
        details.append("Total Amount: Rs. ").append(t.getAmount()).append("\n");
        details.append("--------------------------------\n");
        details.append("Products Sold:\n"); // Assuming we track IDs
        // In real app, we would look up product names using ProductController here
        // For now, listing IDs as stored in Transaction model
        for (String pid : t.getProductIds()) {
            details.append("- Product ID: ").append(pid).append("\n");
        }

        JTextArea area = new JTextArea(details.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Transaction Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private class ActionEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private JPanel panel;
        private int row;

        public ActionEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            panel.setBackground(Color.WHITE);
            JButton viewBtn = new JButton("👁");
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);
            viewBtn.addActionListener(e -> {
                fireEditingStopped();
                viewTransaction(row);
            });
            panel.add(viewBtn);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    // Other methods...
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
}
