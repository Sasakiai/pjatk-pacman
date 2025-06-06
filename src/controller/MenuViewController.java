package controller;

import model.AppModel;
import model.BoardModel;
import model.HighScoreModel;
import view.GameView;
import view.MenuView;

public class MenuViewController {
    private AppModel model;
    private HighScoreModel highScoreModel;
    private MenuView view;
    private final MainController mainController;

    public MenuViewController(AppModel model, HighScoreModel highScoreModel, MenuView view, MainController mainController) {
        this.model = model;
        this.highScoreModel = highScoreModel;
        this.view = view;
        this.mainController = mainController;

        view.menuPanel.btnNewGame.addActionListener(e -> mainController.startGame());
        view.menuPanel.btnHighScores.addActionListener(e -> {
            view.highScoresPanel.updateScores(highScoreModel.getHighScores());
            view.showCard("highscores");
        });
        view.menuPanel.btnExit.addActionListener(e -> System.exit(0));

        view.highScoresPanel.btnReturn.addActionListener(e -> view.showCard("menu"));
    }
}
