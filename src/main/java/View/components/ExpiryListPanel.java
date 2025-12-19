/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nityayadav
 */

public class ExpiryListPanel extends JPanel {

    public ExpiryListPanel() {
        setLayout(new BorderLayout());
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
        setBackground(java.awt.Color.WHITE);

        JLabel title = new JLabel("Near Expiry Medicines");
        add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel(new GridLayout(2, 1));
        listPanel.add(new JLabel("1. Vitamin-C Expires: 15 Dec 2025 (28 days)"));
        listPanel.add(new JLabel("2. Cough Syrup Expires: 22 Dec 2025 (35 days)"));
        add(listPanel, BorderLayout.CENTER);
    }
}
