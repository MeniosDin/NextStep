import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewPanel extends JPanel {

    private JTextArea commentArea;
    private JComboBox<Integer> ratingCombo;
    private JPanel reviewsDisplayPanel;

    private List<String> reviews = new ArrayList<>();
    private List<Program> allPrograms;
    private JLabel selectedProgramLabel;

    public ReviewPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initPrograms(); // initialize mock programs

        // Αναζήτηση και επιλογή προγράμματος
        JPanel searchPanel = new JPanel(null);
        searchPanel.setPreferredSize(new Dimension(700, 150));
        searchPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField();
        searchField.setBounds(20, 10, 300, 30);
        searchPanel.add(searchField);

        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Όλα", "Μεταπτυχιακό", "Πρακτική"});
        typeCombo.setBounds(340, 10, 120, 30);
        searchPanel.add(typeCombo);

        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.setBounds(480, 10, 120, 30);
        searchPanel.add(searchButton);

        JPanel resultsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        resultsPanel.setBounds(20, 50, 640, 80);
        resultsPanel.setBackground(Color.WHITE);
        searchPanel.add(resultsPanel);

        selectedProgramLabel = new JLabel("Επιλεγμένο πρόγραμμα: Κανένα");
        selectedProgramLabel.setBounds(20, 135, 640, 20);
        selectedProgramLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        searchPanel.add(selectedProgramLabel);

        searchButton.addActionListener(e -> {
            resultsPanel.removeAll();
            String query = searchField.getText().trim().toLowerCase();
            String type = (String) typeCombo.getSelectedItem();

            for (Program p : allPrograms) {
                boolean matchText = p.getTitle().toLowerCase().contains(query);
                boolean matchType = type.equals("Όλα") || p.getType().equals(type);
                if (matchText && matchType) {
                    JButton resultBtn = new JButton("<html><center>" + p.getTitle() + "<br/>" + p.getLocation() + "</center></html>");
                    resultBtn.setPreferredSize(new Dimension(200, 60));
                    resultBtn.setBackground(Color.LIGHT_GRAY);
                    resultBtn.addActionListener(ev -> {
                        AppContext.selectedProgram = p;
                        selectedProgramLabel.setText("Επιλεγμένο πρόγραμμα: " + p.getTitle());
                        JOptionPane.showMessageDialog(this, "Επιλέχθηκε: " + p.getTitle(), "Πρόγραμμα Επιλεγμένο", JOptionPane.INFORMATION_MESSAGE);
                    });
                    resultsPanel.add(resultBtn);
                }
            }

            resultsPanel.revalidate();
            resultsPanel.repaint();
        });

        add(searchPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JLabel title = new JLabel("Αξιολόγηση Προγράμματος", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(title);

        formPanel.add(Box.createVerticalStrut(20));

        JLabel rateLabel = new JLabel("Βαθμολογία (1-5):");
        ratingCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingCombo.setMaximumSize(new Dimension(100, 30));

        JLabel commentLabel = new JLabel("Σχόλια (προαιρετικά, έως 1000 χαρακτήρες):");
        commentArea = new JTextArea(5, 50);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(commentArea);

        JButton submitButton = new JButton("Υποβολή Κριτικής");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> handleSubmit());

        formPanel.add(rateLabel);
        formPanel.add(ratingCombo);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(commentLabel);
        formPanel.add(scrollPane);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);

        reviewsDisplayPanel = new JPanel();
        reviewsDisplayPanel.setLayout(new BoxLayout(reviewsDisplayPanel, BoxLayout.Y_AXIS));
        reviewsDisplayPanel.setBorder(BorderFactory.createTitledBorder("Κριτικές Χρηστών"));
        reviewsDisplayPanel.setBackground(Color.WHITE);

        JScrollPane reviewsScroll = new JScrollPane(reviewsDisplayPanel);
        reviewsScroll.setPreferredSize(new Dimension(700, 200));
        add(reviewsScroll, BorderLayout.SOUTH);
    }

    private void handleSubmit() {
        if (AppContext.selectedProgram == null) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε πρόγραμμα από την αναζήτηση!", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int rating = (Integer) ratingCombo.getSelectedItem();
        String comment = commentArea.getText().trim();

        if (comment.length() > 1000) {
            JOptionPane.showMessageDialog(this, "Το σχόλιο υπερβαίνει τους 1000 χαρακτήρες!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String programTitle = AppContext.selectedProgram.getTitle();
        String review = "📘 " + programTitle + " — " + "★".repeat(rating) + " " + (comment.isEmpty() ? "(χωρίς σχόλιο)" : comment);
        reviews.add(0, review);
        refreshReviews();

        ratingCombo.setSelectedIndex(0);
        commentArea.setText("");
        JOptionPane.showMessageDialog(this, "Η κριτική υποβλήθηκε με επιτυχία!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshReviews() {
        reviewsDisplayPanel.removeAll();
        for (String review : reviews) {
            JLabel label = new JLabel("<html><p style='width:600px'>" + review + "</p></html>");
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            reviewsDisplayPanel.add(label);
        }
        reviewsDisplayPanel.revalidate();
        reviewsDisplayPanel.repaint();
    }

    private void initPrograms() {
        allPrograms = new ArrayList<>();
        allPrograms.add(new Program(1, "Μεταπτυχιακό στην διαχείρηση του AI", "Αθήνα", "2 έτη", 2000, 85, "Μεταπτυχιακό"));
        allPrograms.add(new Program(2, "Πρακτική σε Software Development", "Πάτρα", "3 μήνες", 0, 80, "Πρακτική"));
        allPrograms.add(new Program(3, "Μεταπτυχιακό στην Ιατρική", "Θεσσαλονίκη", "1.5 έτος", 1500, 78, "Μεταπτυχιακό"));
        allPrograms.add(new Program(4, "Πρακτική στο Marketing", "Ηράκλειο", "6 μήνες", 700, 70, "Πρακτική"));
    }
}
