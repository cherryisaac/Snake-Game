package SnakeGamer;
import java.util.Map;
import java.util.TreeMap;

public class HighScoreTracker {

    // Use a TreeMap to store the high scores, mapping the score to the player's name
    private Map<Integer, String> highScores;

    public HighScoreTracker() {
        highScores = new TreeMap<>();
    }

    public void addScore(int score, String playerName) {
        highScores.put(score, playerName);
    }

    public int getHighScore() {
        // If there are no high scores yet, return 0 as the high score
        if (highScores.isEmpty()) {
            return 0;
        }

        // Otherwise, return the highest score
        return highScores.keySet().iterator().next();
    }

    public String getHighScorePlayerName() {
        // If there are no high scores yet, return an empty string
        if (highScores.isEmpty()) {
            return "";
        }

        // Otherwise, return the name of the player who achieved the highest score
        return highScores.values().iterator().next();
    }
}

