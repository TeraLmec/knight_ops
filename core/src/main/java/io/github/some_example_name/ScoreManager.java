package io.github.some_example_name;

public class ScoreManager {
    private static ScoreManager instance;
    private int score;

    private ScoreManager() {
        score = 500000;
    }

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public void addPoints(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 500;
    }
}
