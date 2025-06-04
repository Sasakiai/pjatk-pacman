package controller;

import enums.Direction;
import model.BoardModel;
import threads.PacmanThread;
import threads.TimerThread;
import view.GameView;

public class GameController {
    private final BoardModel boardModel;
    private final GameView gameView;
    private final PacmanThread pacmanThread;
    private final TimerThread timerThread;
    private final Object directionLock = new Object();
    private int score = 0;
    private int time = 0;

    public GameController() {
        this.boardModel = new BoardModel(20, 20);
        this.gameView = new GameView(boardModel, this);
        this.pacmanThread = new PacmanThread(
                boardModel,
                boardModel.getRowCount()/2,
                boardModel.getColumnCount()/2,
                directionLock,
                this
        );
        this.timerThread = new TimerThread(this, 1000);

        this.gameView.boardPanel.requestFocusInWindow();
        this.pacmanThread.start();
        this.timerThread.start();
    }

    public void setDirection(Direction direction) {
        this.pacmanThread.setDirection(direction);
    }

    public synchronized void addScore(int value) {
        this.score += value;
        this.gameView.boardInfoPanel.updateScore(score);
    }

    public synchronized void incrementTime() {
        this.time++;
        this.gameView.boardInfoPanel.updateTime(time);
    }

}
