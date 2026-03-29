package app.montaje;

import models.montaje.CadenaMontaje;
import models.montaje.Registro;
import models.montaje.RegistroError;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Diálogo Swing que muestra las tres cadenas de montaje en columnas paralelas.
 * Cada tabla lista sus registros (montaje/error), resaltando en rojo los de
 * error. El doble clic sobre una fila abre el detalle completo del registro.
 */
public class CadenaMontajeUI extends JDialog {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final List<CadenaMontaje> cadenas;
    private final DefaultTableModel[] modelos = new DefaultTableModel[3];
    private final JTable[] tablas = new JTable[3];

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
                    new String[]{"Tipo Registro", "Hora", "Estado", "Trabajador"}, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            modelos[i] = modelo;

            JTable tabla = new JTable(modelo) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    String tipo = (String) getValueAt(row, 0);
                    c.setBackground("ERROR".equals(tipo) ? new Color(255, 180, 180) : getBackground());
                    return c;
                }
            };

            int idx = i;
            tabla.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int fila = tabla.rowAtPoint(e.getPoint());
                        if (fila < 0) return;
                        int filaModelo = tabla.convertRowIndexToModel(fila);
                        Registro registro = cadenas.get(idx).getRegistros().get(filaModelo);
                        mostrarDetalles(registro);
                    }
                }
            });

            tablas[i] = tabla;            tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
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
            List<Registro> registros = cadenas.get(i).getRegistros();
            for (Registro r : registros) {
                String tipoRegistro = r instanceof RegistroError ? "ERROR" : "Montaje";
                modelo.addRow(new Object[]{
                        tipoRegistro,
                        r.getTimestamp().format(FMT),
                        r.getEstadoVehiculo().getNombre(),
                        r.getTrabajador().getNombre() + " " + r.getTrabajador().getApellidos()
                });
            }
        }
    }

    public void mostrarVentana() {
        cargarRegistros();
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    private void mostrarDetalles(Registro registro) {
        JDialog dialog = new JDialog(this, "Detalles del registro", true);
        dialog.setSize(700, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JTextArea area = new JTextArea(registro.detalles());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.add(btnCerrar);

        dialog.add(new JScrollPane(area), BorderLayout.CENTER);
        dialog.add(panelSur, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}