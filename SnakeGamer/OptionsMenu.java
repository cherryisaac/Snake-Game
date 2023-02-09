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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class OptionsMenu extends JFrame implements ActionListener {
    private JButton saveButton, cancelButton;
    private String snakeColor = "cyan";
    private String gridColor = "black";
    private String imageChoice = "no";
    private String musicChoice = "off";
    private int gameSpeed = 75;
    private String difficultyChoice = "Normal";
    private boolean timeMode = false;
    private Timer timer;
    private JSlider speedSlider;
    private JComboBox<String> snakeColorComboBox;
    private JComboBox<String> gridColorComboBox;
    private JComboBox<String> imageComboBox;
    private JComboBox<String> musicComboBox;
    private JComboBox<String> difficultyComboBox;
    private JCheckBox timeModeCheckBox;
    //If it's not accessible to the main menu then the options will not load when clicked
    public JFrame optionsFrame;
    private static final String OPTIONS_FILE = "options.txt";
    JPanel optionsPanel;
    MusicSoundBoard soundBoard;


     public OptionsMenu(){
         soundBoard = new MusicSoundBoard();
         loadOptions();
         optionsFrame = new JFrame("Options");
         optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         optionsPanel = new JPanel();
         optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
         snakeColorSettings();
         gridColorSettings();
         backgroundImageSettings();
         musicSettings();
         difficultySettings();
         snakeSpeedSettings();
         timeModeSettings();

        //Save button properties
        saveButton = new JButton("Save");
        setSaveButton();

        //Cancel button properties
        cancelButton = new JButton("Cancel");
        setCancelButton();
        //I only want this visible when the options button is clicked NOT in-game
        optionsFrame.setVisible(false);
    }
    public void setSaveButton(){
        optionsPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            saveSettings();
            saveOptions();
            soundBoard.setSoundAndPause(getClass().getResource("/save-sound.wav"), 350);
            optionsFrame.dispose();
        });
    }

    public void setCancelButton(){
        optionsPanel.add(cancelButton);
        cancelButton.addActionListener(e -> {
            setVisible(false);
            soundBoard.setSoundAndPause(getClass().getResource("/cancel-sound.wav"), 275);
            optionsFrame.dispose();
        });
        optionsFrame.add(optionsPanel);
        optionsFrame.pack();
    }

    public void snakeColorSettings(){
        JLabel snakeColorLabel = new JLabel("Select snake color:");
        snakeColorComboBox = new JComboBox<>(new String[]
                {"orange", "purple", "blue", "green", "cyan", "yellow", "pink", "random"});
        snakeColorComboBox.setSelectedItem(snakeColor);
        snakeColorComboBox.addActionListener(e -> snakeColor = (String) snakeColorComboBox.getSelectedItem());
        optionsPanel.add(snakeColorLabel);
        optionsPanel.add(snakeColorComboBox);
    }

    public void gridColorSettings(){
        JLabel gridColorLabel = new JLabel("Select grid color:");
        gridColorComboBox = new JComboBox<>(new String[]{"white", "gray", "black"});
        gridColorComboBox.setSelectedItem(gridColor);
        gridColorComboBox.addActionListener(e -> gridColor = (String) gridColorComboBox.getSelectedItem());
        optionsPanel.add(gridColorLabel);
        optionsPanel.add(gridColorComboBox);
    }

    public void backgroundImageSettings(){
        JLabel imageLabel = new JLabel("Toggle background Images:");
        imageComboBox = new JComboBox<>(new String[]{"on", "off"});
        imageComboBox.setSelectedItem(imageChoice);
        imageComboBox.addActionListener(e -> imageChoice = (String) imageComboBox.getSelectedItem());
        optionsPanel.add(imageLabel);
        optionsPanel.add(imageComboBox);
    }

    public void musicSettings(){
        JLabel musicLabel = new JLabel("Enable music:");
        musicComboBox = new JComboBox<>(new String[]{"on", "off"});
        musicComboBox.setSelectedItem(musicChoice);
        musicComboBox.addActionListener(e -> musicChoice = (String) musicComboBox.getSelectedItem());
        optionsPanel.add(musicLabel);
        optionsPanel.add(musicComboBox);
    }

    public void difficultySettings(){
        JLabel difficultyLabel = new JLabel("Choose your difficulty:");
        difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Normal", "Hard", "Insane"});
        difficultyComboBox.setSelectedItem(difficultyChoice);
        difficultyComboBox.addActionListener(e -> difficultyChoice = (String) difficultyComboBox.getSelectedItem());
        optionsPanel.add(difficultyLabel);
        optionsPanel.add(difficultyComboBox);
    }

    public void snakeSpeedSettings(){
        JLabel gameSpeedLabel = new JLabel("Select game speed:");
        speedSlider = new JSlider(JSlider.HORIZONTAL, 30, 100, gameSpeed);
        speedSlider.setInverted(true);
        speedSlider.addChangeListener(e -> gameSpeed = speedSlider.getValue());
        optionsPanel.add(gameSpeedLabel);
        optionsPanel.add(speedSlider);
    }

    public void timeModeSettings(){
        //TODO: Time Mode settings
        JLabel timeModeLabel = new JLabel("Select time mode:");
        timeModeCheckBox = new JCheckBox();
        timeModeCheckBox.setSelected(timeMode);
        timeModeCheckBox.addItemListener(e -> timeMode = timeModeCheckBox.isSelected());
        optionsPanel.add(timeModeLabel);
        optionsPanel.add(timeModeCheckBox);
    }

    public void loadOptions() {
        try (Scanner reader = new Scanner(new File(OPTIONS_FILE))) {
            snakeColor = reader.nextLine();
            gridColor = reader.nextLine();
            imageChoice = reader.nextLine();
            musicChoice = reader.nextLine();
            difficultyChoice = reader.nextLine();
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
            writer.println(difficultyChoice);
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
        difficultyChoice = (String) difficultyComboBox.getSelectedItem();
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
    public String getDifficultyChoice(){
        return difficultyChoice;
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
