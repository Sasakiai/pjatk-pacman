package threads;

import controller.GameController;

public class TimerThread extends Thread {
    private final GameController gameController;
    private boolean running = true;
    private final int interval;

    public TimerThread(GameController gameController, int interval) {
        this.gameController = gameController;
        this.interval = interval;
    }

    public void stopThread() {
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                if (!running) {
                    break;
                }
            }

            gameController.incrementTime();
        }
    }
}
