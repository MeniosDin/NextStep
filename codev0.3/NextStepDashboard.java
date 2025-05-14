import javax.swing.*;
import java.awt.*;

public class NextStepDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    public NextStepDashboard() {
        setTitle("NextStep Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(230, 230, 230));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        ImageIcon logoIcon = new ImageIcon("/Users/chris/Desktop/nextstepsourcecode/logo.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        sidebar.add(logoLabel);

        sidebar.add(createMenuButton("Home", "Home"));
        sidebar.add(createMenuButton("Search", "Search"));
        sidebar.add(createMenuButton("Notifications", "Notifications"));
        sidebar.add(createMenuButton("My Account", "Account"));
        sidebar.add(createMenuButton("Favourites", "Favourites"));
        sidebar.add(createMenuButton("My Applications", "Applications"));
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createMenuButton("FAQ's", "FAQ"));
        sidebar.add(createMenuButton("Contact us", "Contact"));

        add(sidebar, BorderLayout.WEST);

        
        




        // Main content with CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        add(mainContentPanel, BorderLayout.CENTER);

        mainContentPanel.add(new HomePanel(), "Home");
        mainContentPanel.add(new SearchPanel(), "Search");
        mainContentPanel.add(new NotificationsPanel(), "Notifications");
    }

    private JButton createMenuButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(230, 230, 230));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addActionListener(e -> cardLayout.show(mainContentPanel, cardName));
        return button;
    }
}