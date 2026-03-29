import app.IDashboard;
import app.Dashboard;
import data.App;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        App app = new App();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema de Gestión de Fábrica de Vehículos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);

            IDashboard menuPrincipal = new Dashboard(frame, app);
            frame.add(menuPrincipal.getPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}