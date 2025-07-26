package view;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionListener;

public class Interface extends JPanel {

    private JButton Manage;
    private JButton Fight;
    private JButton Hallff;
    private JButton End;

    private JButton UserConfirm;

    private JTextField useranswer;
    private JTextArea gameArea;

    private String username1;
    private String username2;

    private JLabel usernamelab1;
    private JLabel usernamelab2;
    // Background image
    private Image backgroundImage;

    public Interface() {
        Font font = new Font("Algerian", Font.BOLD, 10);

        // Load background image
        backgroundImage = new ImageIcon(
            "images/backgroundin (1).png"
        ).getImage();

        // Use BorderLayout for positioning
        this.setLayout(new BorderLayout());

        // --- Center Panel ---
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setOpaque(false);

        JPanel nesPanelCentop = new JPanel(new FlowLayout());
        nesPanelCentop.setOpaque(false);
        JPanel nesPanelCenSou = new JPanel(new FlowLayout());
        nesPanelCenSou.setOpaque(false);
        JPanel nesPanelCenCen = new JPanel(new BorderLayout());
        nesPanelCenCen.setOpaque(false);;
       

         ImageIcon tle = new ImageIcon(
            new ImageIcon("images/title.png")
                .getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH)
        );
        JLabel title = new JLabel(tle);
        nesPanelCentop.add(title);

        gameArea = new JTextArea("Hello");
        gameArea.setLineWrap(true);
        gameArea.setWrapStyleWord(true);
        gameArea.setEditable(false);
        gameArea.setFont(font);
        gameArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(gameArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        nesPanelCenCen.add(scrollPane, BorderLayout.CENTER);
        
        Manage = new JButton("Manage");
        Manage.setFont(font);
        Fight = new JButton("Fight");
        Fight.setFont(font);
        Hallff = new JButton("Hallff");
        Hallff.setFont(font);
        End = new JButton("End");
        End.setFont(font);

        nesPanelCenSou.add(Manage);
        nesPanelCenSou.add(Fight);
        nesPanelCenSou.add(Hallff);
        nesPanelCenSou.add(End);

        panelCenter.add(nesPanelCentop, BorderLayout.NORTH);
        panelCenter.add(nesPanelCenSou, BorderLayout.SOUTH);
        panelCenter.add(nesPanelCenCen, BorderLayout.CENTER);
    
        //textbox and answer
        //southPanel
        JPanel panelSouth = new JPanel(new FlowLayout());
        panelSouth.setOpaque(false);
        useranswer = new JTextField(15);
        useranswer.setText("Enter your Move");
        useranswer.setFont(font);
        useranswer.setOpaque(false);
        useranswer.setBorder(BorderFactory.createLineBorder(Color.black, 4, true));

        UserConfirm = new JButton("Confirm");
        UserConfirm.setFont(font);
        useranswer.setVisible(false);
        UserConfirm.setVisible(false);

        

// east panel
        JPanel panelEast = new JPanel(new GridLayout(3,1));
        panelEast.setOpaque(false);

        ImageIcon user2 = new ImageIcon(
            new ImageIcon("images/user2.png")
                .getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH)
        );
        ImageIcon user4 = new ImageIcon(
            new ImageIcon("images/user4.png")
                .getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH)
        );
        ImageIcon user6 = new ImageIcon(
            new ImageIcon("images/user6.png")
                .getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH)
        );
        JLabel u2 = new JLabel(user2);
        JLabel u4 = new JLabel(user4);
        JLabel u6 = new JLabel(user6);
        usernamelab2 = new JLabel(username2);
        usernamelab2.setFont(font);
        usernamelab2.setHorizontalAlignment(SwingConstants.CENTER); 
        usernamelab2.setVerticalAlignment(SwingConstants.CENTER); 
        usernamelab2.setOpaque(true);
        usernamelab2.setBackground(Color.decode("#e8d09f"));


        panelEast.add(u2);
        panelEast.add(u4);
        panelEast.add(u6);
        
                

        //west
        JPanel panelWest = new JPanel(new GridLayout(3,1));
        panelWest.setOpaque(false);

        ImageIcon user1 = new ImageIcon(
            new ImageIcon("images/user1.png")
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
        );
        ImageIcon user3 = new ImageIcon(
            new ImageIcon("images/user3.png")
                .getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH)
        );
        ImageIcon user5 = new ImageIcon(
            new ImageIcon("images/user5.png")
                .getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH)
        );
        
        JLabel u1 = new JLabel(user1);
        JLabel u3 = new JLabel(user3);
        JLabel u5 = new JLabel(user5);
        usernamelab1 = new JLabel(username1);
        usernamelab1.setFont(font);
        usernamelab1.setHorizontalAlignment(SwingConstants.CENTER); 
        usernamelab1.setVerticalAlignment(SwingConstants.CENTER); 
        usernamelab1.setOpaque(true);
        usernamelab1.setBackground(Color.decode("#e8d09f"));


        panelWest.add(u1);
        panelWest.add(u3);
        panelWest.add(u5);
        
       
        panelSouth.add(usernamelab1); 
        panelSouth.add(useranswer);
        panelSouth.add(UserConfirm);
        panelSouth.add(usernamelab2);
    
        // --- Add panels to main panel ---
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelEast, BorderLayout.EAST);
        this.add(panelWest, BorderLayout.WEST);
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
        Manage.addActionListener(listener);
        Fight.addActionListener(listener);
        Hallff.addActionListener(listener);
        End.addActionListener(listener);

        UserConfirm.addActionListener(listener);

    }

     public void setDocumentListener(DocumentListener listener) {
        useranswer.getDocument().addDocumentListener(listener);
    }

    public String getUserAnswer() {
        return useranswer.getText();
    }

    public void setUsername1(String name) {
        usernamelab1.setText(name);
    }  
    public void setUsername2(String name) {
        usernamelab2.setText(name);
    }  

    public void setGameAreaText(String text) {
        gameArea.setText(text);
    } 
    public void appendGameAreaText(String text) {
        gameArea.append(text);
    } 

    public void showManageControls() {
    useranswer.setVisible(true);
    UserConfirm.setVisible(true);
    revalidate(); repaint();
}

    public void hideManageControls() {
        useranswer.setVisible(false);
        UserConfirm.setVisible(false);
        revalidate(); repaint();
    }

    public void clearUserAnswer() {
        useranswer.setText("");
    }
}
