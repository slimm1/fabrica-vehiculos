package app.montaje;

import models.montaje.CadenaMontaje;
import models.montaje.RegistroMontaje;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CadenaMontajeUI extends JDialog {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final List<CadenaMontaje> cadenas;

    private final DefaultTableModel[] modelos = new DefaultTableModel[3];

    public CadenaMontajeUI(Frame padre, List<CadenaMontaje> cadenas) {
        super(padre, "Cadenas de Montaje", true);
        this.cadenas = cadenas;
        setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        add(crearPanelCadenas(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelCadenas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < cadenas.size(); i++) {
            CadenaMontaje cadena = cadenas.get(i);
            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"Hora", "Vehículo", "Estado", "Componente"}, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            modelos[i] = modelo;

            JTable tabla = new JTable(modelo);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(160);
            tabla.setAutoCreateRowSorter(true);

            JPanel panelCadena = new JPanel(new BorderLayout());
            panelCadena.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(),
                    cadena.getTipo(),
                    TitledBorder.LEFT, TitledBorder.TOP));
            panelCadena.add(new JScrollPane(tabla), BorderLayout.CENTER);

            panel.add(panelCadena);
        }

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarRegistros());
        panel.add(btnRefrescar);
        return panel;
    }

    private void cargarRegistros() {
        for (int i = 0; i < cadenas.size(); i++) {
            DefaultTableModel modelo = modelos[i];
            modelo.setRowCount(0);
            List<RegistroMontaje> registros = cadenas.get(i).getRegistros();
            for (RegistroMontaje r : registros) {
                modelo.addRow(new Object[]{
                        r.getTimestamp().format(FMT),
                        r.getTipoVehiculo() + " [" + r.getIdVehiculo().substring(0, 8) + "]",
                        r.getEstado(),
                        r.getComponente() != null ? r.getComponente().getNombre() : "—"
                });
            }
        }
    }

    public void mostrarVentana() {
        cargarRegistros();
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}