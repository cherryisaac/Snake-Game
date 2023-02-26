package SnakeGamer;


import javax.swing.*;
import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HighScoreTracker {
    private OptionsMenu optionsMenu;
    public JFrame frame;


    public HighScoreTracker(){
        optionsMenu = new OptionsMenu();
        frame = new JFrame("High Scores");
        frame.setVisible(false);
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
            FileWriter writer = new FileWriter("highscores.txt");
            writer.write("͟N͟a͟m͟e͟ ͟|͟ ͟S͟c͟o͟r͟e͟͟ ͟͟ ͟|͟ ͟D͟i͟f͟f͟i͟c͟u͟l͟t͟y͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟͟ ͟\n");
            for(Map.Entry<Integer, String> entry : scores.entrySet()) {
                writer.write(entry.getValue() + ": " + entry.getKey() + mapNameWithDifficulty() + "\n");
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
        if (!frame.isVisible()) {
            frame.pack();
            frame.setVisible(true);
        }
    }

    public void hideHighScores() {
        if (frame.isVisible()) {
            frame.setVisible(false);
            frame.dispose();
        }
    }

}

