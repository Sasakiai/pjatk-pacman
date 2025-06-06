package powerups;

import controller.GameController;

public class SpeedUpPowerUp implements PowerUp {
    @Override
    public String getName() {
        return "Speed Up";
    }

    @Override
    public String getDescription() {
        return "Pacman moves quicker";
    }

    @Override
    public void apply(GameController gameController) {

    }

    @Override
    public void remove(GameController gameController) {

    }
}
