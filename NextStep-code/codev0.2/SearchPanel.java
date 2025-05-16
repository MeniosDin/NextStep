import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JScrollPane {

    public SearchPanel() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(1000, 700));
        panel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
        searchLabel.setForeground(new Color(0, 128, 128));
        searchLabel.setBounds(40, 30, 300, 30);
        panel.add(searchLabel);

        JTextField searchField = new JTextField("Try searching for something...");
        searchField.setBounds(40, 80, 600, 30);
        panel.add(searchField);

        JLabel historyLabel = new JLabel("History");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        historyLabel.setForeground(Color.GRAY);
        historyLabel.setBounds(40, 130, 200, 25);
        panel.add(historyLabel);

        String[] historyItems = {
            "Μεταπτυχιακό στην Αθήνα",
            "Πρακτική στην Πάτρα",
            "Μεταπτυχιακό με πτυχίο απο ceid"
        };

        int yOffset = 170;
        for (String item : historyItems) {
            JPanel row = new JPanel(null);
            row.setBackground(Color.WHITE);
            row.setBounds(40, yOffset, 600, 30);

            JLabel termLabel = new JLabel(item);
            termLabel.setBounds(0, 0, 500, 30);

            JButton deleteButton = new JButton("X");
            deleteButton.setBounds(550, 0, 45, 30);
            deleteButton.setMargin(new Insets(2, 5, 2, 5));

            row.add(termLabel);
            row.add(deleteButton);
            panel.add(row);

            yOffset += 40;
        }

        JLabel showMore = new JLabel("Show more...");
        showMore.setForeground(Color.GRAY);
        showMore.setBounds(40, yOffset, 200, 20);
        panel.add(showMore);

        JLabel popularLabel = new JLabel("Popular with your degree");
        popularLabel.setFont(new Font("Arial", Font.BOLD, 16));
        popularLabel.setBounds(40, yOffset + 40, 300, 25);
        panel.add(popularLabel);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        cardsPanel.setBounds(40, yOffset + 80, 900, 220);
        cardsPanel.setBackground(Color.WHITE);

        for (int i = 0; i < 4; i++) {
            cardsPanel.add(new HomePanel().createCard(i));
        }

        panel.add(cardsPanel);
        

        setViewportView(panel);
    }
}
