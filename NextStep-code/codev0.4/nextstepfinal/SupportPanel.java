import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SupportPanel extends JPanel {

    private JTextField subjectField;
    private JTextArea messageArea;
    private File attachedFile = null;

    private String userName = "Μαρία Παπαδοπούλου";
    private String userEmail = "maria@example.com";

    public SupportPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Επικοινωνία", createContactForm());
        tabs.add("Συχνές Ερωτήσεις", createFAQPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createContactForm() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel info = new JLabel("Στοιχεία Χρήστη: " + userName + " | " + userEmail);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        form.add(info);
        form.add(Box.createVerticalStrut(20));

        subjectField = new JTextField();
        subjectField.setMaximumSize(new Dimension(400, 30));
        form.add(new JLabel("Θέμα:"));
        form.add(subjectField);
        form.add(Box.createVerticalStrut(10));

        messageArea = new JTextArea(5, 50);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(messageArea);
        form.add(new JLabel("Μήνυμα:"));
        form.add(scroll);
        form.add(Box.createVerticalStrut(10));

        JButton attachBtn = new JButton("Επισύναψη αρχείου");
        attachBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                attachedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Επισυνάφθηκε: " + attachedFile.getName());
            }
        });
        form.add(attachBtn);
        form.add(Box.createVerticalStrut(10));

        JButton sendBtn = new JButton("Αποστολή");
        sendBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendBtn.addActionListener(e -> handleSubmit());
        form.add(sendBtn);

        return form;
    }

    private void handleSubmit() {
        if (subjectField.getText().trim().isEmpty() || messageArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε όλα τα απαιτούμενα πεδία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Το μήνυμά σας καταχωρήθηκε επιτυχώς!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        subjectField.setText("");
        messageArea.setText("");
        attachedFile = null;
    }

   private JScrollPane createFAQPanel() {
    JPanel faqPanel = new JPanel();
    faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
    faqPanel.setBackground(Color.WHITE);
    faqPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    JLabel titleLabel = new JLabel("Frequently Asked Questions");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
    titleLabel.setForeground(new Color(0, 128, 128));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    faqPanel.add(titleLabel);
    faqPanel.add(Box.createVerticalStrut(20));

    String[][] qna = {
        {"Πώς επαληθεύω τον λογαριασμό μου;", 
         "Για να επαληθεύσεις τον λογαριασμό σου, χρειάζεται να συνδεθείς με τον πανεπιστημιακό σου λογαριασμό μέσω της πλατφόρμας..."},
        {"Πώς μπορώ να βρω προγράμματα που ταιριάζουν στο πτυχίο μου;", 
         "Μπορείς να χρησιμοποιήσεις τα φίλτρα στην αναζήτηση για να επιλέξεις πρόγραμμα που ταιριάζει στο πτυχίο σου."},
        {"Είναι όλα τα προγράμματα στην πλατφόρμα επίσημα και αναγνωρισμένα;", 
         "Ναι, η πλατφόρμα περιλαμβάνει επίσημα και αναγνωρισμένα μεταπτυχιακά προγράμματα."},
        {"Πώς μπορώ να αποθηκεύσω ή να παρακολουθώ προγράμματα που με ενδιαφέρουν;", 
         "Μπορείς να προσθέσεις προγράμματα στα Αγαπημένα και να τα βλέπεις ανά πάσα στιγμή από τον λογαριασμό σου."},
        {"Μπορώ να κάνω αίτηση απευθείας από την εφαρμογή;", 
         "Ναι, η πλατφόρμα επιτρέπει άμεση υποβολή αίτησης στα περισσότερα προγράμματα."},
        {"Μπορώ να αλλάξω ή να ενημερώσω τα ακαδημαϊκά μου στοιχεία;", 
         "Ναι, μπορείς να πας στο προφίλ σου και να ενημερώσεις τα στοιχεία σου."}
    };

    for (String[] pair : qna) {
        faqPanel.add(createAccordionRow(pair[0], pair[1]));
        faqPanel.add(Box.createVerticalStrut(10));
    }

    return new JScrollPane(faqPanel);
}

   private JPanel createAccordionRow(String question, String answer) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

    JButton questionButton = new JButton(question + " \u25BC");
    questionButton.setFocusPainted(false);
    questionButton.setContentAreaFilled(false);
    questionButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    questionButton.setFont(new Font("Arial", Font.BOLD, 14));
    questionButton.setHorizontalAlignment(SwingConstants.LEFT);
    questionButton.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel answerLabel = new JLabel("<html><div style='width:600px;'>" + answer + "</div></html>");
    answerLabel.setFont(new Font("Arial", Font.PLAIN, 13));
    answerLabel.setForeground(Color.DARK_GRAY);
    answerLabel.setVisible(false);
    answerLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 5));
    answerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    questionButton.addActionListener(e -> {
        boolean isVisible = answerLabel.isVisible();
        answerLabel.setVisible(!isVisible);
        questionButton.setText(question + (isVisible ? " \u25BC" : " \u25B2")); // ▼ or ▲
        panel.revalidate();
        panel.repaint();
    });

    panel.add(questionButton);
    panel.add(answerLabel);
    return panel;
}



}