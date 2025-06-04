package view;

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

    public BoardPanel(BoardModel model) {
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

    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
