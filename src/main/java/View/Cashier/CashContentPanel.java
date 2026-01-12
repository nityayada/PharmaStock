/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Cashier;

import View.WelcomeView;
import View.components.CashNavbarPanel;
import View.components.CashSidebarPanel;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author nityayadav
 */
public class CashContentPanel extends JPanel {

    private final WelcomeView parentFrame;      // to call switchPage() and showLoginScreen()
    private final User loggedInUser;            // passed from login → needed for Account panel

    private CashSidebarPanel sidebarPanel;
    private CashNavbarPanel navbarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public CashContentPanel(WelcomeView parent, User user) {
        this.parentFrame = parent;
        this.loggedInUser = user;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));    // consistent light background

        // ─── LEFT: Cashier Sidebar ───────────────────────────────
        sidebarPanel = new CashSidebarPanel(parentFrame);   // ← pass WelcomeView
        add(sidebarPanel, BorderLayout.WEST);

        // ─── RIGHT AREA: Navbar on top + switchable content ──────
        JPanel rightArea = new JPanel(new BorderLayout());

        // Top: Navbar (fixed)
        navbarPanel = new CashNavbarPanel();
        rightArea.add(navbarPanel, BorderLayout.NORTH);

        // Main content area using CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        rightArea.add(contentPanel, BorderLayout.CENTER);

        add(rightArea, BorderLayout.CENTER);

        // ─── Add cashier-specific pages ──────────────────────────
        // Make sure these classes exist and .getContentPanel() returns a JPanel
        contentPanel.add(new CashDashboardPanel().getContentPanel(), "Dashboard");
        contentPanel.add(new CashProductPanel().getContentPanel(), "Product");
        contentPanel.add(new CashOrderPanel().getContentPanel(), "Order");

        // Important: Account panel needs the logged-in user
        contentPanel.add(
                new CashAccountPanel(loggedInUser).getContentPanel(),
                "Account"
        );

        // Start on Dashboard + highlight correct sidebar button
        cardLayout.show(contentPanel, "Dashboard");
        sidebarPanel.setActiveButton("Dashboard");
    }

    /**
     * Called from WelcomeView.switchPage() or indirectly via sidebar
     */
    public void switchPage(String pageName) {
        cardLayout.show(contentPanel, pageName);
        sidebarPanel.setActiveButton(pageName);
    }

    // Optional: refresh layout after big changes
    public void refresh() {
        revalidate();
        repaint();
    }
}
