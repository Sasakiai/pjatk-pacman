package controller;

import model.AppModel;
import model.BoardModel;
import view.GameView;
import view.MenuView;

public class MenuViewController {
    private AppModel model;
    private MenuView view;
    private final MainController mainController;

    public MenuViewController(AppModel model, MenuView view, MainController mainController) {
        this.model = model;
        this.view = view;
        this.mainController = mainController;

        view.menuPanel.btnNewGame.addActionListener(e -> mainController.startGame());
        view.menuPanel.btnHighScores.addActionListener(e -> view.showCard("highscores"));
        view.menuPanel.btnExit.addActionListener(e -> System.exit(0));

        view.highScoresPanel.btnReturn.addActionListener(e -> view.showCard("menu"));
    }
}
