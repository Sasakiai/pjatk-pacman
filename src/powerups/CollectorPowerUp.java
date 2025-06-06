package powerups;

import controller.GameController;

public class CollectorPowerUp implements PowerUp {
    @Override
    public String getName() {
        return "Collector";
    }

    @Override
    public String getDescription() {
        return "Collect nearby dots";
    }

    @Override
    public void apply(GameController gameController) {
        gameController.setCollector(true);
    }

    @Override
    public void remove(GameController gameController) {
        gameController.setCollector(false);
    }
}
