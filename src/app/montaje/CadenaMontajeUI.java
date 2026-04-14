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

    // Constructor que inicializa el diálogo. Inicializa la lista de cadenas de montaje a partir de
    // lo recibido en los parámetros.
    public CadenaMontajeUI(Frame padre, List<CadenaMontaje> cadenas) {
        super(padre, "Cadenas de Montaje", true);
        this.cadenas = cadenas;
        setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        add(crearPanelCadenas(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    // Función auxiliar para crear los paneles de las cadenas de montaje.
    private JPanel crearPanelCadenas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Por cada cadena de montaje, crea una columna en el panel.
        for (int i = 0; i < cadenas.size(); i++) {
            // obtener la cadena en la iteración actual
            CadenaMontaje cadena = cadenas.get(i);
            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"Tipo Registro", "Hora", "Estado", "Trabajador"}, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            modelos[i] = modelo;
            // Modelo de tabla para la iteración actual
            JTable tabla = new JTable(modelo) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    String tipo = (String) getValueAt(row, 0);
                    c.setBackground("ERROR".equals(tipo) ? new Color(255, 180, 180) : getBackground());
                    return c;
                }
            };

            // Evento doble click para mostrar detalles de cada registro de la cadena de montaje.
            int idx = i;
            tabla.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int fila = tabla.rowAtPoint(e.getPoint());
                        if (fila < 0) return;
                        int filaModelo = tabla.convertRowIndexToModel(fila);
                        Registro registro = cadenas.get(idx).getRegistros().get(filaModelo);
                        // llamada a la función auxiliar que abre la ventana que muestra los detalles.
                        mostrarDetalles(registro);
                    }
                }
            });
            // configuraciones adicionales de de tabla y panel.
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
        // Devolvemos el panel con toda la configuración.
        return panel;
    }

    // Función auxiliar para crear el panel de botones (Solamente con el botón de refrescar.)
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarRegistros());
        panel.add(btnRefrescar);
        return panel;
    }

    // Añade los registros correspondientes a cada cadena de montaje.
    private void cargarRegistros() {
        // Iteramos a nivel de cadena de montaje
        for (int i = 0; i < cadenas.size(); i++) {
            DefaultTableModel modelo = modelos[i];
            modelo.setRowCount(0);
            List<Registro> registros = cadenas.get(i).getRegistros();
            // Iteramos por cada registro de la cadena de montaje.
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

    // Carga los registros y muestra la ventana.
    public void mostrarVentana() {
        cargarRegistros();
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    // Función auxiliar para mostrar los detalles de un registro en una nueva ventana
    private void mostrarDetalles(Registro registro) {
        JDialog dialog = new JDialog(this, "Detalles del registro", true);
        dialog.setSize(700, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        // En el textarea se muestra el output del objeto registro recibido en los parámetros.
        JTextArea area = new JTextArea(registro.detalles());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón de cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.add(btnCerrar);

        dialog.add(new JScrollPane(area), BorderLayout.CENTER);
        dialog.add(panelSur, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}