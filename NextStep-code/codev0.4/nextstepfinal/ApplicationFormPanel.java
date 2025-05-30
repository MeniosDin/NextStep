import javax.swing.*;
import java.awt.*;

public class ApplicationFormPanel extends JPanel {

    private JTextArea motivationArea;

    public ApplicationFormPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        Program program = AppContext.selectedProgram;
        User user = AppContext.currentUser;

        if (program == null || user == null) {
            JLabel errorLabel = new JLabel("Δεν έχει επιλεγεί πρόγραμμα ή χρήστης.");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            errorLabel.setForeground(Color.RED);
            add(errorLabel, BorderLayout.CENTER);
            return;
        }

        JLabel title = new JLabel("Υποβολή Αίτησης για: " + program.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        formPanel.add(createReadOnlyField("Ονοματεπώνυμο", user.getName()));
        formPanel.add(createReadOnlyField("Email", user.getEmail()));
        formPanel.add(createReadOnlyField("Πτυχίο", user.getDegree() + " - " + user.getDepartment()));
        formPanel.add(Box.createVerticalStrut(10));

        JLabel motivationLabel = new JLabel("Κίνητρο/Μήνυμα:");
        motivationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        motivationArea = new JTextArea(5, 50);
        motivationArea.setLineWrap(true);
        motivationArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(motivationArea);

        formPanel.add(motivationLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(scroll);
        formPanel.add(Box.createVerticalStrut(15));

        JButton submitButton = new JButton("Υποβολή Αίτησης");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> handleSubmit());

        formPanel.add(submitButton);
        add(formPanel, BorderLayout.CENTER);
    }

    private JPanel createReadOnlyField(String label, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField field = new JTextField(value);
        field.setMaximumSize(new Dimension(400, 30));
        field.setEditable(false);
        field.setBackground(new Color(245, 245, 245));

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
        return panel;
    }

   private void handleSubmit() {
    String motivation = motivationArea.getText().trim();

    if (motivation.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε το κίνητρο σας.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Application application = new Application(AppContext.currentUser, AppContext.selectedProgram, motivation);
    AppContext.applications.add(application);

    JOptionPane.showMessageDialog(this, "Η αίτησή σας υποβλήθηκε με επιτυχία!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
    motivationArea.setText("");
}
}
