import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NotificationsPanel extends JPanel {

    private java.util.List<JLabel> notificationLabels = new ArrayList<>();

    public NotificationsPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 700));

        JLabel titleLabel = new JLabel("Ειδοποιήσεις");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 128, 128));
        titleLabel.setBounds(40, 30, 300, 30);
        add(titleLabel);

        String[] notifications = {
            "Η αίτηση σου για το μεταπτυχιακό έγινε δεκτή.",
            "Νέα πρακτική διαθέσιμη στην περιοχή σου.",
            "Το προφίλ σου ενημερώθηκε με επιτυχία."
        };

        int yOffset = 80;
        for (String msg : notifications) {
            JPanel row = createNotificationRow(msg, yOffset);
            add(row);
            yOffset += 50;
        }
    }

    private JPanel createNotificationRow(String message, int yOffset) {
        JPanel row = new JPanel(null);
        row.setBackground(Color.WHITE);
        row.setBounds(40, yOffset, 900, 40);

        JLabel msgLabel = new JLabel(message);
        msgLabel.setBounds(0, 0, 600, 30);
        msgLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        notificationLabels.add(msgLabel);

        JButton markReadButton = new JButton("✔");
        markReadButton.setBounds(620, 0, 50, 30);
        markReadButton.setToolTipText("Σήμανση ως αναγνωσμένο");
        markReadButton.addActionListener(e -> {
            msgLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            msgLabel.setForeground(Color.GRAY);
        });

        JButton deleteButton = new JButton("X");
        deleteButton.setBounds(680, 0, 50, 30);
        deleteButton.setToolTipText("Διαγραφή");
        deleteButton.addActionListener(e -> row.setVisible(false));

        row.add(msgLabel);
        row.add(markReadButton);
        row.add(deleteButton);

        return row;
    }
}
