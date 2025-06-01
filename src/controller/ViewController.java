package controller;

import model.AppModel;
import view.GameView;
import view.MainView;

public class ViewController {
    private AppModel model;
    private MainView view;

    public ViewController(AppModel model, MainView view) {
        this.model = model;
        this.view = view;

        view.menuPanel.btnNewGame.addActionListener(e -> new GameView());
        view.menuPanel.btnHighScores.addActionListener(e -> view.showCard("highscores"));
        view.menuPanel.btnExit.addActionListener(e -> System.exit(0));

        view.highScoresPanel.btnReturn.addActionListener(e -> view.showCard("menu"));
    }
}
