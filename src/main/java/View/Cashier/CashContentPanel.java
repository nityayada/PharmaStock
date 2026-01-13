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
        setBackground(new Color(240, 242, 245));

        //Siderbarpanel
        sidebarPanel = new CashSidebarPanel(parentFrame);
        add(sidebarPanel, BorderLayout.WEST);

        JPanel rightArea = new JPanel(new BorderLayout());

        //Navbar
        navbarPanel = new CashNavbarPanel();
        rightArea.add(navbarPanel, BorderLayout.NORTH);

        // Main content area using CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        rightArea.add(contentPanel, BorderLayout.CENTER);

        add(rightArea, BorderLayout.CENTER);

        //Add cashier-specific pages 
        contentPanel.add(new CashDashboardPanel().getContentPanel(), "Dashboard");
        contentPanel.add(new CashProductPanel().getContentPanel(), "Product");
        contentPanel.add(new CashOrderPanel().getContentPanel(), "Order");

        //Account panel needs the logged-in user
        contentPanel.add(
                new CashAccountPanel(loggedInUser).getContentPanel(),
                "Account"
        );

        // Start on Dashboard
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

    // refresh layout after big changes
    public void refresh() {
        revalidate();
        repaint();
    }
}
