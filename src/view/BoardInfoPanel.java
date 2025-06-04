package view;

import javax.swing.*;
import java.awt.*;

public class BoardInfoPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel timeLabel;

    public BoardInfoPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        scoreLabel = new JLabel("Score: 0");
        timeLabel = new JLabel("Time: 0");

        add(scoreLabel);
        add(Box.createHorizontalStrut(20));
        add(timeLabel);
    }

    public void updateScore(int score) {
        SwingUtilities.invokeLater(() -> scoreLabel.setText("Score: " + score));
    }

    public void updateTime(int time) {
        System.out.println(time);
        SwingUtilities.invokeLater(() -> timeLabel.setText("Time: " + time));
    }
}
