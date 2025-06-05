package view;

import model.HighScoreEntry;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;

public class HighScoresPanel extends JPanel {
    public JButton btnReturn;
    private JList<String> scoreList;
    private DefaultListModel<String> listModel;

    public HighScoresPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        listModel = new DefaultListModel<>();
        scoreList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnReturn = new JButton("Return to main menu");
        btnReturn.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(scrollPane);
        contentPanel.add(btnReturn);

        add(contentPanel, gbc);
    }

    public void updateScores(List<HighScoreEntry> scores) {
        listModel.clear();

        for (HighScoreEntry entry : scores) {
            listModel.addElement(entry.toString());
        }
    }
}
