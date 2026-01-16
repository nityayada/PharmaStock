/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Cashier;

import controller.UserController;
import model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

/**
 *
 * @author nityayadav
 */
public class CashAccountPanel extends JPanel {

    private User currentUser;
    private UserController userController;

    //ui Components to need dynamic updates
    private JLabel welcomeLabel;
    private JLabel profilePic;
    private JLabel nameLabel;
    private JLabel userIdLabel;
    private JLabel roleLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel joinLabel;

    public CashAccountPanel(User user) {
        this.currentUser = user;
        this.userController = new UserController();

        setLayout(new BorderLayout());
        if (currentUser == null) {
            // Fallback for testing if no user passed
            add(new JLabel("No user logged in (Testing Mode)", SwingConstants.CENTER), BorderLayout.CENTER);
        } else {
            add(createAccountContent(), BorderLayout.CENTER);
        }
    }

    public JPanel getContentPanel() {
        return this;
    }

    private JPanel createAccountContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(217, 217, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(new Color(217, 217, 217));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(217, 217, 217));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JLabel headerLabel = new JLabel("My Account", SwingConstants.CENTER);
        headerLabel.setFont(new Font("InaiMathi", Font.BOLD, 36));
        headerLabel.setForeground(new Color(30, 41, 59));

        welcomeLabel = new JLabel("Welcome back, " + currentUser.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("InaiMathi", Font.PLAIN, 18));
        welcomeLabel.setForeground(new Color(100, 116, 139));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(welcomeLabel, BorderLayout.SOUTH);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        container.add(headerPanel, gbc);

        // Profile Card
        JPanel profileCard = createProfileCard();
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        container.add(profileCard, gbc);

        panel.add(container, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProfileCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(50, 80, 50, 80)));
        card.setMinimumSize(new Dimension(700, 700));

        // Profile header with avatar
        JPanel profileHeader = new JPanel(new BorderLayout(30, 0));
        profileHeader.setBackground(Color.WHITE);
        profileHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        // Avatar circle
        JPanel avatarPanel = new JPanel();
        avatarPanel.setPreferredSize(new Dimension(140, 140));
        avatarPanel.setBackground(new Color(236, 253, 245));
        avatarPanel.setBorder(BorderFactory.createLineBorder(new Color(167, 243, 208), 3));
        avatarPanel.setLayout(new GridBagLayout());

        profilePic = new JLabel();
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        updateProfileImage(); // Load image logic
        avatarPanel.add(profilePic);

        // Profile info
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        nameLabel = new JLabel(currentUser.getName());
        nameLabel.setFont(new Font("InaiMathi", Font.BOLD, 28));
        nameLabel.setForeground(new Color(30, 41, 59));
        gbc.gridy = 0;
        infoPanel.add(nameLabel, gbc);

        userIdLabel = new JLabel("ID: " + currentUser.getUserId());
        userIdLabel.setFont(new Font("InaiMathi", Font.PLAIN, 16));
        userIdLabel.setForeground(Color.GRAY);
        gbc.gridy++;
        infoPanel.add(userIdLabel, gbc);

        roleLabel = new JLabel(currentUser.getRole());
        roleLabel.setFont(new Font("InaiMathi", Font.PLAIN, 20));
        roleLabel.setForeground(new Color(16, 185, 129));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        gbc.gridy++;
        infoPanel.add(roleLabel, gbc);

        profileHeader.add(avatarPanel, BorderLayout.WEST);
        profileHeader.add(infoPanel, BorderLayout.CENTER);

        card.add(profileHeader, BorderLayout.NORTH);

        // Contact info
        JPanel contactPanel = new JPanel(new GridBagLayout());
        contactPanel.setBackground(Color.WHITE);
        contactPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        JPanel emailPanel = new JPanel(new BorderLayout(15, 0));
        emailPanel.setBackground(Color.WHITE);
        emailPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel emailIcon = new JLabel("📧");
        emailIcon.setFont(new Font("InaiMathi", Font.PLAIN, 22));
        emailLabel = new JLabel(currentUser.getEmail());
        emailLabel.setFont(new Font("InaiMathi", Font.PLAIN, 17));
        emailLabel.setForeground(new Color(100, 116, 139));
        emailPanel.add(emailIcon, BorderLayout.WEST);
        emailPanel.add(emailLabel, BorderLayout.CENTER);
        gbc.gridy = 0;
        contactPanel.add(emailPanel, gbc);

        // Phone - NO ICON, just Text
        JPanel phonePanel = new JPanel(new BorderLayout(15, 0));
        phonePanel.setBackground(Color.WHITE);
        phonePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel phoneTextLabel = new JLabel("Phone: ");
        phoneTextLabel.setFont(new Font("InaiMathi", Font.PLAIN, 17));
        phoneTextLabel.setForeground(new Color(100, 116, 139));

        phoneLabel = new JLabel(currentUser.getPhoneNumber());
        phoneLabel.setFont(new Font("InaiMathi", Font.PLAIN, 17));
        phoneLabel.setForeground(new Color(100, 116, 139));

        phonePanel.add(phoneTextLabel, BorderLayout.WEST);
        phonePanel.add(phoneLabel, BorderLayout.CENTER);
        gbc.gridy++;
        contactPanel.add(phonePanel, gbc);

        // Join Date
        JPanel joinPanel = new JPanel(new BorderLayout(15, 0));
        joinPanel.setBackground(Color.WHITE);
        joinPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel joinIcon = new JLabel("📅");
        joinIcon.setFont(new Font("InaiMathi", Font.PLAIN, 22));
        joinLabel = new JLabel("Joined: March 2024");
        joinLabel.setFont(new Font("InaiMathi", Font.PLAIN, 17));
        joinLabel.setForeground(new Color(100, 116, 139));
        joinPanel.add(joinIcon, BorderLayout.WEST);
        joinPanel.add(joinLabel, BorderLayout.CENTER);
        gbc.gridy++;
        contactPanel.add(joinPanel, gbc);

        card.add(contactPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 0, 50));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton editBtn = new JButton("Edit Profile");
        stylePrimaryButton(editBtn);
        editBtn.addActionListener(e -> showEditProfileDialog());
        gbc.gridy = 0;
        buttonPanel.add(editBtn, gbc);

        JButton logoutBtn = new JButton("Log Out");
        styleDangerButton(logoutBtn);
        logoutBtn.addActionListener(e -> performLogout());
        gbc.gridy++;
        buttonPanel.add(logoutBtn, gbc);

        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private void updateProfileImage() {
        if (currentUser.getImagePath() != null && !currentUser.getImagePath().isEmpty()) {
            ImageIcon icon = loadImage(currentUser.getImagePath());
            if (icon != null) {
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                profilePic.setIcon(new ImageIcon(img));
                profilePic.setText("");
            } else {
                profilePic.setIcon(null);
                profilePic.setText("👤");
                profilePic.setFont(new Font("InaiMathi", Font.PLAIN, 70));
                profilePic.setForeground(new Color(16, 185, 129));
            }
        } else {
            profilePic.setIcon(null);
            profilePic.setText("👤");
            profilePic.setFont(new Font("InaiMathi", Font.PLAIN, 70));
            profilePic.setForeground(new Color(16, 185, 129));
        }
    }

    private ImageIcon loadImage(String path) {
        if (path == null || path.trim().isEmpty()) {
            return null;
        }
        java.io.File f = new java.io.File(path);
        if (f.exists()) {
            return new ImageIcon(path);
        }
        java.net.URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        }
        return null;
    }

    private void showEditProfileDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Profile", true);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setSize(460, 470); // Increased size slightly
        dialog.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JTextField txtName = new JTextField(currentUser.getName(), 20);
        JTextField txtPhone = new JTextField(currentUser.getPhoneNumber(), 20);
        JTextField txtEmail = new JTextField(currentUser.getEmail(), 20);

        // Image Selection Logic
        JLabel imagePathLabel = new JLabel("No file selected");
        imagePathLabel.setPreferredSize(new Dimension(200, 20));
        String initialPath = currentUser.getImagePath();
        if (initialPath != null) {
            imagePathLabel.setText(new java.io.File(initialPath).getName());
            imagePathLabel.setToolTipText(initialPath);
        }

        JButton browseBtn = new JButton("Browse");
        final String[] selectedImagePath = {currentUser.getImagePath()};

        browseBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
                    "Images", "jpg", "png", "jpeg");
            fc.setFileFilter(filter);
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                selectedImagePath[0] = fc.getSelectedFile().getAbsolutePath();
                imagePathLabel.setText(fc.getSelectedFile().getName());
                imagePathLabel.setToolTipText(selectedImagePath[0]);
            }
        });

        // Add components to dialog
        dialog.add(new JLabel("Full Name:"), gbc);
        gbc.gridy++;
        dialog.add(txtName, gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Phone Number:"), gbc);
        gbc.gridy++;
        dialog.add(txtPhone, gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Email Address:"), gbc);
        gbc.gridy++;
        dialog.add(txtEmail, gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Profile Image:"), gbc);
        gbc.gridy++;
        JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imgPanel.setBackground(Color.WHITE);
        imgPanel.add(browseBtn);
        dialog.add(imgPanel, gbc);

        gbc.gridy++;
        dialog.add(imagePathLabel, gbc);

        // Save Button
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        JButton saveBtn = new JButton("Save Changes");
        stylePrimaryButton(saveBtn);
        saveBtn.setPreferredSize(new Dimension(195, 40));

        saveBtn.addActionListener(e -> {
            String newName = txtName.getText().trim();
            String newPhone = txtPhone.getText().trim();
            String newEmail = txtEmail.getText().trim();

            if (newName.isEmpty() || newPhone.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPhone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(dialog, "Phone number must be exactly 10 digits!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newEmail.toLowerCase().endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(dialog, "Email must be a valid @gmail.com address!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update User Object
            currentUser.setName(newName);
            currentUser.setPhoneNumber(newPhone);
            currentUser.setEmail(newEmail);
            currentUser.setImagePath(selectedImagePath[0]);

            // Persist Update
            userController.updateUser(currentUser);

            // Refresh UI
            refreshDisplay();

            JOptionPane.showMessageDialog(dialog, "Profile updated successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        dialog.add(btnPanel, gbc);
        btnPanel.add(saveBtn);

        dialog.setVisible(true);
    }

    private void refreshDisplay() {
        nameLabel.setText(currentUser.getName());
        welcomeLabel.setText("Welcome back, " + currentUser.getName() + "!");
        emailLabel.setText(currentUser.getEmail());
        phoneLabel.setText(currentUser.getPhoneNumber());
        roleLabel.setText(currentUser.getRole());
        updateProfileImage(); // Refresh image
        revalidate();
        repaint();
    }

    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.getWindowAncestor(this).dispose();
            new View.WelcomeView().setVisible(true);
        }
    }

    private void stylePrimaryButton(JButton button) {
        button.setFont(new Font("InaiMathi", Font.BOLD, 16));
        button.setBackground(new Color(14, 40, 107));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(59, 130, 246), 1),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setMinimumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(37, 99, 235));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(37, 99, 235), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(59, 130, 246));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(59, 130, 246), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)));
            }
        });
    }

    private void styleDangerButton(JButton button) {
        button.setFont(new Font("InaiMathi", Font.BOLD, 16));
        button.setBackground(new Color(239, 68, 68));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(239, 68, 68), 1),
                BorderFactory.createEmptyBorder(15, 40, 15, 40)));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setMinimumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 38, 38));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 38, 38), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(239, 68, 68));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(239, 68, 68), 1),
                        BorderFactory.createEmptyBorder(15, 40, 15, 40)));
            }
        });
    }
}
