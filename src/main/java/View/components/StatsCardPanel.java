/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nityayadav
 */
public class StatsCardPanel extends JPanel {

    public StatsCardPanel(String title, String value, String iconPath) {
        setLayout(new BorderLayout());
        setBorder(javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/images/" + iconPath)));
        add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new java.awt.GridLayout(2, 1));
        JLabel titleLabel = new JLabel(title);
        textPanel.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        textPanel.add(valueLabel);

        add(textPanel, BorderLayout.CENTER);
    }
}
