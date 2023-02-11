package SnakeGamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

public class MainMenu extends JFrame implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    private final JButton startButton = new JButton("Start Game");
    private final JButton exitButton = new JButton("Exit");
    private final JButton optionsButton = new JButton("Options");
    private OptionsMenu optionsMenu = new OptionsMenu();
    private JLabel background;
    private MusicSoundBoard soundBoard;
    public MainMenu() {
        defaultMainMenu();
        setBackgroundImage();
        setImage(getClass().getClassLoader().getResource("3D-Text-1s-280px.gif"), 150, -10, 300, 300);
        setImage(getClass().getClassLoader().getResource("snake.gif"), 95, 125, 400, 300);
        setButton(startButton, 375);
        setButton(optionsButton, 395);
        setButton(exitButton, 415);
        setMouseListener(startButton);
        setMouseListener(optionsButton);
        setMouseListener(exitButton);
        setActionListeners(startButton, optionsButton, exitButton);
    }

    public void defaultMainMenu(){
        //Set the title and layout of the main menu frame
        setTitle("Snake Game");
        setLayout(null);
        //Set the size and location of the main menu frame
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        soundBoard = new MusicSoundBoard();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setMenuMusic(){
        soundBoard.mainMenuMusic();
    }

    public void setImage(URL filename, int x, int y, int width, int height){
        //Sets image
        ImageIcon imageIcon = new ImageIcon(filename);
        JLabel image = new JLabel(imageIcon);
        //Adds image to the main menu
        add(image);
        //Adjusts size and position
        image.setBounds(x, y, width, height);
        getContentPane().setComponentZOrder(image, 0);
    }

    public void setBackgroundImage(){
        //getClass().getClassLoader().getResource() allows for getting resources during production build (not just code)
        ImageIcon bg = new ImageIcon(getClass().getClassLoader().getResource("grid-edit.gif"));
        background = new JLabel(bg);
        add(background);
        background.setBounds(-25, -95, 750, 750);
        background.setBackground(new Color(0,0,0)); //Changes window color
    }

    public void setButton(JButton jButton, int yaxis){
        add(jButton);
        getContentPane().setComponentZOrder(jButton, 0);
        jButton.setBounds(250, yaxis, 100, 20);
        jButton.addActionListener(this);
        jButton.setFont(new Font("Arial", Font.ITALIC, 16));
        jButton.setForeground(Color.black); //Changes text color
        jButton.setVisible(true);
        //Removes button border
//        jButton.setBorderPainted(false);
    }

    public void setMouseListener(JButton jButton){
        jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton.setBackground(Color.green);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton.setBackground(UIManager.getColor("control"));
            }
        });
    }

    public void setActionListeners(JButton start, JButton options, JButton exit){
        start.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    start.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    exit.requestFocus();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    options.requestFocus();
                }
            }
        });
        options.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    options.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    start.requestFocus();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    exit.requestFocus();
                }
            }
        });
        exit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    exit.doClick();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    options.requestFocus();
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    start.requestFocus();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
//            soundBoard.stopMenuMusic();
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            new GameFrame();
            dispose();
        } else if(e.getSource() == optionsButton) {
           //JOptionPane.showMessageDialog(this, "Placeholder for options menu");
            soundBoard.setSoundAndPause(getClass().getResource("/main-menu-sound.wav"), 150);
            new OptionsMenu().optionsFrame.setVisible(true);
        } else if(e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}

