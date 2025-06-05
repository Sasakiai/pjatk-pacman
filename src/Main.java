import controller.MainController;
import controller.MenuViewController;
import model.AppModel;
import view.MenuView;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        // ctrl+shift+q closing the app
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                }
            }

            return false;
        });


        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainController();
        });
    }
}