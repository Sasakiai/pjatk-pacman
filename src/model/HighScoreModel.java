package model;

import java.io.File;
import java.util.List;


public class HighScoreModel {
    private final File highScoreFile = new File("highscores.txt");
    private HighScoreDB highScoreDB;

    public HighScoreModel() {
        highScoreDB = HighScoreDB.load(highScoreFile);
    }

    public synchronized void addHighScore(String name, int score) {
        highScoreDB.addHighScore(new HighScoreEntry(name, score));
        highScoreDB.save(highScoreFile);
    }

    public List<HighScoreEntry> getHighScores() {
        return highScoreDB.getHighScores();
    }
}
