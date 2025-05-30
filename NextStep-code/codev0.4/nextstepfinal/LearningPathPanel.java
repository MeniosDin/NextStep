import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LearningPathPanel extends JPanel {

    private List<String> responses = new ArrayList<>();
    private JTextField answerField;
    private JPanel questionPanel;
    private int currentQuestion = 0;

    private String[] questions = {
        "1. Ποιο είναι το βασικό σου αντικείμενο σπουδών;",
        "2. Ποιους τομείς θα ήθελες να εξερευνήσεις (π.χ. Data Science, Web Development);",
        "3. Έχεις προτιμήσεις σε πόλεις ή χώρες για πρακτική/μεταπτυχιακό;",
        "4. Ποιο είναι το επίπεδο γνώσεων σου σε αυτό το αντικείμενο;",
        "5. Ποιες είναι οι επαγγελματικές σου φιλοδοξίες;"
    };

    public LearningPathPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Δημιουργία Learning Path");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        add(title, BorderLayout.NORTH);

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        questionPanel.setBackground(Color.WHITE);

        add(questionPanel, BorderLayout.CENTER);
        showNextQuestion();
    }

    private void showNextQuestion() {
        questionPanel.removeAll();

        if (currentQuestion >= questions.length) {
            showSummary();
            return;
        }

        JLabel questionLabel = new JLabel(questions[currentQuestion]);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        answerField = new JTextField();
        answerField.setMaximumSize(new Dimension(400, 30));
        answerField.setFont(new Font("Arial", Font.PLAIN, 14));
        answerField.setToolTipText("Γράψε εδώ την απάντησή σου…");

        answerField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 128, 128), 1, true),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JButton nextButton = new JButton("Επόμενη Ερώτηση");
        nextButton.setBackground(new Color(0, 128, 128));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nextButton.addActionListener(e -> {
            String response = answerField.getText().trim();
            responses.add(response.isEmpty() ? "[Δεν απαντήθηκε]" : response);
            currentQuestion++;
            showNextQuestion();
        });

        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(answerField);
        questionPanel.add(Box.createVerticalStrut(15));
        questionPanel.add(nextButton);

        revalidate();
        repaint();
    }

    private void showSummary() {
        questionPanel.removeAll();

        JLabel thankYou = new JLabel("Ευχαριστούμε! Δημιουργούμε το κατάλληλο Learning Path με βάση τις απαντήσεις σου:");
        thankYou.setFont(new Font("Arial", Font.BOLD, 18));
        thankYou.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(new Color(245, 245, 245));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        for (int i = 0; i < questions.length; i++) {
            JLabel qLabel = new JLabel("<html><b>" + questions[i] + "</b><br>Α: " + responses.get(i) + "</html>");
            qLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            qLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            summaryPanel.add(qLabel);
        }

        summaryPanel.add(Box.createVerticalStrut(20));
        JLabel suggestionsTitle = new JLabel("Προτεινόμενα Μαθήματα / Υλικό:");
        suggestionsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        summaryPanel.add(suggestionsTitle);

        String[][] resources = {
            {"Εισαγωγή στο Machine Learning - Coursera", "https://www.coursera.org/learn/machine-learning"},
            {"CS50: Εισαγωγή στην Πληροφορική - edX", "https://www.edx.org/course/cs50s-introduction-to-computer-science"},
            {"Web Development Bootcamp - Udemy", "https://www.udemy.com/course/the-complete-web-development-bootcamp/"},
            {"Data to Insight - FutureLearn", "https://www.futurelearn.com/courses/data-to-insight"},
            {"Εισαγωγή στον Προγραμματισμό - Khan Academy", "https://www.khanacademy.org/computing/computer-programming"}
        };

        List<Integer> usedIndices = new ArrayList<>();
        while (usedIndices.size() < 3) {
            int idx = (int)(Math.random() * resources.length);
            if (!usedIndices.contains(idx)) {
                usedIndices.add(idx);
                String title = resources[idx][0];
                String url = resources[idx][1];

                JLabel linkLabel = new JLabel("<html><a href='#'>" + title + "</a></html>");
                linkLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                linkLabel.setForeground(new Color(0, 102, 204));

                linkLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new java.net.URI(url));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                summaryPanel.add(Box.createVerticalStrut(10));
                summaryPanel.add(linkLabel);
            }
        }

        List<Program> suggestedPrograms = Arrays.asList(
            new Program(1, "MSc in Data Science", "University of Edinburgh", "12 μήνες", 15000, 80, "Μεταπτυχιακό"),
            new Program(2, "MSc in Artificial Intelligence", "KU Leuven", "24 μήνες", 7000, 90, "Μεταπτυχιακό"),
            new Program(3, "MSc in Educational Technology", "UCL", "12 μήνες", 13500, 75, "Μεταπτυχιακό"),
            new Program(4, "MSc in Human-Computer Interaction", "University of Twente", "24 μήνες", 9800, 85, "Μεταπτυχιακό"),
            new Program(5, "MSc in Business Analytics", "Imperial College London", "12 μήνες", 28000, 88, "Μεταπτυχιακό")
        );

        JLabel masterLabel = new JLabel("Προτεινόμενα Μεταπτυχιακά:");
        masterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryPanel.add(masterLabel);

        for (Program p : suggestedPrograms) {
            String stars = "★".repeat(p.getStarRating()) + "☆".repeat(5 - p.getStarRating());
            String programText = String.format("<html><b>%s</b><br>%s<br>Διάρκεια: %s, Δίδακτρα: %d€<br>Ευκαιρίες: %s</html>",
                p.getTitle(), p.getLocation(), p.getDuration(), p.getTuition(), stars);
            JLabel programLabel = new JLabel(programText);
            programLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            programLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            summaryPanel.add(programLabel);
        }

        JScrollPane scrollPane = new JScrollPane(summaryPanel);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionPanel.add(thankYou);
        questionPanel.add(Box.createVerticalStrut(15));
        questionPanel.add(scrollPane);

        revalidate();
        repaint();
    }
}
