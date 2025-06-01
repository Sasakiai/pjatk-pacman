package view;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public JButton btnNewGame;
    public JButton btnHighScores;
    public JButton btnExit;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        btnNewGame = new JButton("New Game");
        btnHighScores = new JButton("High Scores");
        btnExit = new JButton("Exit");

        btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHighScores.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnHighScores);
        buttonPanel.add(btnExit);

        add(buttonPanel, gbc);
    }
}
