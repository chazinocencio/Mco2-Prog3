package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private UserMenu usMenu;
    private Interface Intrfc;

    public MainFrame() {
        super("FINAL FANTASY");

        // 1) Create the CardLayout and a panel that uses it
        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);

        // 2) Create your “background” screen
        ImageIcon bg = new ImageIcon(
            "images/background.png"
        );

        JLabel background = new JLabel(bg);
        
        // …you can add buttons/menus on top of the background here…

        // 3) Create your user‐menu screen
        usMenu = new UserMenu();
        Intrfc = new Interface();
        
        // 4) Add each screen to the cardPanel with a name
        cardPanel.add(usMenu,      "USER_MENU");
        cardPanel.add(Intrfc,      "INTERFACE");

        // 5) Make cardPanel the content pane
        setContentPane(cardPanel);

        // Frame settings
        setSize(bg.getIconWidth(), bg.getIconHeight());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

     public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }

    public UserMenu getUserMenu() {
        return usMenu;
    }

    public Interface getInterface() {
        return Intrfc;
    }
}
