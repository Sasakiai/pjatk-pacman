package controller;

import enums.Direction;
import enums.TileType;
import model.BoardModel;
import model.HighScoreModel;
import model.MazeProvider;
import threads.GhostThread;
import threads.PacmanThread;
import threads.TimerThread;
import view.GameOverView;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private static GameController currentGame;

    private final BoardModel boardModel;
    private final HighScoreModel highScoreModel;
    private final GameView gameView;
    private final PacmanThread pacmanThread;
    private final TimerThread timerThread;
    private final List<GhostThread> ghostThreads = new ArrayList<GhostThread>();
    private final Object directionLock = new Object();
    private int score;
    private int time;
    private int lives;
    private volatile boolean invincible = false;
    private final int invincibleTime = 2000;

    private final int ghostAmount = 2;

    public GameController(HighScoreModel highScoreModel) {
        this(highScoreModel, 0, 0, 3);
    }

    public GameController(HighScoreModel highScoreModel, int score, int time, int lives) {
        this.currentGame = this;
        this.highScoreModel = highScoreModel;
        this.score = score;
        this.time = time;
        this.lives = lives;
        this.boardModel = new BoardModel(MazeProvider.getRandomMaze());
        this.gameView = new GameView(boardModel, this);
        this.pacmanThread = new PacmanThread(
                boardModel,
                boardModel.getPacmanRow(),
                boardModel.getPacmanCol(),
                directionLock,
                this
        );
        this.timerThread = new TimerThread(this, 1000);


        this.createGhosts();
        this.gameView.boardInfoPanel.updateScore(score);
        this.gameView.boardInfoPanel.updateTime(time);
        this.gameView.boardInfoPanel.updateLives(lives);
        this.gameView.boardPanel.requestFocusInWindow();
        this.pacmanThread.start();
        this.timerThread.start();
    }

    // player relatde
    public void setDirection(Direction direction) {
        this.pacmanThread.setDirection(direction);
    }

    public void dotEaten() {
        if (!boardModel.hasDots()) {
            loadNextMaze();
        }
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
        List<int[]> starts = boardModel.getGhostStarts();

        for (int i = 0; i < ghostAmount && i < starts.size(); i++) {
            int[] pos = starts.get(i);
            initGhost(pos[0], pos[1]);
        }
    }

    private void initGhost(int row, int col) {
        GhostThread ghost = new GhostThread(boardModel, row, col, TileType.EMPTY, this);
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
    private void loadNextMaze() {
        gameView.dispose();
        stopThreads();

        new GameController(highScoreModel, score, time, lives);
    }

    public static void stopGame() {
        if (currentGame != null) {
            currentGame.stopThreads();
            currentGame.gameView.dispose();
            currentGame = null;
        }
    }

    private void endGame() {
        gameView.dispose();
        stopThreads();

        new GameOverView(score, highScoreModel);
    }

    private void stopThreads() {
        pacmanThread.stopThread();
        timerThread.stopThread();

        for (GhostThread g : ghostThreads) {
            g.stopThread();
        }
    }

    public void rescaleBoard() {
        gameView.rescaleBoard();
    }
}
