package powerups;

import controller.GameController;

public class RainbowPowerUp implements PowerUp{
    @Override
    public String getName() {
        return "Rainbow";
    }

    @Override
    public String getDescription() {
        return "Walls change colors";
    }

    @Override
    public void apply(GameController gameController) {
        gameController.setRainbow(true);
    }

    @Override
    public void remove(GameController gameController) {
        gameController.setRainbow(false);
    }
}
