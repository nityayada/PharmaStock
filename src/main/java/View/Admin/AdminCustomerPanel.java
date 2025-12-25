package View.Admin;

import controller.CustomerController;
import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import java.util.List;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author nityayadav
 */
public class AdminCustomerPanel extends JPanel {

    private JTable table;
    private CustomerController customerController;
    private DefaultTableModel model;

    public AdminCustomerPanel() {
        setLayout(new BorderLayout());
        customerController = new CustomerController();
    }

    public JPanel getContentPanel() {
        return createCustomerContent();
    }

    private JPanel createCustomerContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));

        // Header
        JLabel headerLabel = new JLabel("Customer", SwingConstants.LEFT);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 32));
        headerLabel.setForeground(new Color(14, 40, 107));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Main vertical layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(217, 217, 217));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        // Top Card: Total Customers (real data)
        JPanel cardsRow = new JPanel(new GridLayout(1, 1, 20, 20));
        cardsRow.setBackground(new Color(217, 217, 217));
        cardsRow.add(createIconCard("Total Customers", String.valueOf(customerController.getTotalCustomers()), "customer-icon.png", new Color(41, 128, 185)));
        content.add(cardsRow);
        content.add(Box.createRigidArea(new Dimension(0, 40)));

        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        searchPanel.setBackground(new Color(217, 217, 217));

        JTextField searchField = new JTextField("Search anything");
        searchField.setPreferredSize(new Dimension(400, 45));
        searchField.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)
        ));
        searchField.addActionListener(e -> updateTable(customerController.searchCustomers(searchField.getText())));
        searchPanel.add(searchField);

        content.add(searchPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // Table
        String[] columns = {"Customer ID", "Name", "Phone Number", "Email", "Action"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // ✅ Action column only
            }
        };

        table = new JTable(model);
        table.setRowHeight(55);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("InaiMathi", Font.BOLD, 14));

        // Load real data
        updateTable(customerController.getAllCustomers());

        // Action column: View only
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            actionPanel.setBackground(Color.WHITE);

            JButton viewBtn = new JButton("👁");
            viewBtn.setForeground(new Color(41, 128, 185));
            viewBtn.setToolTipText("View Customer");
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);

            actionPanel.add(viewBtn);
            return actionPanel;
        });
        table.getColumn("Action").setCellEditor(new ActionButtonEditor());

//        // Make View button clickable
//        table.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent e) {
//                int row = table.rowAtPoint(e.getPoint());
//                int column = table.columnAtPoint(e.getPoint());
//
//                if (row >= 0 && column == table.getColumn("Action").getModelIndex()) {
//                    Point clickPoint = e.getPoint();
//                    Rectangle cellRect = table.getCellRect(row, column, true);
//                    Point relative = new Point(clickPoint.x - cellRect.x, clickPoint.y - cellRect.y);
//
//                    JPanel actionPanel = (JPanel) table.getCellRenderer(row, column).getTableCellRendererComponent(table, null, false, false, row, column);
//                    Component[] buttons = actionPanel.getComponents();
//
//                    for (Component comp : buttons) {
//                        if (comp instanceof JButton && comp.getBounds().contains(relative)) {
//                            viewCustomer(row);
//                            break;
//                        }
//                    }
//                }
//            }
//        });
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        content.add(scrollPane);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    // Load/update table with real customers
    private void updateTable(List<Customer> customerList) {
        model.setRowCount(0);
        for (Customer c : customerList) {
            model.addRow(new Object[]{
                c.getCustomerId(),
                c.getName(),
                c.getPhoneNumber(),
                c.getEmail(),
                "" // Action placeholder
            });
        }
    }

    // View customer details
    private void viewCustomer(int viewRow) {

        int modelRow = table.convertRowIndexToModel(viewRow);
        String id = model.getValueAt(modelRow, 0).toString();

        Customer c = customerController.getAllCustomers().stream()
                .filter(cust -> cust.getCustomerId().equals(id))
                .findFirst()
                .orElse(null);

        if (c == null) {
            JOptionPane.showMessageDialog(this,
                    "Customer not found",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Customer ID: " + c.getCustomerId() + "\n"
                + "Name: " + c.getName() + "\n"
                + "Phone: " + c.getPhoneNumber() + "\n"
                + "Email: " + c.getEmail(),
                "Customer Details",
                JOptionPane.INFORMATION_MESSAGE
        );
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

    class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {

        private final JPanel panel;
        private final JButton viewButton;
        private int viewRow;

        public ActionButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            panel.setBackground(Color.WHITE);

            viewButton = new JButton("👁");
            viewButton.setBorderPainted(false);
            viewButton.setContentAreaFilled(false);
            viewButton.setForeground(new Color(41, 128, 185));

            viewButton.addActionListener(e -> {
                viewCustomer(viewRow);
                fireEditingStopped();
            });

            panel.add(viewButton);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            this.viewRow = row; // VIEW index
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

}
