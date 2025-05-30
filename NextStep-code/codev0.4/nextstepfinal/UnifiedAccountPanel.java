import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UnifiedAccountPanel extends JPanel {

    private Map<String, JTextField> profileFields = new HashMap<>();
    private JPanel verificationPanel;

    public UnifiedAccountPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("My Account");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        content.add(Box.createVerticalStrut(20));

        JLabel infoLabel = sectionLabel("Personal Information");
        content.add(infoLabel);
        content.add(Box.createVerticalStrut(10));

        content.add(createInfoForm());

        JButton saveBtn = new JButton("Save");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.setPreferredSize(new Dimension(100, 30));
        saveBtn.addActionListener(e -> saveProfile());
        content.add(Box.createVerticalStrut(10));
        content.add(saveBtn);

        content.add(Box.createVerticalStrut(30));
        JLabel verLabel = sectionLabel("Account Verification");
        content.add(verLabel);
        content.add(Box.createVerticalStrut(10));
        verificationPanel = new JPanel();
        verificationPanel.setBackground(Color.WHITE);
        verificationPanel.setLayout(new BoxLayout(verificationPanel, BoxLayout.Y_AXIS));
        updateVerificationPanel();
        content.add(verificationPanel);

        add(new JScrollPane(content), BorderLayout.CENTER);
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 128, 128));
        return label;
    }

    private JPanel createInfoForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        String[][] fields = {
                {"First Name", AppContext.currentUser.getName()},
                {"Last Name", AppContext.currentUser.getLastName()},
                {"Email", AppContext.currentUser.getEmail()},
                {"Phone Number", AppContext.currentUser.getPhone()},
                {"Country", AppContext.currentUser.getCountry()}
        };

        for (int i = 0; i < fields.length; i++) {
            JLabel lbl = new JLabel(fields[i][0]);
            JTextField tf = new JTextField(fields[i][1]);
            tf.setPreferredSize(new Dimension(200, 30));
            profileFields.put(fields[i][0], tf);

            gbc.gridx = i % 2 == 0 ? 0 : 2;
            gbc.gridy = i / 2;
            formPanel.add(lbl, gbc);

            gbc.gridx = i % 2 == 0 ? 1 : 3;
            formPanel.add(tf, gbc);
        }

        return formPanel;
    }

    private void saveProfile() {
        for (Map.Entry<String, JTextField> entry : profileFields.entrySet()) {
            if (entry.getValue().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Field '" + entry.getKey() + "' cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        AppContext.currentUser.setName(profileFields.get("First Name").getText());
        AppContext.currentUser.setLastName(profileFields.get("Last Name").getText());
        AppContext.currentUser.setEmail(profileFields.get("Email").getText());
        AppContext.currentUser.setPhone(profileFields.get("Phone Number").getText());
        AppContext.currentUser.setCountry(profileFields.get("Country").getText());

        JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateVerificationPanel() {
        verificationPanel.removeAll();

        if (AppContext.currentUser.isVerified()) {
            JLabel msg = new JLabel("Verified Account ✔");
            msg.setFont(new Font("Arial", Font.BOLD, 14));
            msg.setForeground(new Color(0, 153, 102));
            verificationPanel.add(msg);
        } else {
            JComboBox<String> methodCombo = new JComboBox<>(new String[]{"University Email", "Upload Degree"});
            JTextField inputField = new JTextField();
            JPanel form = new JPanel();
            form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
            form.setBackground(Color.WHITE);

            methodCombo.addActionListener(e -> {
                form.removeAll();
                String labelText = methodCombo.getSelectedItem().equals("University Email") ?
                        "Enter university email:" : "Enter degree link/file:";
                form.add(new JLabel(labelText));
                form.add(Box.createVerticalStrut(5));
                form.add(inputField);
                form.revalidate();
                form.repaint();
            });

            verificationPanel.add(new JLabel("Choose verification method:"));
            verificationPanel.add(Box.createVerticalStrut(5));
            verificationPanel.add(methodCombo);
            verificationPanel.add(Box.createVerticalStrut(10));
            verificationPanel.add(form);

            JButton verifyBtn = new JButton("Verify");
            verifyBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            verifyBtn.addActionListener(e -> {
                String val = inputField.getText().trim();
                if (val.isEmpty() || (methodCombo.getSelectedItem().equals("University Email") && !Pattern.matches(".+@.+\\..+", val))) {
                    JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AppContext.currentUser.setVerified(true);
                JOptionPane.showMessageDialog(this, "Verification completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateVerificationPanel();
            });

            verificationPanel.add(Box.createVerticalStrut(10));
            verificationPanel.add(verifyBtn);
        }

        verificationPanel.revalidate();
        verificationPanel.repaint();
    }
}
