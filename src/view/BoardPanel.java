package view;

import controller.GameController;
import enums.Direction;
import enums.TileType;
import model.BoardModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoardPanel extends JPanel implements KeyListener {
    public JTable board;
    private final GameController gameController;
    private BufferedImage pacmanUp;
    private BufferedImage pacmanDown;
    private BufferedImage pacmanLeft;
    private BufferedImage pacmanRight;
    private BufferedImage ghostImage;

    public BoardPanel(BoardModel model, GameController gameController) {
        this.gameController = gameController;
        setBackground(Color.BLACK);

        try {pacmanUp = ImageIO.read(getClass().getResource("/pacmanUp.png"));} catch (IOException e) {}
        try {pacmanDown = ImageIO.read(getClass().getResource("/pacmanDown.png"));} catch (IOException e) {}
        try {pacmanRight = ImageIO.read(getClass().getResource("/pacmanRight.png"));} catch (IOException e) {}
        try {pacmanLeft = ImageIO.read(getClass().getResource("/pacmanLeft.png"));} catch (IOException e) {}

        try {ghostImage = ImageIO.read(getClass().getResource("/ghost.png"));} catch (IOException e) {}

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
        board.setBackground(Color.BLACK);

        // sizing settings
        int size = calculateRowColSize();
        board.setRowHeight(size);
        for (int i = 0; i < board.getColumnCount(); i++) {
            TableColumn col = board.getColumnModel().getColumn(i);

            col.setMinWidth(size);
            col.setMaxWidth(size);
            col.setPreferredWidth(size);
        }

        add(board, BorderLayout.CENTER);
        setFocusable(true);
        addKeyListener(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                rescaleBoard();
            }
        });
    }

    private int calculateRowColSize() {
        return 24;
    }

    private void rescaleBoard() {
        gameController.rescaleBoard();
    }

    private class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel tile = new JPanel();
            tile.setOpaque(true);

            if (value instanceof TileType) {
                switch ((TileType) value) {
                    case EMPTY:
                        tile.setBackground(Color.BLACK);
                        break;
                    case DOT:
                        tile.setBackground(Color.BLACK);
                        tile.setLayout(new BorderLayout());
                        tile.add(new DotPanel(), BorderLayout.CENTER);
                        break;
                    case WALL:
                        tile.setBackground(gameController.getWallColor());
                        break;
                    case PLAYER:
                        tile.setBackground(Color.BLACK);
                        BufferedImage pacmanImage;

                        switch (gameController.getPacmanDirection()) {
                            case UP: pacmanImage = pacmanUp; break;
                            case DOWN: pacmanImage = pacmanDown; break;
                            case LEFT: pacmanImage = pacmanLeft; break;
                            default: pacmanImage = pacmanRight;
                        }

                        tile.setLayout(new BorderLayout());
                        Image scaled = pacmanImage.getScaledInstance(table.getRowHeight(), table.getRowHeight(), Image.SCALE_SMOOTH);
                        tile.add(new JLabel(new ImageIcon(scaled)), BorderLayout.CENTER);

                        break;
                    case PLAYER_HIT:
                        tile.setBackground(new Color(1f, 1f, 0f, 0.5f));
                        break;
                    case GHOST:
                        tile.setBackground(Color.BLACK);

                        if (ghostImage != null) {
                            tile.setLayout(new BorderLayout());
                            Image sscaled = ghostImage.getScaledInstance(table.getRowHeight(), table.getRowHeight(), Image.SCALE_SMOOTH);
                            tile.add(new JLabel(new ImageIcon(sscaled)), BorderLayout.CENTER);
                        }
                        break;
                    case POWERUP:
                        tile.setBackground(Color.BLACK);
                        tile.setLayout(new BorderLayout());
                        tile.add(new PowerUpPanel(), BorderLayout.CENTER);
                        break;
                    default:
                        tile.setBackground(Color.BLACK);
                }
            }

            return tile;
        }
    }

    private class DotPanel extends JLabel {
        public DotPanel() {
            setText("•");
            setForeground(Color.WHITE);
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private class PowerUpPanel extends JLabel {
        public PowerUpPanel() {
            setText("•");
            setForeground(Color.MAGENTA);
            setHorizontalAlignment(SwingConstants.CENTER);
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
