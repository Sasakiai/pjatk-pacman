package controller;

import model.AppModel;
import view.MenuView;

public class MainController {
    private final AppModel model;
    private final MenuView menuView;
    private final MenuViewController menuController;

    public MainController() {
        this.model = new AppModel();
        this.menuView = new MenuView();
        this.menuController = new MenuViewController(model, menuView, this);
    }

    public void startGame() {
        new GameController();
    }
}
