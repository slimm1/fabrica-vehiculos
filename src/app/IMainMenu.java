package app;

import javax.swing.JPanel;

public interface IMainMenu {
    JPanel getPanel();

    void configurarAcciones();

    void init();
}