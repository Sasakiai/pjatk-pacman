package controller;

import enums.Direction;
import enums.TileType;
import model.BoardModel;
import model.HighScoreModel;
import model.MazeProvider;
import powerups.PowerUp;
import powerups.PowerUpFactory;
import threads.GhostThread;
import threads.PacmanThread;
import threads.PowerUpThread;
import threads.TimerThread;
import view.GameOverView;
import view.GameView;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameController {
    private static GameController currentGame;

    private final BoardModel boardModel;
    private final HighScoreModel highScoreModel;
    private final GameView gameView;
    private final PacmanThread pacmanThread;
    private final TimerThread timerThread;
    private final PowerUpThread powerUpThread;
    private final List<GhostThread> ghostThreads = new ArrayList<GhostThread>();
    private final Object directionLock = new Object();
    private int score;
    private int time;
    private int lives;
    private volatile boolean invincible = false;
    private final int invincibleTime = 2000;

    private final Map<Point, PowerUp> powerUps = new HashMap<Point, PowerUp>();
    private int scoreMult = 1;
    private boolean hunger = false;
    private boolean rainbow = false;
    private boolean collector = false;
    private boolean powerUpActive = false;

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
        this.powerUpThread = new PowerUpThread(this);


        this.createGhosts();
        this.gameView.boardInfoPanel.updateScore(score);
        this.gameView.boardInfoPanel.updateTime(time);
        this.gameView.boardInfoPanel.updateLives(lives);
        this.gameView.boardPanel.requestFocusInWindow();
        this.pacmanThread.start();
        this.timerThread.start();
        this.powerUpThread.start();
    }

    // player relatde
    public void setDirection(Direction direction) {
        this.pacmanThread.setDirection(direction);
    }

    public Direction getPacmanDirection() {
        return this.pacmanThread.getDirection();
    }

    public void dotEaten() {
        if (!boardModel.hasDots()) {
            loadNextMaze();
        }
    }

    public synchronized void addScore(int value) {
        this.score += value * scoreMult;
        this.gameView.boardInfoPanel.updateScore(score);
    }

    public synchronized void incrementTime() {
        this.time++;
        this.gameView.boardInfoPanel.updateTime(time);
    }

    // ghost related
    private void createGhosts() {
        List<int[]> starts = boardModel.getGhostStarts();

        for (int i = 0; i < starts.size(); i++) {
            int[] pos = starts.get(i);
            initGhost(pos[0], pos[1]);
        }
    }

    private void initGhost(int row, int col) {
        System.out.println("Ghost added");
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

    // powerup related
    public void trySpawnPowerUps() {
        Random r = new Random();

        for (GhostThread g : ghostThreads) {
            if (r.nextInt(100) < 25) {
                PowerUp p = PowerUpFactory.getRandomPowerUp();
                g.givePowerUp(p);
            }
        }
    }

    public synchronized void placePowerUp(int row, int col, PowerUp powerUp) {
        powerUps.put(new Point(row, col), powerUp);
        boardModel.setTile(row, col, TileType.POWERUP);
    }

    public synchronized void powerUpCollected(int row, int col) {
        if (powerUpActive) {
            return;
        }

        PowerUp powerUp = powerUps.remove(new Point(row, col));
        boardModel.setTile(row, col, TileType.EMPTY);

        if (powerUp != null) {
            powerUpActive = true;
            powerUp.apply(this);
            gameView.boardInfoPanel.setPowerUpText(powerUp.getName() + ": " + powerUp.getDescription());

            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }

                powerUpActive = false;
                powerUp.remove(this);
                gameView.boardInfoPanel.clearPowerUpText();
            }).start();
        }
    }

    public void ghostEatenAt(int row, int col) {
        GhostThread ghostEaten = null;

        for (GhostThread g : ghostThreads) {
            if (g.getCurrentRow() == row && g.getCurrentCol() == col) {
                ghostEaten = g;
                break;
            }
        }

        if (ghostEaten != null) {
            ghostEaten.stopThread();
            ghostThreads.remove(ghostEaten);
            boardModel.setTile(row, col, TileType.EMPTY);
        }
    }

    public void setScoreMult(int scoreMult) {
        this.scoreMult = scoreMult;
    }

    public void setHunger(boolean hunger) {
        this.hunger = hunger;
    }

    public void setSpeedMultiplier(double mult) {
        pacmanThread.setSpeedMultiplier(mult);
    }

    public boolean isHunger() {
        return this.hunger;
    }

    public void setCollector(boolean collector) {
        this.collector = collector;
    }

    public boolean isCollector() {
        return this.collector;
    }

    public boolean isPowerUpActive() {
        return this.powerUpActive;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }


    public Color getWallColor() {
        if (rainbow) {
            Random r = new Random();

            return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }

        return Color.BLUE;
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
        powerUpThread.stopThread();

        for (GhostThread g : ghostThreads) {
            g.stopThread();
        }
    }

    public void rescaleBoard() {
        gameView.rescaleBoard();
    }
}
