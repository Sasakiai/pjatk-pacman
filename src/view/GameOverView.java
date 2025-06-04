package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameOverView extends JFrame {
    public JTextField nameField;
    public JButton submitBtn;

    public GameOverView() {
        setTitle("game over");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);

        JLabel inputLabel = new InputLabel();
        nameField = new NameField();
        submitBtn = new SubmitBtn();

        add(inputLabel, gbc);
        gbc.gridy++;

        add(nameField, gbc);
        gbc.gridy++;

        add(submitBtn, gbc);

        submitBtn.addActionListener(e -> {
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class InputLabel extends JLabel {
        public InputLabel() {
            setText("Enter your name");
        }
    }

    private class NameField extends JTextField {
        public NameField() {
            setColumns(15);
        }
    }

    private class SubmitBtn extends JButton {
        public SubmitBtn() {
            setText("Submit");
        }
    }
}
