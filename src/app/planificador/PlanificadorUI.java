package app.planificador;

import data.almacen.IAlmacen;
import data.gestion.IGestorCadenasMontaje;
import exception.ColaVehiculosVacia;
import models.simulacion.SimulacionListener;
import models.vehiculo.*;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanificadorUI extends JDialog implements IPlanificadorUI {

    private final IGestorCadenasMontaje gestorCadenasMontaje;
    private final IAlmacen almacen;
    private final Map<String, Integer> filasPorId = new HashMap<>();

    private DefaultTableModel modeloCola;
    private JTextArea areaLogs;
    private JButton btnSimple;
    private JButton btnCompleja;
    private JButton btnMuyCompleja;
    private JButton btnNuevaSimulacion;

    public PlanificadorUI(Frame padre, IGestorCadenasMontaje gestorCadenasMontaje, IAlmacen almacen) {
        super(padre, "Planificador", true);
        this.gestorCadenasMontaje = gestorCadenasMontaje;
        this.almacen = almacen;

        setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(),  BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    // ── Panel superior: botón añadir vehículo ─────────────────────────────────

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnAnadir = new JButton("Añadir vehículo a la cola");
        btnAnadir.addActionListener(e -> abrirFormularioVehiculo());
        panel.add(btnAnadir);
        return panel;
    }

    // ── Panel central: tabla cola de vehículos ────────────────────────────────

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cola de montaje"));

        modeloCola = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Color", "Motor", "Tapicería", "Ruedas", "Estado"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = new JTable(modeloCola);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ── Panel inferior: botones simulación + logs ─────────────────────────────

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnSimple      = new JButton("Simulación Simple");
        btnCompleja    = new JButton("Simulación Compleja");
        btnMuyCompleja = new JButton("Simulación Muy Compleja");

        btnNuevaSimulacion = new JButton("Nueva simulación");
        btnNuevaSimulacion.setEnabled(false);
        btnNuevaSimulacion.addActionListener(e -> limpiarSimulacion());

        btnSimple.addActionListener(e -> ejecutarSimulacion("Simple"));
        btnCompleja.addActionListener(e -> ejecutarSimulacion("Compleja"));
        btnMuyCompleja.addActionListener(e -> ejecutarSimulacion("Muy Compleja"));

        panelBotones.add(btnSimple);
        panelBotones.add(btnCompleja);
        panelBotones.add(btnMuyCompleja);
        panelBotones.add(Box.createHorizontalStrut(30));
        panelBotones.add(btnNuevaSimulacion);

        // Logs
        areaLogs = new JTextArea(16, 0);
        areaLogs.setEditable(false);
        areaLogs.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        panel.add(panelBotones,          BorderLayout.NORTH);
        panel.add(new JScrollPane(areaLogs), BorderLayout.CENTER);
        return panel;
    }

    // ── Formulario añadir vehículo ────────────────────────────────────────────

    private void abrirFormularioVehiculo() {
        JDialog dialog = new JDialog(this, "Nuevo vehículo", true);
        dialog.setSize(650, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = defaultGbc();

        // Tipo de vehículo
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Turismo", "Furgoneta", "Biplaza Deportivo"});

        // Color
        JTextField txtColor = new JTextField(10);

        // Cantidad
        JSpinner spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));

        // Componentes
        ArrayList<Motor>    motores    = almacen.obtenerMotores();
        ArrayList<Rueda>    ruedas     = almacen.obtenerRuedas();
        ArrayList<Tapiceria> tapicerias = almacen.obtenerTapicerias();

        JComboBox<Motor>    cmbMotor    = new JComboBox<>(motores.toArray(new Motor[0]));
        JComboBox<Rueda>    cmbRueda    = new JComboBox<>(ruedas.toArray(new Rueda[0]));
        JComboBox<Tapiceria> cmbTapiceria = new JComboBox<>(tapicerias.toArray(new Tapiceria[0]));

        agregarFila(dialog, gbc, 0, "Tipo:",       cmbTipo);
        agregarFila(dialog, gbc, 1, "Color:",      txtColor);
        agregarFila(dialog, gbc, 2, "Cantidad:",   spnCantidad);
        agregarFila(dialog, gbc, 3, "Motor:",      cmbMotor);
        agregarFila(dialog, gbc, 4, "Tapicería:",  cmbTapiceria);
        agregarFila(dialog, gbc, 5, "Ruedas:",     cmbRueda);

        JButton btnGuardar = new JButton("Añadir a la cola");
        btnGuardar.addActionListener(e -> {
            String color = txtColor.getText().trim();
            if (color.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El color no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cmbMotor.getSelectedItem() == null || cmbRueda.getSelectedItem() == null || cmbTapiceria.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(dialog, "No hay componentes disponibles en el almacén.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad    = (int) spnCantidad.getValue();
            Motor motor     = (Motor)     cmbMotor.getSelectedItem();
            Rueda rueda     = (Rueda)     cmbRueda.getSelectedItem();
            Tapiceria tap   = (Tapiceria) cmbTapiceria.getSelectedItem();

            if (motor.getStock() < cantidad) {
                JOptionPane.showMessageDialog(dialog, "Stock insuficiente de motor: hay " + motor.getStock() + " unidades.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tap.getStock() < cantidad) {
                JOptionPane.showMessageDialog(dialog, "Stock insuficiente de tapicería: hay " + tap.getStock() + " unidades.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (rueda.getStock() < cantidad * 4) {
                JOptionPane.showMessageDialog(dialog, "Stock insuficiente de ruedas: se necesitan " + (cantidad * 4) + " y hay " + rueda.getStock() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int tipoIdx     = cmbTipo.getSelectedIndex();

            for (int i = 0; i < cantidad; i++) {
                ArrayList<Componente> comps = new ArrayList<>();
                comps.add(almacen.extraerMotor(motor.getId()));
                comps.add(almacen.extraerTapiceria(tap.getId()));
                comps.add(almacen.extraerRueda(rueda.getId()));

                Vehiculo v = switch (tipoIdx) {
                    case 1  -> new Furgoneta(color, 3, 2100, 3500, comps);
                    case 2  -> new BiplazaDeportivo(color, 2, 1100, 1400, comps);
                    default -> new Turismo(color, 5, 1300, 1900, comps);
                };

                gestorCadenasMontaje.encolarVehiculo(v);
                filasPorId.put(v.getId(), modeloCola.getRowCount());
                modeloCola.addRow(new Object[]{
                        v.getId().substring(0, 8),
                        v.getTipo(),
                        v.getColor(),
                        motor.getNombre(),
                        tap.getNombre(),
                        rueda.getNombre(),
                        v.getEstadoActual()
                });
            }
            log("Añadidos " + cantidad + " vehículo(s) de tipo " + cmbTipo.getSelectedItem() + " a la cola.");
            dialog.dispose();
        });

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnGuardar, gbc);
        dialog.setVisible(true);
    }

    private void ejecutarSimulacion(String tipo) {
        setBotonesSimulacion(false, false);
        log("--- Iniciando Simulación " + tipo + " ---");

        new Thread(() -> {
            try {
                SimulacionListener listener = (logTexto, idVehiculo, estado) ->
                        SwingUtilities.invokeLater(() -> {
                            log(logTexto);
                            Integer fila = filasPorId.get(idVehiculo);
                            if (fila != null) modeloCola.setValueAt(estado, fila, 6);
                        });

                switch (tipo) {
                    case "Simple"       -> gestorCadenasMontaje.iniciarSimulacionSimple(listener);
                    case "Compleja"     -> gestorCadenasMontaje.iniciarSimulacionCompleja(listener);
                    case "Muy Compleja" -> gestorCadenasMontaje.iniciarSimulacionMuyCompleja(listener);
                }

                log("--- Simulación " + tipo + " completada ---");
            } catch (ColaVehiculosVacia ex) {
                log("ERROR: " + ex.getMessage());
            } finally {
                SwingUtilities.invokeLater(() -> setBotonesSimulacion(false, true));
            }
        }).start();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void setBotonesSimulacion(boolean enabled, boolean enableNewSimulation) {
        btnSimple.setEnabled(enabled);
        btnCompleja.setEnabled(enabled);
        btnMuyCompleja.setEnabled(false);
        btnNuevaSimulacion.setEnabled(enableNewSimulation);
    }

    private void log(String mensaje) {
        SwingUtilities.invokeLater(() -> areaLogs.append(mensaje + "\n"));
    }

    private GridBagConstraints defaultGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void agregarFila(JDialog dialog, GridBagConstraints gbc, int fila, String label, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(campo, gbc);
    }

    private void limpiarSimulacion() {
        modeloCola.setRowCount(0);
        filasPorId.clear();
        areaLogs.setText("");
        btnNuevaSimulacion.setEnabled(false);
        setBotonesSimulacion(true, false);
    }

    public void mostrarVentana() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}