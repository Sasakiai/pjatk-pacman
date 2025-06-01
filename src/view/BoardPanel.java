package view;

import model.BoardModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoardPanel extends JPanel {
    public JTable board;

    public BoardPanel(BoardModel model) {
        board = new JTable(model);
        board.setRowHeight(32);
        board.setFocusable(false);
        board.setCellSelectionEnabled(false);
        board.setColumnSelectionAllowed(false);
        board.setDragEnabled(false);


        add(board);
    }

//    private class CellRenderer extends DefaultTableCellRenderer {
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//
//        }
//    }
}
