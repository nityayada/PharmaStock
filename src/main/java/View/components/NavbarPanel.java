/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author nityayadav
 */
public class NavbarPanel extends JPanel {

    public NavbarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250)); // full white like in image
        setPreferredSize(new Dimension(0, 70)); // Fixed height
        setBorder(new EmptyBorder(10, 30, 10, 30)); // Padding left/right

        // Left: Welcome Label (VERTICAL)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(null);

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setFont(new Font("InaiMathi", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.GRAY);

        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("InaiMathi", Font.BOLD, 22));
        adminLabel.setForeground(Color.BLACK);

//      Align labels to the left
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        adminLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

//       Optional spacing between labels
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        leftPanel.add(adminLabel);

        add(leftPanel, BorderLayout.WEST);

    }

}
