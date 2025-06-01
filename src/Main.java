import controller.ViewController;
import model.AppModel;
import view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        // ctrl+shift+q closing the app
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            }

            return false;
        });


        javax.swing.SwingUtilities.invokeLater(() -> {
            AppModel model = new AppModel();
            MainView view = new MainView();
            new ViewController(model, view);
        });
    }
}