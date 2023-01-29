package SnakeGamer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OptionsMenu extends JFrame implements ActionListener {
    private JButton saveButton, cancelButton;
    private String snakeColor = "blue";
    private int gameSpeed = 75;
    private boolean timeMode = false;
    private Timer timer;
    private JSlider speedSlider;
    private JComboBox<String> snakeColorComboBox;
    private JCheckBox timeModeCheckBox;
    private JFrame optionsFrame;
    private static final String OPTIONS_FILE = "options.txt";


     OptionsMenu(){
         loadOptions();
         optionsFrame = new JFrame("Options");
         optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));

        JLabel snakeColorLabel = new JLabel("Select snake color:");
        snakeColorComboBox = new JComboBox<>(new String[]
            {"orange", "purple", "blue", "green", "cyan", "yellow", "pink", "random"});
        snakeColorComboBox.setSelectedItem(snakeColor);
        snakeColorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    snakeColor = (String) snakeColorComboBox.getSelectedItem();
            }
        });
        optionsPanel.add(snakeColorLabel);
        optionsPanel.add(snakeColorComboBox);

        JLabel gameSpeedLabel = new JLabel("Select game speed:");
        //The higher the number, the slower the game is
        speedSlider = new JSlider(JSlider.HORIZONTAL, 30, 100, gameSpeed);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameSpeed = speedSlider.getValue();
            }
        });
        optionsPanel.add(gameSpeedLabel);
        optionsPanel.add(speedSlider);

        JLabel timeModeLabel = new JLabel("Select time mode:");
        timeModeCheckBox = new JCheckBox();
        timeModeCheckBox.setSelected(timeMode);
        timeModeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                timeMode = timeModeCheckBox.isSelected();
            }
        });
         optionsPanel.add(timeModeLabel);
         optionsPanel.add(timeModeCheckBox);

        saveButton = new JButton("Save");
        optionsPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
                saveOptions();
                optionsFrame.dispose();
            }
        });


        cancelButton = new JButton("Cancel");
        optionsPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                optionsFrame.dispose();
            }
        });
        optionsFrame.add(optionsPanel);
        optionsFrame.pack();
        optionsFrame.setVisible(true);
    }

    public void loadOptions() {
        try (Scanner reader = new Scanner(new File(OPTIONS_FILE))) {
            snakeColor = reader.nextLine();
            gameSpeed = reader.nextInt();
            timeMode = reader.nextBoolean();
        } catch (FileNotFoundException e) {
            System.err.println("Options file not found, using default options.");
        }
    }

    public void saveOptions() {
        try (PrintWriter writer = new PrintWriter(OPTIONS_FILE)) {
            writer.println(snakeColor);
            writer.println(gameSpeed);
            writer.println(timeMode);
        } catch (FileNotFoundException e) {
            System.err.println("Could not save options to file " + OPTIONS_FILE);
        }
    }
    public int getGameSpeed() {
        return gameSpeed;
    }
    public String getSnakeColor() {
        return snakeColor;
    }

    private void saveSettings() {
        snakeColor = (String) snakeColorComboBox.getSelectedItem();
        gameSpeed = speedSlider.getValue();
        timeMode = timeModeCheckBox.isSelected();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton){
            saveSettings();
            saveOptions();
        } else if(e.getSource() == cancelButton){
            optionsFrame.dispose();
        }
    }
}
