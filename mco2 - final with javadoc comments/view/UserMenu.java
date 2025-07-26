package view;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * UserMenu.java
 *
 * Represents the initial user menu screen where two player usernames are entered
 * before starting the game. Displays a background image, title graphic, and
 * input fields with a start button.
 */
public class UserMenu extends JPanel {

    /** Text field for entering the first player's username. */
    private JTextField username1answer;
    /** Text field for entering the second player's username. */
    private JTextField username2answer;
    /** Button to confirm usernames and start the game. */
    private JButton startBtn;

    /** Background image drawn behind all components. */
    private Image backgroundImage;

    /**
     * Constructs the UserMenu panel, loads the background, initializes components,
     * and arranges them using BorderLayout.
     */
    public UserMenu() {
  
        Font font = new Font("Algerian", Font.BOLD, 10);

        backgroundImage = new ImageIcon(
            "images/background.png"
        ).getImage();

       
        this.setLayout(new BorderLayout());

        
        JPanel panelCenter = new JPanel(new FlowLayout());
        panelCenter.setOpaque(false);

        ImageIcon tle = new ImageIcon(
            new ImageIcon("images/title.png")
                .getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH)
        );
        JLabel title = new JLabel(tle);
        panelCenter.add(title);

       
        JPanel panelSouth = new JPanel(new FlowLayout());
        panelSouth.setOpaque(false);

        startBtn = new JButton("Start");
        startBtn.setFont(font);

        username1answer = new JTextField(15);
        username2answer = new JTextField(15);

        username1answer.setFont(font);
        username2answer.setFont(font);

        username1answer.setText("Username 1");
        username2answer.setText("Username 2");

        username1answer.setOpaque(false);
        username2answer.setOpaque(false);

        username1answer.setBorder(BorderFactory.createLineBorder(Color.black, 4, true));
        username2answer.setBorder(BorderFactory.createLineBorder(Color.black, 4, true));

        panelSouth.add(username1answer);
        panelSouth.add(username2answer);
        panelSouth.add(startBtn);

       
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
    }

    
    @Override
     /**
     * Paints the background image to fill the entire panel area.
     *
     * @param g the Graphics context used for drawing
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

     /**
     * Registers an ActionListener to the start button.
     *
     * @param listener the ActionListener to handle button clicks
     */
    public void setActionListener(ActionListener listener) {
        startBtn.addActionListener(listener);
    }

    /**
     * Registers a DocumentListener to both username text fields to listen for text changes.
     *
     * @param listener the DocumentListener to handle document events
     */
    public void setDocumentListener(DocumentListener listener) {
        username1answer.getDocument().addDocumentListener(listener);
        username2answer.getDocument().addDocumentListener(listener);
    }

    /**
     * Retrieves the text entered in the first username field.
     *
     * @return the current text of the first username field
     */
    public String getUsername1() {
        return username1answer.getText();
    }

     /**
     * Retrieves the text entered in the second username field.
     *
     * @return the current text of the second username field
     */
    public String getUsername2() {
        return username2answer.getText();
    }
}
