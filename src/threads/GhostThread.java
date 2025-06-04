package threads;

import controller.GameController;
import enums.Direction;
import enums.TileType;
import model.BoardModel;

import java.util.Random;

public class GhostThread extends Thread {
    private final BoardModel boardModel;
    private final GameController gameController;
    private int currentRow;
    private int currentCol;
    private TileType previousTile = TileType.EMPTY;
    private final Random random = new Random();
    private int moveInterval = 400;
    private boolean running = true;

    public GhostThread(BoardModel boardModel, int startRow, int startCol, TileType previousTile, GameController gameController) {
        this.boardModel = boardModel;
        this.gameController = gameController;
        this.currentRow = startRow;
        this.currentCol = startCol;
        this.previousTile = previousTile;
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
            } catch (InterruptedException e) {
                if (!running) break;
            }


            Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
            Direction dir = directions[random.nextInt(directions.length)];
            int newRow = currentRow;
            int newCol = currentCol;

            switch (dir) {
                case UP: newRow--; break;
                case DOWN: newRow++; break;
                case LEFT: newCol--; break;
                case RIGHT: newCol++; break;
            }

            if (newRow < 0 || newRow >= boardModel.getRowCount() ||
                newCol < 0 || newCol >= boardModel.getColumnCount()) {
                continue;
            }

            TileType newTile;

            synchronized (boardModel) {
                newTile = boardModel.getTile(newRow, newCol);

                if (newTile == TileType.WALL || newTile == TileType.GHOST) {
                    continue;
                }

                if (newTile == TileType.PLAYER) {
                    gameController.playerHit();
                }

                boardModel.setTile(currentRow, currentCol, previousTile);
                previousTile = (newTile == TileType.DOT ? newTile : TileType.EMPTY);
                boardModel.setTile(newRow, newCol, TileType.GHOST);


                currentRow = newRow;
                currentCol = newCol;
            }
        }

    }
}
