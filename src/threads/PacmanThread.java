package threads;

import enums.Direction;
import enums.TileType;
import model.BoardModel;

public class PacmanThread extends Thread {
    private final BoardModel boardModel;
    private int currentRow;
    private int currentCol;
    private Direction direction;
    private Object directionLock;
    private int moveInterval = 200;
    private boolean running = true;

    public PacmanThread(BoardModel boardModel, int startRow, int startCol, Object directionLock) {
        this.boardModel = boardModel;
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

    public void stopThread() {
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(moveInterval);
            } catch (InterruptedException ex) {
                if (!running) break;
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

            synchronized (boardModel) {
                boardModel.setTile(this.currentRow, this.currentCol, TileType.EMPTY);
                boardModel.setTile(newRow, newCol, TileType.PLAYER);

                this.currentRow = newRow;
                this.currentCol = newCol;
            }
        }


    }
}
