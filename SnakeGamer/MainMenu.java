package SnakeGamer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainMenu extends JFrame implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    //Create buttons for the main menu
    private final JButton startButton = new JButton("Start Game");
    private final JButton exitButton = new JButton("Exit");
    private final JButton optionsButton = new JButton("Options");
    private OptionsMenu optionsMenu = new OptionsMenu();
    private JLabel titleLogo;
    private JLabel snakeImage;

    public MainMenu() {
        //Set the title and layout of the main menu frame
        setTitle("Snake Game");
        setLayout(null);

        //Set background image...change directory according to the image route on your os.
        //If background only shows a black screen then there might be a typo or wrong directory.
        ImageIcon bg = new ImageIcon("/Users/isaaccherry/Documents/Snake-Game/Snake-Game/Images/grid.gif");
        JLabel background = new JLabel(bg);
        ImageIcon logo = new ImageIcon("/Users/isaaccherry/Documents/Snake-Game/Snake-Game/Images/3D-Text-1s-280px.gif");
        titleLogo = new JLabel(logo);
        titleLogo.setVerticalAlignment(JLabel.TOP);
        ImageIcon snake = new ImageIcon("/Users/isaaccherry/Documents/Snake-Game/Snake-Game/Images/snake.gif");
        snakeImage = new JLabel(snake);

        //Add the buttons to the main menu frame
        add(startButton);
        add(optionsButton);
        add(exitButton);

        //Adds background to the main menu, adjust size and position
        add(background);
        add(snakeImage);
        add(titleLogo);
        
        //Snake, logo, and background positions
        background.setBounds(-25, -95, 750, 750);
        snakeImage.setBounds(95, 125, 400, 300);
        titleLogo.setBounds(150, 15, 300, 300);
        
      //background.setOpaque(true);
        background.setBackground(new Color(0,0,0)); //Changes window color

        //Ensure that the buttons/images are rendered on top of the background image
        getContentPane().setComponentZOrder(titleLogo, 0);
        getContentPane().setComponentZOrder(snakeImage, 0);
        getContentPane().setComponentZOrder(startButton, 0);
        getContentPane().setComponentZOrder(optionsButton, 0);
        getContentPane().setComponentZOrder(exitButton, 0);

        //move the buttons on top of background
        startButton.setBounds(250, 375, 100, 20);
        optionsButton.setBounds(250, 395, 100, 20);
        exitButton.setBounds(250, 415, 100, 20);

        //Add action listeners to the buttons
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        optionsButton.addActionListener(this);

        //Changes button font and removes button border
        startButton.setFont(new Font("Arial", Font.ITALIC, 16));
        optionsButton.setFont(new Font("Arial", Font.ITALIC, 16));
        exitButton.setFont(new Font("Arial", Font.ITALIC, 16));
        startButton.setForeground(Color.black);
        optionsButton.setForeground(Color.black);
        exitButton.setForeground(Color.black);
        startButton.setVisible(true);
        optionsButton.setVisible(true);
        exitButton.setVisible(true);
//        startButton.setBorderPainted(false);
//        highScoreButton.setBorderPainted(false);
//        exitButton.setBorderPainted(false);

        //Set the size and location of the main menu frame
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
         //Arrow keys for navigating the menu and enter to execute each action
        startButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startButton.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    exitButton.requestFocus();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    optionsButton.requestFocus();
                }
            }
        });

        optionsButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    optionsButton.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    startButton.requestFocus();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    exitButton.requestFocus();
                }
            }
        });

        exitButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    exitButton.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    optionsButton.requestFocus();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    startButton.requestFocus();
                }
            }
        });
        //Buttons change color when mouse hovers over each button
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.CYAN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(UIManager.getColor("control"));
            }
        });

        optionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                optionsButton.setBackground(Color.CYAN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                optionsButton.setBackground(UIManager.getColor("control"));
            }
        });

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(Color.CYAN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(UIManager.getColor("control"));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
            //Start the game when the start button is clicked
            new GameFrame();
            dispose();
        } else if(e.getSource() == optionsButton) {
            //Show the high scores when the high scores button is clicked
           //JOptionPane.showMessageDialog(this, "Placeholder for options menu");
            new OptionsMenu().optionsFrame.setVisible(true);
        } else if(e.getSource() == exitButton) {
            //Exit the game when the exit button is clicked
            System.exit(0);
        }
    }
}

