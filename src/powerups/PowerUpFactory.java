package powerups;

import java.util.Random;

public class PowerUpFactory {
    private static final Random RANDOM = new Random();

    public static PowerUp getRandomPowerUp() {
        switch (RANDOM.nextInt(5)) {
            case 0:
                return new DoublePointsPowerUp();
            case 1:
                return new HungerPowerUp();
            case 2:
                return new SpeedUpPowerUp();
            case 3:
                return new RainbowPowerUp();
            default:
                return new CollectorPowerUp();
        }
    }
}
