/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import View.WelcomeView;
import View.components.NavbarPanel;
import View.components.SidebarPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;

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

        //Sidebar 
        sidebarPanel = new SidebarPanel(parentFrame);   // ← pass WelcomeView, not old frame
        add(sidebarPanel, BorderLayout.WEST);

        //cENTER + TOP
        JPanel rightArea = new JPanel(new BorderLayout());

        // Navbar
        navbarPanel = new NavbarPanel();
        rightArea.add(navbarPanel, BorderLayout.NORTH);

        // Main content with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        rightArea.add(contentPanel, BorderLayout.CENTER);

        add(rightArea, BorderLayout.CENTER);

        contentPanel.add(new AdminDashboardPanel().getContentPanel(), "Dashboard");
        contentPanel.add(new AdminProductPanel().getContentPanel(), "Products");
        contentPanel.add(new AdminTransactionPanel().getContentPanel(), "Transactions");
        contentPanel.add(new AdminCustomerPanel().getContentPanel(), "Customer");
        contentPanel.add(new AdminUserPanel().getContentPanel(), "User");

        //Start with Dashboard visible 
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

    //method to force refresh: this method helps to referesh the panel when any changes is makes  
    public void refresh() {
        revalidate();
        repaint();
    }
}
