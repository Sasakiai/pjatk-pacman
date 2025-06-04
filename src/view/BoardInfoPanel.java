package view;

import javax.swing.*;
import java.awt.*;

public class BoardInfoPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel livesLabel;

    public BoardInfoPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        scoreLabel = new JLabel("Score: 0");
        timeLabel = new JLabel("Time: 0");
        livesLabel = new JLabel("Lives: 3");

        add(scoreLabel);
        add(Box.createHorizontalStrut(20));
        add(timeLabel);
        add(Box.createHorizontalStrut(20));
        add(livesLabel);
    }

    public void updateScore(int score) {
        SwingUtilities.invokeLater(() -> scoreLabel.setText("Score: " + score));
    }

    public void updateTime(int time) {
        SwingUtilities.invokeLater(() -> timeLabel.setText("Time: " + time));
    }

    public void updateLives(int lives) {
        SwingUtilities.invokeLater(() -> livesLabel.setText("Lives: " + lives));
    }
}
