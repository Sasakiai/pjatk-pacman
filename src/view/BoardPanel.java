package view;

import controller.GameController;
import enums.Direction;
import enums.TileType;
import model.BoardModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoardPanel extends JPanel implements KeyListener {
    public JTable board;
    private final GameController gameController;

    public BoardPanel(BoardModel model, GameController gameController) {
        this.gameController = gameController;
        board = new JTable(model);

        // disable any interaction
        board.setFocusable(false);
        board.setCellSelectionEnabled(false);
        board.setColumnSelectionAllowed(false);
        board.setDragEnabled(false);

        // display settings
        board.setDefaultRenderer(Object.class, new CellRenderer());
        board.setIntercellSpacing(new Dimension(0, 0));
        board.setTableHeader(null);
        board.setShowGrid(false);

        // sizing settings
        int size = calculateRowColSize();
        board.setRowHeight(size);
        for (int i = 0; i < board.getColumnCount(); i++) {
            TableColumn col = board.getColumnModel().getColumn(i);

            col.setMinWidth(size);
            col.setMaxWidth(size);
            col.setPreferredWidth(size);
        }

        add(board);
        setFocusable(true);
        addKeyListener(this);
    }

    private int calculateRowColSize() {
        return 16;
    }

    private class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel tile = new JPanel();
            tile.setOpaque(true);

            if (value instanceof TileType) {
                System.out.println(value);
                switch ((TileType) value) {
                    case EMPTY:
                        tile.setBackground(Color.BLACK);
                        break;
                    case WALL:
                        tile.setBackground(Color.BLUE);
                        break;
                    case PLAYER:
                        tile.setBackground(Color.PINK);
                        break;
                    default:
                        tile.setBackground(Color.BLACK);
                }
            }

            return tile;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                gameController.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                gameController.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                gameController.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                gameController.setDirection(Direction.RIGHT);
                break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
