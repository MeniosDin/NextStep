import javax.swing.*;

public class  NextStep{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NextStepDashboard dashboard = new NextStepDashboard();
            dashboard.setVisible(true);
        });
    }
}