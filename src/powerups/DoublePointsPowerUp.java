package powerups;

import controller.GameController;

public class DoublePointsPowerUp implements PowerUp {
    @Override
    public String getName() {
        return "Double Points";
    }

    @Override
    public String getDescription() {
        return "Dots give double points";
    }

    @Override
    public void apply(GameController gameController) {
        gameController.setScoreMult(2);
    }

    @Override
    public void remove(GameController gameController) {
        gameController.setScoreMult(1);
    }
}
