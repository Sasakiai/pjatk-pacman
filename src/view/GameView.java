package view;

import model.BoardModel;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    public JPanel cardPanel;
    public CardLayout cardLayout;

    public BoardSizePanel boardSizePanel;
    public BoardPanel boardPanel;

    public GameView(BoardModel boardModel) {
        setTitle("Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        boardPanel = new BoardPanel(boardModel);
        add(boardPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
