/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import View.WelcomeView;
import View.components.NavbarPanel;
import View.components.SidebarPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author nityayadav
 */
public class AdminContentPanel extends JPanel {

    private final WelcomeView parentFrame;      // reference to call switchPage() & showLoginScreen()

    private SidebarPanel sidebarPanel;
    private NavbarPanel navbarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public AdminContentPanel(WelcomeView parent) {
        this.parentFrame = parent;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));    // same light background as before

        // ─── LEFT: Sidebar ───────────────────────────────────────
        sidebarPanel = new SidebarPanel(parentFrame);   // ← pass WelcomeView, not old frame
        add(sidebarPanel, BorderLayout.WEST);

        // ─── CENTER + TOP: Right area with navbar + content ──────
        JPanel rightArea = new JPanel(new BorderLayout());

        // Top: Navbar (fixed)
        navbarPanel = new NavbarPanel();
        rightArea.add(navbarPanel, BorderLayout.NORTH);

        // Main content with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        rightArea.add(contentPanel, BorderLayout.CENTER);

        add(rightArea, BorderLayout.CENTER);

        // ─── Add all admin pages ─────────────────────────────────
        // Make sure these panels exist and .getContentPanel() returns JPanel
        contentPanel.add(new AdminDashboardPanel().getContentPanel(), "Dashboard");
        contentPanel.add(new AdminProductPanel().getContentPanel(), "Products");
        contentPanel.add(new AdminTransactionPanel().getContentPanel(), "Transactions");
        contentPanel.add(new AdminCustomerPanel().getContentPanel(), "Customer");
        contentPanel.add(new AdminUserPanel().getContentPanel(), "User");

        // Start with Dashboard visible + button highlighted
        cardLayout.show(contentPanel, "Dashboard");
        sidebarPanel.setActiveButton("Dashboard");
    }

    /**
     * Called from WelcomeView.switchPage() or directly from sidebar
     */
    public void switchPage(String pageName) {
        cardLayout.show(contentPanel, pageName);
        sidebarPanel.setActiveButton(pageName);
    }

    // Optional: method to force refresh / revalidate after major changes
    public void refresh() {
        revalidate();
        repaint();
    }
}
