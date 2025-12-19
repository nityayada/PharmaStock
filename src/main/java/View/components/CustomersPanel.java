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
public class CustomersPanel extends JPanel {

    public CustomersPanel() {
        setLayout(new BorderLayout());
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
        setBackground(java.awt.Color.WHITE);

        JLabel title = new JLabel("Customers Go to Customer Page");
        add(title, BorderLayout.NORTH);

        JPanel inner = new JPanel(new GridLayout(2, 1));
        inner.add(new JLabel("845 Total no. of Customers"));
        inner.add(new JLabel("Paracetamol Frequently bought item"));
        add(inner, BorderLayout.CENTER);
    }
}
