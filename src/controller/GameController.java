package controller;

import enums.Direction;
import model.BoardModel;
import threads.PacmanThread;
import view.GameView;

public class GameController {
    private final BoardModel boardModel;
    private final GameView gameView;
    private final PacmanThread pacmanThread;
    private final Object directionLock = new Object();

    public GameController() {
        this.boardModel = new BoardModel(20, 20);
        this.gameView = new GameView(boardModel, this);
        this.pacmanThread = new PacmanThread(
                boardModel,
                boardModel.getRowCount()/2,
                boardModel.getColumnCount()/2,
                directionLock
        );
        this.gameView.boardPanel.requestFocusInWindow();
        this.pacmanThread.start();
    }

    public void setDirection(Direction direction) {
        pacmanThread.setDirection(direction);
    }


}
