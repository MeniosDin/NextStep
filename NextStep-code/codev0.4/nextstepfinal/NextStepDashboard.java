import javax.swing.*;
import java.awt.*;

public class NextStepDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private FavouritesPanel favouritesPanel;

    public NextStepDashboard() {
        setTitle("NextStep Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        AppContext.currentUser = new User(1, "Μαρία", "Παπαδοπούλου", "maria@example.com", "6900000000", "Ελλάδα", "Πληροφορικής Πανεπιστήμιο Πατρών", "Πληροφορικής", false);
        AppContext.selectedProgram = new Program(101, "Μεταπτυχιακό στην Πληροφορική", "Αθήνα", "2 έτη", 3000, 85, "Μεταπτυχιακό");

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(230, 230, 230));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        try {
            ImageIcon logoIcon = new ImageIcon("C:/Users/menel/OneDrive/Υπολογιστής/Nextstep/ne.PNG");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            sidebar.add(logoLabel);
        } catch (Exception e) {
            System.out.println("Δεν βρέθηκε το logo.");
        }

        sidebar.add(createMenuButton("Home", "Home"));
        sidebar.add(createMenuButton("Search", "Search"));
        sidebar.add(createMenuButton("Notifications", "Notifications"));
        sidebar.add(createMenuButton("My Account", "Account"));
        sidebar.add(createMenuButton("Reviews", "Review"));
        sidebar.add(createMenuButton("Compare", "Compare"));
        sidebar.add(createMenuButton("Apply", "Apply"));
        sidebar.add(createMenuButton("Favourites", "Favourites"));
        sidebar.add(createMenuButton("Learning Path", "LearningPath"));
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createMenuButton("FAQ / Contact", "Support"));

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        add(mainContentPanel, BorderLayout.CENTER);

        mainContentPanel.add(new HomePanel(), "Home");
        mainContentPanel.add(new SearchPanel(), "Search");
        mainContentPanel.add(new NotificationsPanel(), "Notifications");
        mainContentPanel.add(new UnifiedAccountPanel(), "Account");
        mainContentPanel.add(new ReviewPanel(), "Review");
        mainContentPanel.add(new CompareProgramsPanel(), "Compare");
        mainContentPanel.add(new ApplicationFormPanel(), "Apply");
        mainContentPanel.add(new SupportPanel(), "Support");
        mainContentPanel.add(new LearningPathPanel(), "LearningPath");

        favouritesPanel = new FavouritesPanel();
        mainContentPanel.add(favouritesPanel, "Favourites");

        cardLayout.show(mainContentPanel, "Home");
    }

    private JButton createMenuButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(230, 230, 230));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addActionListener(e -> {
            if (cardName.equals("Favourites")) {
                favouritesPanel.refreshFavourites();
            }
            cardLayout.show(mainContentPanel, cardName);
        });
        return button;
    }
}
