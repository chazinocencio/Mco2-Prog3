package view;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserMenu extends JPanel {

    private JTextField username1answer;
    private JTextField username2answer;
    private JButton startBtn;

    // Background image
    private Image backgroundImage;

    public UserMenu() {

        Font font = new Font("Algerian", Font.BOLD, 10);

        // Load background image
        backgroundImage = new ImageIcon(
            "images/background.png"
        ).getImage();

        // Use BorderLayout for positioning
        this.setLayout(new BorderLayout());

        // --- Center Panel with title ---
        JPanel panelCenter = new JPanel(new FlowLayout());
        panelCenter.setOpaque(false);

        ImageIcon tle = new ImageIcon(
            new ImageIcon("images/title.png")
                .getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH)
        );
        JLabel title = new JLabel(tle);
        panelCenter.add(title);

        // --- South Panel with text fields and button ---
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

        // --- Add panels to main panel ---
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
    }

    // Override paintComponent to draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Public methods
    public void setActionListener(ActionListener listener) {
        startBtn.addActionListener(listener);
    }

    public void setDocumentListener(DocumentListener listener) {
        username1answer.getDocument().addDocumentListener(listener);
        username2answer.getDocument().addDocumentListener(listener);
    }

    public String getUsername1() {
        return username1answer.getText();
    }

    public String getUsername2() {
        return username2answer.getText();
    }
}
