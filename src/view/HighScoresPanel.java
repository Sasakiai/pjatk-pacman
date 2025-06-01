package view;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class HighScoresPanel extends JPanel {
    public JButton btnReturn;

    public HighScoresPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel x = new JLabel("pomocy");
        x.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnReturn = new JButton("Return to main menu");
        btnReturn.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(x);
        contentPanel.add(btnReturn);

        add(contentPanel, gbc);
    }
}
