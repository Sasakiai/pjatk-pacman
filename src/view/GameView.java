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
    public BoardInfoPanel boardInfoPanel;
    private final GameController gameController;

    public GameView(BoardModel boardModel, GameController gameController) {
        this.gameController = gameController;

        setTitle("Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel(boardModel, gameController);
        boardInfoPanel = new BoardInfoPanel();

        add(boardInfoPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        boardPanel.requestFocusInWindow();
    }

    public void rescaleBoard() {
        pack();
    }
}
