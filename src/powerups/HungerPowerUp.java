package powerups;

import controller.GameController;

public class HungerPowerUp implements PowerUp {
    @Override
    public String getName() {
        return "Hunger";
    }

    @Override
    public String getDescription() {
        return "Pacman can eat ghosts";
    }

    @Override
    public void apply(GameController gameController) {
        gameController.setHunger(true);
    }

    @Override
    public void remove(GameController gameController) {
        gameController.setHunger(false);
    }
}
