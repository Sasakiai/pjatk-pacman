package controller;

import enums.Direction;
import enums.TileType;
import model.BoardModel;
import model.HighScoreModel;
import threads.GhostThread;
import threads.PacmanThread;
import threads.TimerThread;
import view.GameOverView;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final BoardModel boardModel;
    private final HighScoreModel highScoreModel;
    private final GameView gameView;
    private final PacmanThread pacmanThread;
    private final TimerThread timerThread;
    private final List<GhostThread> ghostThreads = new ArrayList<GhostThread>();
    private final Object directionLock = new Object();
    private int score = 0;
    private int time = 0;
    private int lives = 3;
    private volatile boolean invincible = false;
    private final int invincibleTime = 2000;

    private final int ghostAmount = 2;

    public GameController(HighScoreModel highScoreModel) {
        this.highScoreModel = highScoreModel;
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


        this.createGhosts();
        this.gameView.boardPanel.requestFocusInWindow();
        this.pacmanThread.start();
        this.timerThread.start();
    }

    // player relatde
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

    // ghost related
    private void createGhosts() {
        for (int i = 0; i < ghostAmount; i++) {
            int[] emptyTileCoords = this.boardModel.getEmptyTileCoords();

            if (emptyTileCoords[0] != -1) {
                initGhost(emptyTileCoords[0], emptyTileCoords[1]);
            }
        }
    }

    private void initGhost(int row, int col) {
        TileType previousTile = boardModel.getTile(row, col);

        boardModel.setTile(row, col, TileType.GHOST);

        GhostThread ghost = new GhostThread(boardModel, row, col, previousTile, this);
        ghostThreads.add(ghost);
        ghost.start();
    }

    public synchronized void playerHit() {
        if (invincible) {
            return;
        }

        lives--;
        gameView.boardInfoPanel.updateLives(lives);

        if (lives <= 0) {
            endGame();
            return;
        }

        invincible = true;
        pacmanThread.setCanMove(false);
        pacmanThread.setDirection(Direction.NONE);

        new Thread(() -> {
            try {
                Thread.sleep(invincibleTime);
            } catch (InterruptedException e) {

            }

            invincible = false;
            pacmanThread.setCanMove(true);
        }).start();
    }

    // othersss
    private void endGame() {
        pacmanThread.stopThread();
        timerThread.stopThread();

        for (GhostThread g : ghostThreads) {
            g.stopThread();
        }

        gameView.dispose();
        new GameOverView(score, highScoreModel);
    }

    public void rescaleBoard() {
        gameView.rescaleBoard();
    }
}
