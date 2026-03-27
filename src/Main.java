import app.IMainMenu;
import app.MainMenu;
import data.App;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        App app = new App();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema de Gestión de Fábrica de Vehículos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);

            IMainMenu menuPrincipal = new MainMenu(frame, app);
            frame.add(menuPrincipal.getPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}