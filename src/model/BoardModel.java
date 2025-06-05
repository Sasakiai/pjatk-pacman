package model;

import enums.TileType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BoardModel extends AbstractTableModel {
    private final int rows;
    private final int cols;
    private final TileType[][] board;
    private int pacmanRow;
    private int pacmanCol;
    private final List<int[]> ghostStarts = new ArrayList<int[]>();

    public BoardModel(int[][] maze) {
        this.rows = maze.length;
        this.cols = maze[0].length;
        board = new TileType[rows][cols];

        initBoard(maze);
    }

    public void initBoard(int[][] maze) {
        ghostStarts.clear();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int mazeTileValue = maze[row][col];

                switch (mazeTileValue) {
                    case 1:
                        board[row][col] = TileType.WALL;
                        break;
                    case 2:
                        board[row][col] = TileType.PLAYER;
                        pacmanRow = row;
                        pacmanCol = col;
                        break;
                    case 3:
                        board[row][col] = TileType.GHOST;
                        ghostStarts.add(new int[]{row, col});
                        break;
                    default:
                        board[row][col] = TileType.DOT;
                }
            }
        }
    }

    public int getPacmanRow() {
        return this.pacmanRow;
    }

    public int getPacmanCol() {
        return this.pacmanCol;
    }

    public List<int[]> getGhostStarts() {
        return new ArrayList<int[]>(this.ghostStarts);
    }

    public boolean hasDots() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == TileType.DOT) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return cols;
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public synchronized TileType getTile(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void setTile(int rowIndex, int colIndex, TileType value) {
        board[rowIndex][colIndex] = value;
        fireTableCellUpdated(rowIndex, colIndex);
    }
}
