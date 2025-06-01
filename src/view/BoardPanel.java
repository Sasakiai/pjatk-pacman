package view;

import enums.TileType;
import model.BoardModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class BoardPanel extends JPanel {
    public JTable board;

    public BoardPanel(BoardModel model) {
        board = new JTable(model);

        // disable any interaction
        board.setFocusable(false);
        board.setCellSelectionEnabled(false);
        board.setColumnSelectionAllowed(false);
        board.setDragEnabled(false);

        // display settings
        board.setRowHeight(32);
        board.setIntercellSpacing(new Dimension(0, 0));
        board.setTableHeader(null);
        board.setShowGrid(false);

        for (int i = 0; i < board.getColumnCount(); i++) {
            TableColumn col = board.getColumnModel().getColumn(i);

            col.setMinWidth(32);
            col.setMaxWidth(32);
            col.setPreferredWidth(32);
        }

        board.setDefaultRenderer(Object.class, new CellRenderer());

        add(board);
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
                    default:
                        tile.setBackground(Color.BLACK);
                }
            }

            return tile;
        }
    }
}
