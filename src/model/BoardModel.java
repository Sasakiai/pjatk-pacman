package model;

import enums.TileType;

import javax.swing.table.AbstractTableModel;

public class BoardModel extends AbstractTableModel {
    private final int rows;
    private final int cols;
    private final TileType[][] board;

    public BoardModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new TileType[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                board[i][j] = TileType.EMPTY;
            }
        }
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

    public void setTileValue(int rowIndex, int colIndex, TileType value) {
        board[rowIndex][colIndex] = value;
        fireTableCellUpdated(rowIndex, colIndex);
    }
}
