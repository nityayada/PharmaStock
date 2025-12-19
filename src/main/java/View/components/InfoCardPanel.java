/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nityayadav
 */
public class InfoCardPanel extends JPanel {

    public InfoCardPanel(String title, String value, String subtitle) {
        setLayout(new BorderLayout());
        setBorder(javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        add(titleLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        add(valueLabel, BorderLayout.CENTER);

        JLabel subLabel = new JLabel(subtitle);
        add(subLabel, BorderLayout.SOUTH);
    }
}
