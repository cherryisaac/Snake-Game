package SnakeGamer;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

public class HighScoreTracker extends JFrame implements ActionListener {
    private OptionsMenu optionsMenu;
    private JFrame frame;
    private MusicSoundBoard soundBoard;


    public HighScoreTracker() {
        optionsMenu = new OptionsMenu();
        frame = new JFrame("High Scores");
        soundBoard = new MusicSoundBoard();
    }

    public String mapNameWithDifficulty(){
        switch (optionsMenu.getDifficultyChoice()){
            case "Normal" -> {
                return  " Normal";
            }
            case "Hard" -> {
                return  " Hard";
            }
            case "Insane" -> {
                return  " Insane";
            }
            default -> {
                return " Easy";
            }
        }
    }

    public void saveHighScores(TreeMap<Integer, String> scores) {
        try {
            FileWriter writer = new FileWriter("highscores.txt", false);
//            writer.write("͟N͟a͟m͟e͟ ͟|͟ ͟S͟c͟o͟r͟e͟͟ ͟͟ ͟|͟ ͟D͟i͟f͟f͟i͟c͟u͟l͟t͟y͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟\n");
            writer.write("͟N͟a͟m͟e͟ ͟|͟ ͟S͟c͟o͟r͟e͟͟ ͟͟ ͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟͟ ͟͟ ͟͟ ͟͟ ͟͟\n");
            for(Map.Entry<Integer, String> entry : scores.entrySet()) {
//                writer.write(entry.getValue() + ": " + entry.getKey() + mapNameWithDifficulty() + "\n");
                writer.write(entry.getValue() + ": " + entry.getKey() + "\n");
            }
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<Integer, String> loadHighScores() {
        TreeMap<Integer, String> scores = new TreeMap<>(Collections.reverseOrder());

        try {
            File file = new File("highscores.txt");
            if(!file.exists()) {
                return scores;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if(parts.length == 2) {
                    String name = parts[0].trim();
                    String[] scoreAndDifficulty = parts[1].trim().split(" ");
                    if(scoreAndDifficulty.length == 2) {
                        int score = Integer.parseInt(scoreAndDifficulty[0].stripTrailing());
                        scores.put(score, name);
                    }
                }
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return scores;
    }


    public void showHighScores() {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    hideHighScores();
                    soundBoard.setSound(getClass().getResource("/highscores-close.wav"));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"));
            String line = reader.readLine();
            while (line != null) {
                textArea.append(line + "\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            frame.pack();
            frame.setVisible(true);
    }

    public void hideHighScores() {
            frame.setVisible(false);
            frame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}