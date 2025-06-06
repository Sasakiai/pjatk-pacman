package threads;

import controller.GameController;
import enums.Direction;
import enums.TileType;
import model.BoardModel;

public class PacmanThread extends Thread {
    private final BoardModel boardModel;
    private final GameController gameController;
    private int currentRow;
    private int currentCol;
    private Direction direction;
    private Object directionLock;
    private final int baseMoveInterval = 200;
    private int moveInterval = 200;
    private boolean running = true;
    private volatile boolean canMove = true;

    public PacmanThread(BoardModel boardModel, int startRow, int startCol, Object directionLock, GameController gameController) {
        this.boardModel = boardModel;
        this.gameController = gameController;
        this.currentRow = startRow;
        this.currentCol = startCol;
        this.direction = Direction.NONE;
        this.directionLock = directionLock;
    }

    public void setDirection(Direction direction) {
        synchronized (directionLock) {
            this.direction = direction;
        }
    }

    public Direction getDirection() {
        synchronized (directionLock) {
            return this.direction;
        }
    }

    public void stopThread() {
        running = false;
        interrupt();
    }

    public synchronized void setSpeedMultiplier(double multiplier) {
        moveInterval = (int) (baseMoveInterval * multiplier);
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;

        setSelfOpacity();
    }

    public synchronized void setSelfOpacity() {
        if (canMove) {
            boardModel.setTile(currentRow, currentCol, TileType.PLAYER);
        } else {
            boardModel.setTile(currentRow, currentCol, TileType.PLAYER_HIT);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(moveInterval);
            } catch (InterruptedException ex) {
                if (!running) break;
            }

            if (!canMove) {
                continue;
            }

            Direction directionCopy;

            synchronized (directionLock) {
                directionCopy = this.direction;
            }

            if (directionCopy == Direction.NONE) {
                continue;
            }

            int newRow = this.currentRow;
            int newCol = this.currentCol;

            switch (directionCopy) {
                case UP: newRow--; break;
                case DOWN: newRow++; break;
                case LEFT: newCol--; break;
                case RIGHT: newCol++; break;
            }

            //check if in granice plansdzy
            if (newRow < 0 || newRow >= boardModel.getRowCount() ||
                newCol < 0 || newCol >= boardModel.getColumnCount()) {
                continue;
            }

            TileType newTile;

            synchronized (boardModel) {
                newTile = boardModel.getTile(newRow, newCol);

                if (newTile == TileType.WALL) {
                    continue;
                }
            }

            if (newTile == TileType.GHOST) {
                if (gameController.isHunger()) {
                    gameController.ghostEatenAt(newRow, newCol);
                    newTile = TileType.EMPTY;
                } else {
                    gameController.playerHit();
                    continue;
                }
            }

            boolean collectedDot = false;

            if (newTile == TileType.DOT) {
                gameController.addScore(10);
                collectedDot = true;
            }

            if (newTile == TileType.POWERUP) {
                if (gameController.isPowerUpActive()) {
                    continue;
                } else {
                    gameController.powerUpCollected(newRow, newCol);
                }
            }

            synchronized (boardModel) {
                boardModel.setTile(this.currentRow, this.currentCol, TileType.EMPTY);
                boardModel.setTile(newRow, newCol, TileType.PLAYER);

                this.currentRow = newRow;
                this.currentCol = newCol;
            }

            if (collectedDot) {
                gameController.dotEaten();
            }

            if (gameController.isCollector()) {
                int rowBefore = this.currentRow;
                int rowAfter = this.currentRow;
                int colBefore = this.currentCol;
                int colAfter = this.currentCol;

                switch(directionCopy) {
                    case UP:
                        rowBefore++;
                        rowAfter--;
                        break;
                    case DOWN:
                        rowBefore--;
                        rowAfter++;
                    case LEFT:
                        colBefore++;
                        colAfter--;
                    case RIGHT:
                        colBefore--;
                        colAfter++;
                }

                synchronized (boardModel) {
                    if (rowAfter >= 0 && rowAfter < boardModel.getRowCount() && colAfter >= 0 && colAfter < boardModel.getColumnCount()) {
                        if (boardModel.getTile(rowAfter, rowBefore) == TileType.DOT) {
                            boardModel.setTile(rowAfter, colAfter, TileType.EMPTY);
                            gameController.addScore(10);
                            gameController.dotEaten();
                        }
                    }

                    if (rowBefore >= 0 && rowBefore < boardModel.getRowCount() && colBefore >= 0 && colBefore < boardModel.getColumnCount()) {
                        if (boardModel.getTile(rowBefore, colBefore) == TileType.DOT) {
                            boardModel.setTile(rowBefore, colBefore, TileType.EMPTY);
                            gameController.addScore(10);
                            gameController.dotEaten();
                        }
                    }
                }
            }
        }


    }
}
