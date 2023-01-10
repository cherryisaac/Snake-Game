package SnakeGamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    //Create buttons for the main menu
    private JButton startButton = new JButton("Start Game");
    private JButton exitButton = new JButton("Exit");
    private JButton highScoreButton = new JButton("High Scores");

    public MainMenu() {
        //Set the title and layout of the main menu frame
        setTitle("Snake Game");
        setLayout(new GridLayout(3, 1));

        //Add the buttons to the main menu frame
        add(startButton);
        add(highScoreButton);
        add(exitButton);

        //Add action listeners to the buttons
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        highScoreButton.addActionListener(this);

        //Set the size and location of the main menu frame
        setSize(200, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
            //Start the game when the start button is clicked
            new GameFrame();
            dispose();
        } else if(e.getSource() == highScoreButton) {
            //Show the high scores when the high scores button is clicked
            JOptionPane.showMessageDialog(this, "Placeholder for high scores");
        } else if(e.getSource() == exitButton) {
            //Exit the game when the exit button is clicked
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MainMenu().setVisible(true);
    }
}

