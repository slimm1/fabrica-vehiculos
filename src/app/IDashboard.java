package app;

import javax.swing.JPanel;

public interface IDashboard {
    JPanel getPanel();

    void configurarAcciones();

    void init();
}