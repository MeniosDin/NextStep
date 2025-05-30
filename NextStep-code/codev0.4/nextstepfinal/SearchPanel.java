import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchPanel extends JScrollPane {

     private JPanel contentPanel;
    private int yOffset;
    private JTextField searchField;
    private JComboBox<String> typeCombo;
    private JPanel resultsPanel;

    private List<Program> allPrograms;

    public SearchPanel() {
        contentPanel = new JPanel(null);
        contentPanel.setPreferredSize(new Dimension(1000, 1200));
        contentPanel.setBackground(Color.WHITE);

        yOffset = 30;

        initPrograms();
        addSearchTitle();
        addSearchField();
        addHistorySection();
        addPopularSection();
        addResultsPanel();

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
        searchField = new JTextField("");
        searchField.setBounds(40, yOffset, 400, 30);
        contentPanel.add(searchField);

        typeCombo = new JComboBox<>(new String[]{"Όλα", "Μεταπτυχιακό", "Πρακτική"});
        typeCombo.setBounds(460, yOffset, 120, 30);
        contentPanel.add(typeCombo);

        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.setBounds(600, yOffset, 120, 30);
        contentPanel.add(searchButton);

        searchButton.addActionListener(e -> performSearch());

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
            cardsPanel.add(createProgramCard(allPrograms.get(i % allPrograms.size())));
        }

        contentPanel.add(cardsPanel);
        yOffset += 240;
    }

    private void addResultsPanel() {
        JLabel resultsLabel = new JLabel("Αποτελέσματα Αναζήτησης:");
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultsLabel.setBounds(40, yOffset, 300, 25);
        contentPanel.add(resultsLabel);
        yOffset += 30;

        resultsPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        resultsPanel.setBounds(40, yOffset, 900, 400);
        resultsPanel.setBackground(Color.WHITE);
        contentPanel.add(resultsPanel);
    }

    private void performSearch() {
        String query = searchField.getText().trim().toLowerCase();
        String type = (String) typeCombo.getSelectedItem();

        resultsPanel.removeAll();

        for (Program p : allPrograms) {
            boolean matchText = p.getTitle().toLowerCase().contains(query);
            boolean matchType = type.equals("Όλα") || p.getType().equals(type);

            if (matchText && matchType) {
                resultsPanel.add(createProgramCard(p));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

   private JPanel createProgramCard(Program program ) {
    JPanel card = new JPanel();
    card.setPreferredSize(new Dimension(200, 180));
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));
    card.setBackground(Color.WHITE);
    
    int rating = program.getStarRating();
    
    JLabel title = new JLabel("<html><b>" + program.getTitle() + "</b></html>");
    title.setFont(new Font("Arial", Font.PLAIN, 13));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel desc = new JLabel("<html><center>"
            + "Τοποθεσία: " + program.getLocation() + "<br>"
            + "Διάρκεια: " + program.getDuration() + "<br>"
            + "Δίδακτρα: " + program.getTuition() + "€"
            + "</center></html>");
    desc.setFont(new Font("Arial", Font.PLAIN, 11));
    desc.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton applyBtn = new JButton("Κάνε αίτηση τώρα!");
    applyBtn.setBackground(new Color(0, 128, 128));
    applyBtn.setForeground(Color.WHITE);
    applyBtn.setFocusPainted(false);
    applyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

    applyBtn.addActionListener(e -> {
        AppContext.selectedProgram = program;
        JFrame frame = new JFrame("Αίτηση");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.add(new ApplicationFormPanel());
        frame.setVisible(true);
    });

    JPanel ratingPanel = new JPanel();
ratingPanel.setBackground(Color.WHITE);
ratingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

JLabel ratingText = new JLabel("Κριτικές: ");
ratingText.setFont(new Font("Arial", Font.PLAIN, 12));
ratingPanel.add(ratingText);

for (int i = 1; i <= 5; i++) {
    JLabel star = new JLabel(i <= rating ? "★" : "☆");
    star.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
    star.setForeground(Color.BLACK);
    ratingPanel.add(star);
}
   
    ratingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    card.add(Box.createVerticalStrut(8));
    card.add(title);
    card.add(Box.createVerticalStrut(5));
    card.add(desc);
    card.add(Box.createVerticalStrut(5));
    card.add(applyBtn);
    card.add(Box.createVerticalStrut(5));
    card.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(card);
        new ProgramDetailsDialog(topFrame, program).setVisible(true);
    }
  });
    card.add(ratingPanel);


    return card;
}


    private void initPrograms() {
        allPrograms = new ArrayList<>();
        allPrograms.add(new Program(1, "Μεταπτυχιακό στην Πληροφορική", "Αθήνα", "2 έτη", 3000, 85, "Μεταπτυχιακό"));
        allPrograms.add(new Program(2, "Πρακτική σε Software Startup", "Πάτρα", "3 μήνες", 0, 80, "Πρακτική"));
        allPrograms.add(new Program(3, "Μεταπτυχιακό στα Δίκτυα", "Θεσσαλονίκη", "1.5 έτος", 2500, 78, "Μεταπτυχιακό"));
        allPrograms.add(new Program(4, "Πρακτική στο εξωτερικό", "Ηράκλειο", "6 μήνες", 0, 70, "Πρακτική"));
    }
}
