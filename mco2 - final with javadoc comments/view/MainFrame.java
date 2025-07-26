package view;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame.java
 *
 * The main application window that manages different UI screens using a CardLayout.
 * Initializes the user menu and game interface panels and handles switching between them.
 */
public class MainFrame extends JFrame {
    /** Layout manager for card-based navigation between panels. */
    private CardLayout cardLayout;
    /** Panel that holds each screen as a card. */
    private JPanel cardPanel;

    /** The initial user menu screen. */
    private UserMenu usMenu;
    /** The main game interface screen. */
    private Interface Intrfc;

    /**
     * Constructs the MainFrame, sets up the CardLayout, loads the background,
     * and adds the UserMenu and Interface panels as cards.
     */
    public MainFrame() {
        super("FINAL FANTASY");

        
        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);

        
        ImageIcon bg = new ImageIcon(
            "images/background.png"
        );

        JLabel background = new JLabel(bg);
        
        
        usMenu = new UserMenu();
        Intrfc = new Interface();
        
        
        cardPanel.add(usMenu,      "USER_MENU");
        cardPanel.add(Intrfc,      "INTERFACE");

        setContentPane(cardPanel);

        setSize(bg.getIconWidth(), bg.getIconHeight());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Switches the displayed card to the one matching the specified name.
     *
     * @param name the identifier of the card to show ("USER_MENU" or "INTERFACE")
     */
     public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }

    /**
     * Retrieves the UserMenu panel for further configuration or listener attachment.
     *
     * @return the UserMenu instance used in this frame
     */
    public UserMenu getUserMenu() {
        return usMenu;
    }

    /**
     * Retrieves the game Interface panel for updates or listener attachment.
     *
     * @return the Interface instance used in this frame
     */
    public Interface getInterface() {
        return Intrfc;
    }
}
