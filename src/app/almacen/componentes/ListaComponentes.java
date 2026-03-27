package app.almacen.componentes;

import app.almacen.BalanceStock;
import data.almacen.IAlmacen;
import models.vehiculo.componente.motor.*;
import models.vehiculo.componente.rueda.*;
import models.vehiculo.componente.tapiceria.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ListaComponentes extends JDialog implements IListaComponentes {

    private DefaultListModel<String> modeloMotores = new DefaultListModel<>();
    private DefaultListModel<String> modeloRuedas = new DefaultListModel<>();
    private DefaultListModel<String> modeloTapicerias = new DefaultListModel<>();

    private JLabel lblTotalMotores = new JLabel("Total: 0");
    private JLabel lblTotalRuedas = new JLabel("Total: 0");
    private JLabel lblTotalTapicerias = new JLabel("Total: 0");

    private List<Motor> motores;
    private List<Rueda> ruedas;
    private List<Tapiceria> tapicerias;

    private IAlmacen almacen;

    public ListaComponentes(Frame padre, IAlmacen almacen) {
        super(padre, "Gestión de Componentes", true);
        setSize(GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds().getSize());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.almacen = almacen;
        add(crearPanelListas(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelListas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnMotor = new JButton("Añadir Motor");
        JButton btnRueda = new JButton("Añadir Rueda");
        JButton btnTapiceria = new JButton("Añadir Tapicería");

        btnMotor.addActionListener(e -> abrirFormularioMotor());
        btnRueda.addActionListener(e -> abrirFormularioRueda());
        btnTapiceria.addActionListener(e -> abrirFormularioTapiceria());

        panel.add(crearPanelLista("Motores", modeloMotores, lblTotalMotores, btnMotor));
        panel.add(crearPanelLista("Ruedas", modeloRuedas, lblTotalRuedas, btnRueda));
        panel.add(crearPanelLista("Tapicerías", modeloTapicerias, lblTotalTapicerias, btnTapiceria));

        return panel;
    }

    private JPanel crearPanelLista(String titulo, DefaultListModel<String> modelo, JLabel lblTotal, JButton btnAnadir) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), titulo,
                TitledBorder.LEFT, TitledBorder.TOP));
        panel.add(new JScrollPane(new JList<>(modelo)), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        lblTotal.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        panelSur.add(lblTotal, BorderLayout.WEST);
        panelSur.add(btnAnadir, BorderLayout.EAST);

        panel.add(panelSur, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnBalance = new JButton("Ver Balance de Stock");
        btnBalance.addActionListener(e -> new BalanceStock((JFrame) getParent(), almacen).mostrarVentana());
        panel.add(btnBalance);
        return panel;
    }

    private void actualizarTotal(JLabel label, DefaultListModel<?> modelo) {
        label.setText("Total: " + modelo.getSize());
    }

    // ── Formulario Motor ──────────────────────────────────────────────────────

    private void abrirFormularioMotor() {
        JDialog dialog = new JDialog(this, "Nuevo Motor", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = defaultGbc();

        String[] tipos = {"Motor de Gasolina", "Motor Eléctrico", "Motor Híbrido"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtCilindrada = new JTextField(10);
        JTextField txtPotencia = new JTextField(10);
        JTextField txtCilindros = new JTextField(10);

        agregarFila(dialog, gbc, 0, "Tipo:", cmbTipo);
        agregarFila(dialog, gbc, 1, "Cilindrada (cc):", txtCilindrada);
        agregarFila(dialog, gbc, 2, "Potencia (CV):", txtPotencia);
        agregarFila(dialog, gbc, 3, "Núm. cilindros:", txtCilindros);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                int cilindrada = Integer.parseInt(txtCilindrada.getText().trim());
                int potencia = Integer.parseInt(txtPotencia.getText().trim());
                int cilindros = Integer.parseInt(txtCilindros.getText().trim());

                Motor motor = switch (cmbTipo.getSelectedIndex()) {
                    case 1 -> new MotorElectrico(cilindrada, potencia, cilindros);
                    case 2 -> new MotorHibrido(cilindrada, potencia, cilindros);
                    default -> new MotorGasolina(cilindrada, potencia, cilindros);
                };

                motores.add(motor);
                almacen.agregarMotor(motor);
                modeloMotores.addElement(motor.getNombre() + " — " + motor.toString());
                actualizarTotal(lblTotalMotores, modeloMotores);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnGuardar, gbc);
        dialog.setVisible(true);
    }

    // ── Formulario Rueda ──────────────────────────────────────────────────────

    private void abrirFormularioRueda() {
        JDialog dialog = new JDialog(this, "Nueva Rueda", true);
        dialog.setSize(350, 270);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = defaultGbc();

        String[] tipos = {"Rueda Normal", "Rueda Deportivo", "Rueda Todoterreno"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtAncho = new JTextField(10);
        JTextField txtDiametro = new JTextField(10);
        JTextField txtCarga = new JTextField(10);
        JTextField txtVelocidad = new JTextField(10);

        agregarFila(dialog, gbc, 0, "Tipo:", cmbTipo);
        agregarFila(dialog, gbc, 1, "Ancho (mm):", txtAncho);
        agregarFila(dialog, gbc, 2, "Diámetro (pulgadas):", txtDiametro);
        agregarFila(dialog, gbc, 3, "Índice carga (kg):", txtCarga);
        agregarFila(dialog, gbc, 4, "Cód. velocidad (km/h):", txtVelocidad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                int ancho = Integer.parseInt(txtAncho.getText().trim());
                int diametro = Integer.parseInt(txtDiametro.getText().trim());
                int carga = Integer.parseInt(txtCarga.getText().trim());
                int velocidad = Integer.parseInt(txtVelocidad.getText().trim());

                Rueda rueda = switch (cmbTipo.getSelectedIndex()) {
                    case 1 -> new RuedaDeportivo(ancho, diametro, carga, velocidad);
                    case 2 -> new RuedaTodoterreno(ancho, diametro, carga, velocidad);
                    default -> new RuedaNormal(ancho, diametro, carga, velocidad);
                };

                ruedas.add(rueda);
                almacen.agregarRueda(rueda);
                modeloRuedas.addElement(rueda.getNombre() + " — " + rueda.toString());
                actualizarTotal(lblTotalRuedas, modeloRuedas);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnGuardar, gbc);
        dialog.setVisible(true);
    }

    // ── Formulario Tapicería ──────────────────────────────────────────────────

    private void abrirFormularioTapiceria() {
        JDialog dialog = new JDialog(this, "Nueva Tapicería", true);
        dialog.setSize(350, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = defaultGbc();

        String[] tipos = {"Tapicería de Tela", "Tapicería de Cuero", "Tapicería Alcántara"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtColor = new JTextField(10);
        JTextField txtMetros = new JTextField(10);

        agregarFila(dialog, gbc, 0, "Tipo:", cmbTipo);
        agregarFila(dialog, gbc, 1, "Color:", txtColor);
        agregarFila(dialog, gbc, 2, "Metros cuadrados:", txtMetros);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                String color = txtColor.getText().trim();
                int metros = Integer.parseInt(txtMetros.getText().trim());

                if (color.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El color no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Tapiceria tapiceria = switch (cmbTipo.getSelectedIndex()) {
                    case 1 -> new TapiceriaCuero(color, metros);
                    case 2 -> new TapiceriaAlcantara(color, metros);
                    default -> new TapiceriaTela(color, metros);
                };

                tapicerias.add(tapiceria);
                almacen.agregarTapiceria(tapiceria);
                modeloTapicerias.addElement(tapiceria.getNombre() + " — " + tapiceria);
                actualizarTotal(lblTotalTapicerias, modeloTapicerias);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnGuardar, gbc);
        dialog.setVisible(true);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

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

    // ── IListaComponentes ─────────────────────────────────────────────────────

    @Override
    public void cargarMotores(List<Motor> motores) {
        this.motores = motores;
        modeloMotores.clear();
        motores.forEach(m -> modeloMotores.addElement(m.getNombre() + " — " + m.toString()));
        actualizarTotal(lblTotalMotores, modeloMotores);
    }

    @Override
    public void cargarRuedas(List<Rueda> ruedas) {
        this.ruedas = ruedas;
        modeloRuedas.clear();
        ruedas.forEach(r -> modeloRuedas.addElement(r.getNombre() + " — " + r.toString()));
        actualizarTotal(lblTotalRuedas, modeloRuedas);
    }

    @Override
    public void cargarTapicerias(List<Tapiceria> tapicerias) {
        this.tapicerias = tapicerias;
        modeloTapicerias.clear();
        tapicerias.forEach(t -> modeloTapicerias.addElement(t.getNombre() + " — " + t.toString()));
        actualizarTotal(lblTotalTapicerias, modeloTapicerias);
    }

    @Override
    public void mostrarVentana() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}