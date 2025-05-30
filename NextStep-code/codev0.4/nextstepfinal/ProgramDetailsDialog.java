import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramDetailsDialog extends JDialog {

    public ProgramDetailsDialog(JFrame parent, Program program) {
        super(parent, program.getTitle(), true);
        setSize(500, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(program.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        JLabel desc = new JLabel("<html><center>Αναλυτική περιγραφή:<br>" +
                "Τοποθεσία: " + program.getLocation() + "<br>" +
                "Διάρκεια: " + program.getDuration() + "<br>" +
                "Δίδακτρα: " + program.getTuition() + "€<br>" +
                "Κατηγορία: " + program.getType() + "</center></html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 13));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(desc);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton applyBtn = new JButton("Κάνε αίτηση τώρα!");
        applyBtn.setBackground(new Color(0, 170, 170));
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyBtn.addActionListener(e -> {
            AppContext.selectedProgram = program;
            JDialog dialog = new JDialog(this, "Αίτηση", true);
            dialog.setSize(700, 500);
            dialog.setLocationRelativeTo(this);
            dialog.add(new ApplicationFormPanel());
            dialog.setVisible(true);
        });
        mainPanel.add(applyBtn);
        mainPanel.add(Box.createVerticalStrut(10));

        JButton compareBtn = new JButton("Προσθήκη στη Σύγκριση");
        compareBtn.setBackground(new Color(100, 100, 255));
        compareBtn.setForeground(Color.WHITE);
        compareBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        compareBtn.addActionListener(e -> {
            if (!AppContext.compareList.contains(program)) {
                AppContext.compareList.add(program);
                JOptionPane.showMessageDialog(this, "Το πρόγραμμα προστέθηκε στη σύγκριση.");
            } else {
                JOptionPane.showMessageDialog(this, "Το πρόγραμμα υπάρχει ήδη στη σύγκριση.");
            }
        });
        mainPanel.add(compareBtn);
        mainPanel.add(Box.createVerticalStrut(10));

        JToggleButton favButton = new JToggleButton();
        boolean isFavourite = AppContext.favourites.contains(program);
        favButton.setText(isFavourite ? "♥ Αγαπημένο" : "♡ Προσθήκη στα αγαπημένα");
        favButton.setFocusPainted(false);
        favButton.setBackground(Color.WHITE);
        favButton.setForeground(Color.DARK_GRAY);
        favButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        favButton.addActionListener(e -> {
            if (favButton.isSelected()) {
                AppContext.favourites.add(program);
                favButton.setText("♥ Αγαπημένο");
            } else {
                AppContext.favourites.remove(program);
                favButton.setText("♡ Προσθήκη στα αγαπημένα");
            }
        });
        favButton.setSelected(isFavourite);
        mainPanel.add(favButton);
        mainPanel.add(Box.createVerticalStrut(10));

        JLabel reviewPrompt = new JLabel("Πες μας την γνώμη σου:");
        reviewPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(reviewPrompt);

        JButton leaveReviewBtn = new JButton("Γράψε Κριτική");
        leaveReviewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaveReviewBtn.addActionListener(e -> {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JPanel starsRow = new JPanel();
            JLabel rateLabel = new JLabel("Αστέρια (1-5): ");
            JComboBox<Integer> starBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
            starsRow.add(rateLabel);
            starsRow.add(starBox);

            JTextArea commentArea = new JTextArea(5, 30);
            commentArea.setLineWrap(true);
            commentArea.setWrapStyleWord(true);
            JScrollPane scroll = new JScrollPane(commentArea);

            panel.add(starsRow, BorderLayout.NORTH);
            panel.add(scroll, BorderLayout.CENTER);

            int result = JOptionPane.showConfirmDialog(this, panel, "Νέα Κριτική", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int rating = (Integer) starBox.getSelectedItem();
                String comment = commentArea.getText().trim();
                Review review = new Review(AppContext.reviews.size() + 1, AppContext.currentUser, program, rating, comment);
                AppContext.reviews.add(review);
                JOptionPane.showMessageDialog(this, "Η κριτική σας καταχωρήθηκε!");
                this.dispose();
                new ProgramDetailsDialog((JFrame) getParent(), program).setVisible(true); // reload dialog
            }
        });
        mainPanel.add(leaveReviewBtn);

        JPanel userReviewsPanel = new JPanel();
        userReviewsPanel.setLayout(new BoxLayout(userReviewsPanel, BoxLayout.Y_AXIS));
        userReviewsPanel.setBackground(Color.WHITE);
        userReviewsPanel.setBorder(BorderFactory.createTitledBorder("Κριτικές για αυτό το πρόγραμμα"));
        List<Review> programReviews = AppContext.reviews.stream()
                .filter(r -> r.getProgram().getProgramID() == program.getProgramID())
                .collect(Collectors.toList());

        if (programReviews.isEmpty()) {
            JLabel noReviews = new JLabel("Δεν υπάρχουν ακόμα κριτικές.");
            userReviewsPanel.add(noReviews);
        } else {
            for (Review r : programReviews) {
                String stars = "★".repeat(r.getRating()) + "☆".repeat(5 - r.getRating());
                JLabel reviewLabel = new JLabel("<html><b>" + r.getUser().getName() + ":</b> "
                        + stars + "<br><i>" + r.getComment() + "</i></html>");
                reviewLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userReviewsPanel.add(reviewLabel);
            }
        }

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(userReviewsPanel);

        add(mainPanel, BorderLayout.CENTER);
    }
}
