/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nityayadav
 */
public class AdminTransactionPanel extends JPanel {

    public AdminTransactionPanel() {
        setLayout(new BorderLayout());
    }

    public JPanel getContentPanel() {
        return createTransactionContent();
    }

    private JPanel createTransactionContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

//        // Your existing header, cards, search, table code here...
//        // Example
//        JTable table = new JTable(new DefaultTableModel(new String[]{"ID", "Date"}, 0));
//        panel.add(new JScrollPane(table), BorderLayout.CENTER);
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

        cardsRow.add(createIconCard("Total Transaction", "500", "transaction-icon.png", new Color(41, 128, 185)));
        cardsRow.add(createIconCard("Total Sale", "23,000", "sale-icon.png", new Color(39, 174, 96)));

        content.add(cardsRow);

        // === Search Bar ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        searchPanel.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("InaiMathi", Font.PLAIN, 16));
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
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

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

    // Other methods...
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
