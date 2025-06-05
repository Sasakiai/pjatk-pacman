package controller;

import model.AppModel;
import model.HighScoreModel;
import view.MenuView;

public class MainController {
    private final AppModel model;
    private final HighScoreModel highScoreModel;
    private final MenuView menuView;
    private final MenuViewController menuController;

    public MainController() {
        this.model = new AppModel();
        this.highScoreModel = new HighScoreModel();
        this.menuView = new MenuView();
        this.menuController = new MenuViewController(model, highScoreModel, menuView, this);
    }

    public void startGame() {
        new GameController(highScoreModel);
    }
}
