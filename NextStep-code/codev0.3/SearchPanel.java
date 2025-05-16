import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JScrollPane {

    private JPanel contentPanel;
    private int yOffset;

    public SearchPanel() {
        contentPanel = new JPanel(null);
        contentPanel.setPreferredSize(new Dimension(1000, 700));
        contentPanel.setBackground(Color.WHITE);

        yOffset = 30;
        addSearchTitle();
        addSearchField();
        addHistorySection();
        addPopularSection();

        setViewportView(contentPanel);
    }

    private void addSearchTitle() {
        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
        searchLabel.setForeground(new Color(0, 128, 128));
        searchLabel.setBounds(40, yOffset, 300, 30);
        contentPanel.add(searchLabel);
        yOffset += 50;
    }

    private void addSearchField() {
        JTextField searchField = new JTextField("Try searching for something...");
        searchField.setBounds(40, yOffset, 600, 30);
        contentPanel.add(searchField);
        yOffset += 50;
    }

    private void addHistorySection() {
        JLabel historyLabel = new JLabel("History");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        historyLabel.setForeground(Color.GRAY);
        historyLabel.setBounds(40, yOffset, 200, 25);
        contentPanel.add(historyLabel);
        yOffset += 40;

        String[] historyItems = {
            "Μεταπτυχιακό στην Αθήνα",
            "Πρακτική στην Πάτρα",
            "Μεταπτυχιακό με πτυχίο απο ceid"
        };

        for (String item : historyItems) {
            JPanel row = createHistoryRow(item);
            row.setBounds(40, yOffset, 600, 30);
            contentPanel.add(row);
            yOffset += 40;
        }

        JLabel showMore = new JLabel("Show more...");
        showMore.setForeground(Color.GRAY);
        showMore.setBounds(40, yOffset, 200, 20);
        contentPanel.add(showMore);
        yOffset += 40;
    }

    private JPanel createHistoryRow(String term) {
        JPanel row = new JPanel(null);
        row.setBackground(Color.WHITE);
        row.setSize(600, 30);

        JLabel termLabel = new JLabel(term);
        termLabel.setBounds(0, 0, 500, 30);

        JButton deleteButton = new JButton("X");
        deleteButton.setBounds(550, 0, 45, 30);
        deleteButton.setMargin(new Insets(2, 5, 2, 5));
        deleteButton.addActionListener(e -> row.setVisible(false));

        row.add(termLabel);
        row.add(deleteButton);

        return row;
    }

    private void addPopularSection() {
        JLabel popularLabel = new JLabel("Popular with your degree");
        popularLabel.setFont(new Font("Arial", Font.BOLD, 16));
        popularLabel.setBounds(40, yOffset, 300, 25);
        contentPanel.add(popularLabel);
        yOffset += 40;

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        cardsPanel.setBounds(40, yOffset, 900, 220);
        cardsPanel.setBackground(Color.WHITE);

        for (int i = 0; i < 4; i++) {
            cardsPanel.add(new HomePanel().createCard(i));
        }

        contentPanel.add(cardsPanel);
    }
}
