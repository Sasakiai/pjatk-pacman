package view;

import javax.swing.*;
import java.awt.*;

public class BoardSizePanel extends JPanel {

    public BoardSizePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0 ,10);


    }
}
