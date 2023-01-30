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
    private String gridColor = "white";
    private String imageChoice = "no";
    private String musicChoice = "off";
    private int gameSpeed = 75;
    private boolean timeMode = false;
    private Timer timer;
    private JSlider speedSlider;
    private JComboBox<String> snakeColorComboBox;
    private JComboBox<String> gridColorComboBox;
    private JComboBox<String> imageComboBox;
    private JComboBox<String> musicComboBox;
    private JCheckBox timeModeCheckBox;
    public JFrame optionsFrame; //If it's not accessible to the main menu then the options will not load when clicked
    private static final String OPTIONS_FILE = "options.txt";


     public OptionsMenu(){
         loadOptions();
         optionsFrame = new JFrame("Options");
         optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
        
        //Snake color settings
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
         
         //Grid color settings
         JLabel gridColorLabel = new JLabel("Select grid color:");
         gridColorComboBox = new JComboBox<>(new String[]{"white", "gray", "black"});
         gridColorComboBox.setSelectedItem(gridColor);
         gridColorComboBox.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 gridColor = (String) gridColorComboBox.getSelectedItem();
             }
         });
         optionsPanel.add(gridColorLabel);
         optionsPanel.add(gridColorComboBox);
         
         //Background image settings
         JLabel imageLabel = new JLabel("Toggle background Images:");
         imageComboBox = new JComboBox<>(new String[]{"yes", "no"});
         imageComboBox.setSelectedItem(imageChoice);
         imageComboBox.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 imageChoice = (String) imageComboBox.getSelectedItem();
             }
         });
         optionsPanel.add(imageLabel);
         optionsPanel.add(imageComboBox);
         
          //Music settings
         JLabel musicLabel = new JLabel("Enable music:");
         musicComboBox = new JComboBox<>(new String[]{"off", "on"});
         musicComboBox.setSelectedItem(musicChoice);
         musicComboBox.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 musicChoice = (String) musicComboBox.getSelectedItem();
             }
         });
         optionsPanel.add(musicLabel);
         optionsPanel.add(musicComboBox);
        
        //Snake speed settings
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

        //TODO: Time Mode settings
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
         
        //Save button properties
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

        //Cancel button properties
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
        //I only want this visible when the options button is clicked NOT in-game
        optionsFrame.setVisible(false);
    }

    public void loadOptions() {
        try (Scanner reader = new Scanner(new File(OPTIONS_FILE))) {
            snakeColor = reader.nextLine();
            gridColor = reader.nextLine();
            imageChoice = reader.nextLine();
            musicChoice = reader.nextLine();
            gameSpeed = reader.nextInt();
            timeMode = reader.nextBoolean();
        } catch (FileNotFoundException e) {
            System.err.println("Options file not found, using default options.");
        }
    }

    //Write settings to a save file
    public void saveOptions() {
        try (PrintWriter writer = new PrintWriter(OPTIONS_FILE)) {
            writer.println(snakeColor);
            writer.println(gridColor);
            writer.println(imageChoice);
            writer.println(musicChoice);
            writer.println(gameSpeed);
            writer.println(timeMode);
        } catch (FileNotFoundException e) {
            System.err.println("Could not save options to file " + OPTIONS_FILE);
        }
    }
    
    private void saveSettings() {
        snakeColor = (String) snakeColorComboBox.getSelectedItem();
        gridColor = (String) gridColorComboBox.getSelectedItem();
        imageChoice = (String) imageComboBox.getSelectedItem();
        musicChoice = (String) musicComboBox.getSelectedItem();
        gameSpeed = speedSlider.getValue();
        timeMode = timeModeCheckBox.isSelected();
    }
    
    //Getters so the parameters are accessible to other classes
    public String getSnakeColor() {
        return snakeColor;
    }
    public String getGridColor() {
        return gridColor;
    }
    public String getBackgroundImages(){
        return imageChoice;
    }
    public String getMusicChoice() {
        return musicChoice;
    }
    public int getGameSpeed() {
        return gameSpeed;
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
