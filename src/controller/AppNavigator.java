package controller;

import java.awt.*;

public class AppNavigator {
    public static void returnToMainMenu() {
        GameController.stopGame();

        for (Frame frame : Frame.getFrames()) {
            frame.dispose();
        }

        new MainController();
    }
}
