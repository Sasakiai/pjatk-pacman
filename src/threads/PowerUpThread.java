package threads;

import controller.GameController;

public class PowerUpThread extends Thread {
    private final GameController gameController;
    private boolean running = true;

    public PowerUpThread(GameController gameController) {
        this.gameController = gameController;
    }

    public void stopThread() {
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                if (!running) break;
            }

            gameController.trySpawnPowerUps();
        }
    }
}
