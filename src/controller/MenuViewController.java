package controller;

import model.AppModel;
import model.BoardModel;
import view.GameView;
import view.MenuView;

public class MenuViewController {
    private AppModel model;
    private MenuView view;

    private BoardModel boardModel;
    private GameView gameView;

    public MenuViewController(AppModel model, MenuView view) {
        this.model = model;
        this.view = view;
        this.boardModel = new BoardModel(20, 20);

        view.menuPanel.btnNewGame.addActionListener(e -> new GameView(boardModel));
        view.menuPanel.btnHighScores.addActionListener(e -> view.showCard("highscores"));
        view.menuPanel.btnExit.addActionListener(e -> System.exit(0));

        view.highScoresPanel.btnReturn.addActionListener(e -> view.showCard("menu"));
    }
}
