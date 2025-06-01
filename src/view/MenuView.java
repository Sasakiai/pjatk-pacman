package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuView extends JFrame {
    public JPanel cardPanel;
    public CardLayout cardLayout;

    public MenuPanel menuPanel;
    public HighScoresPanel highScoresPanel;

    public MenuView() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel();
        highScoresPanel = new HighScoresPanel();

        cardPanel.add(menuPanel, "menu");
        cardPanel.add(highScoresPanel, "highscores");

        add(cardPanel);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q')) {
                    System.exit(0);
                }
            }
        });
    }

    public void showCard(String name) {
        this.cardLayout.show(cardPanel, name);
    }
}
