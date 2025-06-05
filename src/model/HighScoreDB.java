package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreDB implements Serializable {
    private List<HighScoreEntry> highScores = new ArrayList<HighScoreEntry>();

    public List<HighScoreEntry> getHighScores() {
        return Collections.unmodifiableList(highScores);
    }

    public void addHighScore(HighScoreEntry entry) {
        highScores.add(entry);
        highScores.sort((a,b) -> Integer.compare(b.getScore(), a.getScore()));
    }

    public static HighScoreDB load(File file) {
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();

                if (obj instanceof HighScoreDB db) {
                    return db;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return new HighScoreDB();
    }

    public void save(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
