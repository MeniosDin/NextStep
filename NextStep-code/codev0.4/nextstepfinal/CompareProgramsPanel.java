import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;

public class CompareProgramsPanel extends JPanel {

    private JPanel compareArea;

    public CompareProgramsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Σύγκριση Προγραμμάτων", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel topControls = new JPanel();
        topControls.setBackground(Color.WHITE);

        JButton compareButton = new JButton("Σύγκριση Τωρινών Επιλογών");
        compareButton.addActionListener(e -> comparePrograms());
        topControls.add(compareButton);
        add(topControls, BorderLayout.NORTH);

        compareArea = new JPanel(new GridLayout(1, 4, 10, 10));
        compareArea.setBackground(Color.WHITE);
        compareArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(new JScrollPane(compareArea), BorderLayout.CENTER);
    }

    public void refreshCompareList() {
        compareArea.removeAll();

        for (Program program : AppContext.compareList) {
            compareArea.add(createProgramCard(program));
        }

        compareArea.revalidate();
        compareArea.repaint();
    }

    private JPanel createProgramCard(Program program) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(220, 200));

        card.add(new JLabel("<html><b>" + program.getTitle() + "</b></html>"));
        card.add(new JLabel("Τοποθεσία: " + program.getLocation()));
        card.add(new JLabel("Διάρκεια: " + program.getDuration()));
        card.add(new JLabel("Δίδακτρα: " + program.getTuition() + "€"));
        card.add(new JLabel("Προοπτική: " + program.getOpportunityScore() + "%"));

        JButton removeBtn = new JButton("Κατάργηση");
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.addActionListener(e -> {
            AppContext.compareList.remove(program);
            refreshCompareList();
        });
        card.add(Box.createVerticalStrut(5));
        card.add(removeBtn);

        return card;
    }

    private void comparePrograms() {
        List<Program> selectedPrograms = AppContext.compareList;

        if (selectedPrograms.size() < 2) {
            JOptionPane.showMessageDialog(this, "Προσθέστε τουλάχιστον 2 προγράμματα για σύγκριση.", "Ελάχιστα απαιτούμενα", JOptionPane.WARNING_MESSAGE);
            return;
        }

        compareArea.removeAll();

        int bestTuition = selectedPrograms.stream().mapToInt(Program::getTuition).min().orElse(0);
        int bestOpportunity = selectedPrograms.stream().map(Program::getOpportunityScore).max(Comparator.naturalOrder()).orElse(0);

        for (Program p : selectedPrograms) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            card.setPreferredSize(new Dimension(220, 200));

            card.add(new JLabel("<html><b>" + p.getTitle() + "</b></html>"));
            card.add(new JLabel("Τοποθεσία: " + p.getLocation()));
            card.add(new JLabel("Διάρκεια: " + p.getDuration()));

            JLabel tuitionLabel = new JLabel("Δίδακτρα: " + p.getTuition() + "€");
            tuitionLabel.setForeground(p.getTuition() == bestTuition ? Color.GREEN.darker() : Color.RED);
            card.add(tuitionLabel);

            JLabel oppLabel = new JLabel("Προοπτική: " + p.getOpportunityScore() + "%");
            oppLabel.setForeground(p.getOpportunityScore() == bestOpportunity ? Color.GREEN.darker() : Color.RED);
            card.add(oppLabel);

            compareArea.add(card);
        }

        compareArea.revalidate();
        compareArea.repaint();
    }
}
