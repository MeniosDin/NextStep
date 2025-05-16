import javax.swing.*;
import java.awt.*;

public class HomePanel extends JScrollPane {

    public HomePanel() {
        // Δημιουργία panel για το HomePanel
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(1000, 700));
        panel.setBackground(Color.WHITE);

        // Ετικέτα καλωσορίσματος
        JLabel welcomeLabel = new JLabel("Welcome back, Maria!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(40, 20, 400, 30);
        panel.add(welcomeLabel);

        // Ετικέτα για την ενότητα "Home"
        JLabel homeLabel = new JLabel("Home");
        homeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        homeLabel.setForeground(new Color(0, 128, 128));
        homeLabel.setBounds(40, 60, 200, 30);
        panel.add(homeLabel);

        // Δημιουργία ενότητας "Recommended for you"
        JPanel recommended = createSectionPanel("Recommended for you");
        recommended.setBounds(40, 110, 900, 220);
        panel.add(recommended);

        // Δημιουργία ενότητας "Recently Viewed"
        JPanel recent = createSectionPanel("Recently Viewed");
        recent.setBounds(40, 350, 900, 220);
        panel.add(recent);

        // Δημιουργία κουμπιού ειδοποίησης με εικόνα
        ImageIcon bellIcon = new ImageIcon("/Users/chris/Desktop/nextstepsourcecode/noti.jpg");
        Image scaledBell = bellIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton bellButton = new JButton(new ImageIcon(scaledBell));

        // Ρύθμιση θέσης κουμπιού στο πάνω δεξί μέρος
        bellButton.setBounds(930, 20, 30, 30);
        bellButton.setContentAreaFilled(false); // Αφαίρεση φόντου
        bellButton.setBorderPainted(false);     // Αφαίρεση περιγράμματος
        bellButton.setFocusPainted(false);      // Αφαίρεση περιγράμματος όταν το κουμπί έχει focus
        bellButton.setToolTipText("Ειδοποιήσεις");

        // Ενέργεια όταν πατηθεί το κουμπί
        bellButton.addActionListener(e -> {
            Container parent = getParent();
            while (parent != null && !(parent instanceof JFrame)) {
                parent = parent.getParent();
            }
            if (parent instanceof JFrame) {
                Component[] comps = ((JFrame) parent).getContentPane().getComponents();
                for (Component comp : comps) {
                    if (comp instanceof JPanel) {
                        JPanel panel2 = (JPanel) comp;  // Κλασσική cast μορφή
                        if (panel2.getLayout() instanceof CardLayout) {
                            CardLayout layout = (CardLayout) panel2.getLayout();
                            layout.show(panel2, "Notifications");
                            break;
                        }
                    }
                }
            }
        });

        // Προσθήκη του κουμπιού στο panel
        panel.add(bellButton);

        // Ορισμός του panel ως περιεχόμενο του JScrollPane
        setViewportView(panel);
    }

    // Δημιουργία της ενότητας με τίτλο
    private JPanel createSectionPanel(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        section.add(label, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        cardsPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            cardsPanel.add(createCard(i));
        }

        section.add(cardsPanel, BorderLayout.CENTER);
        section.setPreferredSize(new Dimension(900, 220));
        return section;
    }

    // Δημιουργία κάρτας με πληροφορίες
    public JPanel createCard(int index) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));
        card.setPreferredSize(new Dimension(200, 200));
        card.setBackground(Color.WHITE);

        String title = index % 2 == 0 ? "Τίτλος Μεταπτυχιακού" : "Τίτλος Πρακτικής";
        String description = "<html><p style='width:180px'>Πληροφορίες όπως Τοποθεσία, Διάρκεια, Ώραριο, Μισθός/Αποζημίωση, Προσόντα, Οφέλη</p></html>";
        int rating = 3 + (index % 3);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JLabel ratingLabel = new JLabel("Βαθμολογία: " + "★".repeat(rating) + "☆".repeat(5 - rating));

        JButton applyButton = new JButton("Κάνε αίτηση τώρα!");
        applyButton.setBackground(new Color(0, 128, 128));
        applyButton.setForeground(Color.WHITE);

        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(descLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(applyButton);
        card.add(ratingLabel);

        return card;
    }
}
