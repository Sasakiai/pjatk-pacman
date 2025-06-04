package view;

import controller.GameController;
import model.BoardModel;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    public JPanel cardPanel;
    public CardLayout cardLayout;

    public BoardSizePanel boardSizePanel;
    public BoardPanel boardPanel;
    private final GameController gameController;

    public GameView(BoardModel boardModel, GameController gameController) {
        this.gameController = gameController;

        setTitle("Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        boardPanel = new BoardPanel(boardModel, gameController);
        add(boardPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        boardPanel.requestFocusInWindow();
    }
}
