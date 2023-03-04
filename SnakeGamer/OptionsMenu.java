package SnakeGamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Dimension;


public class OptionsMenu extends JFrame implements ActionListener {
    private JButton saveButton, cancelButton;
    private String snakeColor = "cyan";
    private String gridColor = "black";
    private String imageChoice = "off";
    private String musicChoice = "off";
    private int gameSpeed = 75;
    private String difficultyChoice = "Normal";
    private boolean iscChecked = true;
    private JSlider speedSlider;
    private JCheckBox checkBox;
    private JComboBox<String> snakeColorComboBox;
    private JComboBox<String> gridColorComboBox;
    private JComboBox<String> imageComboBox;
    private JComboBox<String> musicComboBox;
    private JComboBox<String> difficultyComboBox;
    //If it's not accessible to the main menu then the options will not load when clicked
    public JFrame optionsFrame;
    private static final String OPTIONS_FILE = "options.txt";
    private JPanel optionsPanel;
    private MusicSoundBoard soundBoard;


     public OptionsMenu(){
         soundBoard = new MusicSoundBoard();
         loadOptions();
         optionsFrame = new JFrame("Options");
         optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         optionsPanel = new JPanel();
         optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
         optionsPanel.setPreferredSize(new Dimension(300, 375));
         snakeColorSettings();
         gridColorSettings();
         backgroundImageSettings();
         musicSettings();
         difficultySettings();
         snakeSpeedSettings();
         scorePromptToggle();

        //Save button properties
        saveButton = new JButton("Save");
        saveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        setSaveButton();

        //Cancel button properties
        cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        setCancelButton();

        optionsFrame.add(optionsPanel);
        optionsFrame.pack();
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
            soundBoard.setSoundAndPause(getClass().getResource("/cancel-sound.wav"), 275);
            optionsFrame.dispose();
        });
    }

    public void snakeColorSettings(){
        JLabel snakeColorLabel = new JLabel("Select snake color:");
        snakeColorLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //Changes JLabel's X-position
        snakeColorComboBox = new JComboBox<>(new String[]
                {"orange", "purple", "blue", "green", "cyan", "yellow", "pink", "random"});
        snakeColorComboBox.setSelectedItem(snakeColor);
        snakeColorComboBox.addActionListener(e -> snakeColor = (String) snakeColorComboBox.getSelectedItem());
        optionsPanel.add(snakeColorLabel);
        optionsPanel.add(snakeColorComboBox);
    }

    public void gridColorSettings(){
        JLabel gridColorLabel = new JLabel("Select grid color:");
        gridColorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridColorComboBox = new JComboBox<>(new String[]{"white", "gray", "black"});
        gridColorComboBox.setSelectedItem(gridColor);
        gridColorComboBox.addActionListener(e -> gridColor = (String) gridColorComboBox.getSelectedItem());
        optionsPanel.add(gridColorLabel);
        optionsPanel.add(gridColorComboBox);
    }

    public void backgroundImageSettings(){
        JLabel imageLabel = new JLabel("Toggle background Images:");
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageComboBox = new JComboBox<>(new String[]{"off", "static animated-bg", "moving background", "all bg-images"});
        imageComboBox.setSelectedItem(imageChoice);
        imageComboBox.addActionListener(e -> imageChoice = (String) imageComboBox.getSelectedItem());
        optionsPanel.add(imageLabel);
        optionsPanel.add(imageComboBox);
    }

    public void musicSettings(){
        JLabel musicLabel = new JLabel("Enable music:");
        musicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicComboBox = new JComboBox<>(new String[]{"on", "off"});
        musicComboBox.setSelectedItem(musicChoice);
        musicComboBox.addActionListener(e -> musicChoice = (String) musicComboBox.getSelectedItem());
        optionsPanel.add(musicLabel);
        optionsPanel.add(musicComboBox);
    }

    public void difficultySettings(){
        JLabel difficultyLabel = new JLabel("Choose your difficulty:");
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Normal", "Hard", "Insane"});
        difficultyComboBox.setSelectedItem(difficultyChoice);
        difficultyComboBox.addActionListener(e -> difficultyChoice = (String) difficultyComboBox.getSelectedItem());
        optionsPanel.add(difficultyLabel);
        optionsPanel.add(difficultyComboBox);
    }

    public void snakeSpeedSettings(){
        JLabel gameSpeedLabel = new JLabel("Select game speed:");
        gameSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        speedSlider = new JSlider(JSlider.HORIZONTAL, 30, 100, gameSpeed);
        speedSlider.setInverted(true);
        speedSlider.addChangeListener(e -> gameSpeed = speedSlider.getValue());
        optionsPanel.add(gameSpeedLabel);
        optionsPanel.add(speedSlider);
    }

    public void scorePromptToggle(){
         JLabel scoreLabel = new JLabel("Turn on high score prompt?");
         scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         checkBox = new JCheckBox();
         checkBox.setAlignmentX(JCheckBox.CENTER_ALIGNMENT);
         checkBox.setSelected(iscChecked);
         checkBox.addItemListener(e-> iscChecked = checkBox.isSelected());
         checkBox.setSize(300, 300);
         checkBox.setVisible(true);
         optionsPanel.add(scoreLabel);
         optionsPanel.add(checkBox);
    }

    public void loadOptions() {
        try (Scanner reader = new Scanner(new File(OPTIONS_FILE))) {
            snakeColor = reader.nextLine();
            gridColor = reader.nextLine();
            imageChoice = reader.nextLine();
            musicChoice = reader.nextLine();
            difficultyChoice = reader.nextLine();
            gameSpeed = reader.nextInt();
            iscChecked = reader.nextBoolean();
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
            writer.println(iscChecked);
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
        iscChecked = checkBox.isSelected();
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
    public boolean getScorePromptToggle(){
         return iscChecked;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
