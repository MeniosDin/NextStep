import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FavouritesPanel extends JScrollPane {

    private JPanel contentPanel;

    public FavouritesPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        setViewportView(contentPanel);

        refreshFavourites();
    }

    public void refreshFavourites() {
        contentPanel.removeAll();

        JLabel title = new JLabel("Αγαπημένα Προγράμματα");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 128, 128));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(20));

        List<Program> favourites = AppContext.favourites;

        if (favourites.isEmpty()) {
            JLabel empty = new JLabel("Δεν έχετε προσθέσει προγράμματα στα αγαπημένα.");
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(empty);
        } else {
            JPanel grid = new JPanel(new GridLayout(0, 4, 15, 15));
            grid.setBackground(Color.WHITE);
            for (Program p : favourites) {
                grid.add(createCard(p));
            }
            contentPanel.add(grid);
        }

        revalidate();
        repaint();
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

        JLabel desc = new JLabel("<html><center>"
                + "Τοποθεσία: " + program.getLocation() + "<br>"
                + "Διάρκεια: " + program.getDuration() + "<br>"
                + "Δίδακτρα: " + program.getTuition() + "€"
                + "</center></html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 11));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton removeBtn = new JButton("Κατάργηση");
        removeBtn.setBackground(Color.LIGHT_GRAY);
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.addActionListener(e -> {
            AppContext.favourites.remove(program);
            refreshFavourites();
        });

          JButton detailsBtn = new JButton("Λεπτομέρειες");
        detailsBtn.setBackground(new Color(0, 128, 128));
        detailsBtn.setForeground(Color.WHITE);
        detailsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsBtn.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(FavouritesPanel.this);
            JDialog dialog = new ProgramDetailsDialog(parentFrame, program);
            dialog.setVisible(true);
        });

        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(desc);
        card.add(Box.createVerticalStrut(5));
        card.add(detailsBtn);
        card.add(Box.createVerticalStrut(5));
        card.add(removeBtn);
        return card;
    }
}
