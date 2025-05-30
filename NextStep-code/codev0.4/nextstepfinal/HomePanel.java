import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomePanel extends JScrollPane {

    private List<Program> programs = List.of(
        new Program(1, "Μεταπτυχιακό στην Πληροφορική", "Αθήνα", "2 έτη", 3000, 85, "Μεταπτυχιακό"),
        new Program(2, "Πρακτική σε Software Startup", "Πάτρα", "3 μήνες", 0, 90, "Πρακτική"),
        new Program(3, "Μεταπτυχιακό στα Δίκτυα", "Θεσσαλονίκη", "1.5 έτος", 2500, 78, "Μεταπτυχιακό"),
        new Program(4, "Πρακτική στο εξωτερικό", "Βερολίνο", "6 μήνες", 0, 70, "Πρακτική")
    );

    public HomePanel() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(1000, 700));
        panel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome back, Maria!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(40, 20, 400, 30);
        panel.add(welcomeLabel);

        JLabel homeLabel = new JLabel("Home");
        homeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        homeLabel.setForeground(new Color(0, 128, 128));
        homeLabel.setBounds(40, 60, 200, 30);
        panel.add(homeLabel);

        JPanel recommended = createSectionPanel("Recommended for you");
        recommended.setBounds(40, 110, 900, 220);
        panel.add(recommended);

        JPanel recent = createSectionPanel("Recently Viewed");
        recent.setBounds(40, 350, 900, 220);
        panel.add(recent);

        ImageIcon bellIcon = new ImageIcon("C:/Users/menel/OneDrive/Υπολογιστής/Nextstep/bell.JPG");
        Image scaledBell = bellIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton bellButton = new JButton(new ImageIcon(scaledBell));
        bellButton.setBounds(930, 20, 30, 30);
        bellButton.setContentAreaFilled(false);
        bellButton.setBorderPainted(false);
        bellButton.setFocusPainted(false);
        bellButton.setToolTipText("Ειδοποιήσεις");

        bellButton.addActionListener(e -> {
            Container parent = getParent();
            while (parent != null && !(parent instanceof JFrame)) parent = parent.getParent();
            if (parent instanceof JFrame) {
                Component[] comps = ((JFrame) parent).getContentPane().getComponents();
                for (Component comp : comps) {
                    if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof CardLayout) {
                        ((CardLayout) ((JPanel) comp).getLayout()).show((JPanel) comp, "Notifications");
                        break;
                    }
                }
            }
        });

        panel.add(bellButton);
        setViewportView(panel);
    }

    private JPanel createSectionPanel(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        section.add(label, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        cardsPanel.setBackground(Color.WHITE);

        for (int i = 0; i < Math.min(programs.size(), 4); i++) {
            cardsPanel.add(createCard(programs.get(i)));
        }

        section.add(cardsPanel, BorderLayout.CENTER);
        section.setPreferredSize(new Dimension(900, 220));
        return section;
    }

    private JPanel createCard(Program program) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128)));
    card.setPreferredSize(new Dimension(200, 200));
    card.setBackground(Color.WHITE);

    JLabel titleLabel = new JLabel("<html><b>" + program.getTitle() + "</b></html>");
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel descLabel = new JLabel("<html><center>"
        + "Τοποθεσία: " + program.getLocation() + "<br>"
        + "Διάρκεια: " + program.getDuration() + "<br>"
        + "Δίδακτρα: " + program.getTuition() + "€"
        + "</center></html>");
    descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
    descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    int rating = Math.round(program.getOpportunityScore() / 20.0f);
    rating = Math.max(1, Math.min(rating, 5)); // 1 έως 5

    JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
    ratingPanel.setBackground(Color.WHITE);
    ratingPanel.add(new JLabel("Κριτικές: "));

    for (int i = 1; i <= 5; i++) {
        JLabel star = new JLabel(i <= rating ? "★" : "☆");
        star.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        star.setForeground(Color.BLACK);
        ratingPanel.add(star);
    }

    JButton applyButton = new JButton("Κάνε αίτηση τώρα!");
    applyButton.setBackground(new Color(0, 128, 128));
    applyButton.setForeground(Color.WHITE);
    applyButton.setFocusPainted(false);
    applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    applyButton.addActionListener(e -> {
        AppContext.selectedProgram = program;
        JFrame frame = new JFrame("Αίτηση");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.add(new ApplicationFormPanel());
        frame.setVisible(true);
    });
 
    card.add(Box.createVerticalStrut(5));


    card.add(Box.createVerticalStrut(10));
    card.add(titleLabel);
    card.add(Box.createVerticalStrut(5));
    card.add(descLabel);
    card.add(Box.createVerticalStrut(5));
    card.add(applyButton);
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

}
