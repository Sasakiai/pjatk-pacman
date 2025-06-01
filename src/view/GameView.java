package view;

import javax.swing.*;

public class GameView extends JFrame {

    public GameView() {
        setTitle("Game");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel abc = new JLabel("chuj");
        add(abc);

        setVisible(true);
    }
}
