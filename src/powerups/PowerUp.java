package powerups;

import controller.GameController;

public interface PowerUp {
    String getName();
    String getDescription();

    void apply(GameController gameController);
    void remove(GameController gameController);
}
