package app.almacen.componentes;

import app.almacen.BalanceStock;
import data.almacen.IAlmacen;
import models.vehiculo.componente.motor.*;
import models.vehiculo.componente.rueda.*;
import models.vehiculo.componente.tapiceria.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana Swing que muestra las tres tablas de componentes (motores, ruedas,
 * tapicerías) con su stock total y permite añadir nuevos elementos mediante
 * formularios modales. También da acceso al balance de stock global.
 */
public class ListaComponentes extends JDialog implements IListaComponentes {

    private JTable tablaMotores;
    private JTable tablaRuedas;
    private JTable tablaTapicerias;

    // Incializar modelos de tablas
    private DefaultTableModel modeloMotores = new DefaultTableModel(new String[]{"Nombre", "Cilindrada", "Potencia (CV)", "Cilindros", "Stock"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
        @Override public Class<?> getColumnClass(int c) { return c == 4 ? Integer.class : String.class; }
    };
    private DefaultTableModel modeloRuedas = new DefaultTableModel(new String[]{"Nombre", "Ancho (mm)", "Diámetro", "Carga (kg)", "Vel. (km/h)", "Stock"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
        @Override public Class<?> getColumnClass(int c) { return c == 5 ? Integer.class : String.class; }
    };
    private DefaultTableModel modeloTapicerias = new DefaultTableModel(new String[]{"Nombre", "Color", "Metros²", "Stock"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
        @Override public Class<?> getColumnClass(int c) { return c == 3 ? Integer.class : String.class; }
    };

    private JLabel lblTotalMotores    = new JLabel("Total stock: 0");
    private JLabel lblTotalRuedas     = new JLabel("Total stock: 0");
    private JLabel lblTotalTapicerias = new JLabel("Total stock: 0");

    private List<Motor>    motores;
    private List<Rueda>    ruedas;
    private List<Tapiceria> tapicerias;

    private IAlmacen almacen;

    // Inicializa el listado de componentes a través del almacén
    public ListaComponentes(Frame padre, IAlmacen almacen) {
        super(padre, "Gestión de Componentes", true);
        setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.almacen = almacen;
        add(crearPanelListas(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    // Crear los paneles con cada lista de cada familia
    private JPanel crearPanelListas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnMotor     = new JButton("Añadir Motor");
        JButton btnRueda     = new JButton("Añadir Rueda");
        JButton btnTapiceria = new JButton("Añadir Tapicería");

        btnMotor.addActionListener(e -> abrirFormularioMotor());
        btnRueda.addActionListener(e -> abrirFormularioRueda());
        btnTapiceria.addActionListener(e -> abrirFormularioTapiceria());

        this.tablaMotores    = new JTable(modeloMotores);
        this.tablaRuedas     = new JTable(modeloRuedas);
        this.tablaTapicerias = new JTable(modeloTapicerias);

        for (JTable tabla : new JTable[]{tablaMotores, tablaRuedas, tablaTapicerias}) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(160);
            tabla.getColumnModel().getColumn(0).setMinWidth(120);
        }

        panel.add(crearPanelTabla("Motores",    lblTotalMotores,    btnMotor,     tablaMotores));
        panel.add(crearPanelTabla("Ruedas",     lblTotalRuedas,     btnRueda,     tablaRuedas));
        panel.add(crearPanelTabla("Tapicerías", lblTotalTapicerias, btnTapiceria, tablaTapicerias));

        return panel;
    }

    // Función auxiliar para crear la tabla de componentes de cada panel
    private JPanel crearPanelTabla(String titulo, JLabel lblTotal, JButton btnAnadir, JTable tabla) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), titulo, TitledBorder.LEFT, TitledBorder.TOP));

        tabla.setAutoCreateRowSorter(true);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        lblTotal.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        panelSur.add(lblTotal, BorderLayout.WEST);
        panelSur.add(btnAnadir, BorderLayout.EAST);
        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    // Panel inferior con botones de acciones adicionales dentro de esta ventana.
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnBalance = new JButton("Ver Balance de Stock");
        btnBalance.addActionListener(e -> new BalanceStock((JFrame) getParent(), almacen).mostrarVentana());
        panel.add(btnBalance);
        return panel;
    }

    // Función para actualizar el stock según lo que haya en el almacén al llamarla
    private void actualizarTotalMotores() {
        // Uso del stream de la lista de motores para sumar el stock de cada uno de sus objetos.
        int total = motores == null ? 0 : motores.stream().mapToInt(Motor::getStock).sum();
        lblTotalMotores.setText("Total stock: " + total);
    }

    private void actualizarTotalRuedas() {
        int total = ruedas == null ? 0 : ruedas.stream().mapToInt(Rueda::getStock).sum();
        lblTotalRuedas.setText("Total stock: " + total);
    }

    private void actualizarTotalTapicerias() {
        int total = tapicerias == null ? 0 : tapicerias.stream().mapToInt(Tapiceria::getStock).sum();
        lblTotalTapicerias.setText("Total stock: " + total);
    }

    // Función auxiliar para abrir y construir el formulario de creación de motores
    private void abrirFormularioMotor() {
        JDialog dialog = new JDialog(this, "Nuevo Motor", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = defaultGbc();

        // Definir los tipos de motores disponibles en el selector.
        String[] tipos = {"Motor de Gasolina", "Motor Eléctrico", "Motor Híbrido"};
        JComboBox<String> cmbTipo   = new JComboBox<>(tipos);
        JTextField txtCilindrada    = new JTextField(10);
        JTextField txtPotencia      = new JTextField(10);
        JTextField txtCilindros     = new JTextField(10);
        JTextField txtStock         = new JTextField(10);

        // uso de función para añadir fila al formulario.
        agregarFila(dialog, gbc, 0, "Tipo:", cmbTipo);
        agregarFila(dialog, gbc, 1, "Cilindrada (cc):", txtCilindrada);
        agregarFila(dialog, gbc, 2, "Potencia (CV):", txtPotencia);
        agregarFila(dialog, gbc, 3, "Núm. cilindros:", txtCilindros);
        agregarFila(dialog, gbc, 4, "Cantidad a añadir:", txtStock);

        JButton btnGuardar = new JButton("Guardar");
        // Listener para el guardado de nuevo motor
        btnGuardar.addActionListener(e -> {
            try {
                // recoger los valores de los campos del formulario.
                int cilindrada = Integer.parseInt(txtCilindrada.getText().trim());
                int potencia   = Integer.parseInt(txtPotencia.getText().trim());
                int cilindros  = Integer.parseInt(txtCilindros.getText().trim());
                int stock      = Integer.parseInt(txtStock.getText().trim());

                // Definir un nuevo objeto Motor en función del tipo seleccionado en el selector.
                Motor motor = switch (cmbTipo.getSelectedIndex()) {
                    case 1  -> new MotorElectrico(stock, cilindrada, potencia, cilindros);
                    case 2  -> new MotorHibrido(stock, cilindrada, potencia, cilindros);
                    default -> new MotorGasolina(stock, cilindrada, potencia, cilindros);
                };

                // Añadir el nuevo motor a la lista de motores de esta clase, al almacén y al modelo de tabla de la UI
                motores.add(motor);
                almacen.agregarMotor(motor);
                modeloMotores.addRow(new Object[]{motor.getNombre(), cilindrada, potencia, cilindros, stock});
                actualizarTotalMotores();
                // cerrar esta ventana
                dialog.dispose();
            } catch (NumberFormatException ex) {
                // Validación basiquísima. Si alguno de los parseos lanza esta excepción, la instancia no se crea.
                JOptionPane.showMessageDialog(dialog, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Configuraciones adicionales.
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnGuardar, gbc);
        dialog.setVisible(true);
    }

    // Misma dinámica que la anterior. Apertura y configuración de la ventana que aloja el formulario de nueva rueda.
    private void abrirFormularioRueda() {
        JDialog dialog = new JDialog(this, "Nueva Rueda", true);
        dialog.setSize(350, 270);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = defaultGbc();

        // Tipos de rueda disponibles en el selector.
        String[] tipos = {"Rueda Normal", "Rueda Deportivo", "Rueda Todoterreno"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtAncho     = new JTextField(10);
        JTextField txtDiametro  = new JTextField(10);
        JTextField txtCarga     = new JTextField(10);
        JTextField txtVelocidad = new JTextField(10);
        JTextField txtStock     = new JTextField(10);

        // filas del formulario a través de la función "agregarFila"
        agregarFila(dialog, gbc, 0, "Tipo:", cmbTipo);
        agregarFila(dialog, gbc, 1, "Ancho (mm):", txtAncho);
        agregarFila(dialog, gbc, 2, "Diámetro (pulgadas):", txtDiametro);
        agregarFila(dialog, gbc, 3, "Índice carga (kg):", txtCarga);
        agregarFila(dialog, gbc, 4, "Cód. velocidad (km/h):", txtVelocidad);
        agregarFila(dialog, gbc, 5, "Cantidad a añadir:", txtStock);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                // Recoger y parsear los tipos de datos del formulario
                int ancho     = Integer.parseInt(txtAncho.getText().trim());
                int diametro  = Integer.parseInt(txtDiametro.getText().trim());
                int carga     = Integer.parseInt(txtCarga.getText().trim());
                int velocidad = Integer.parseInt(txtVelocidad.getText().trim());
                int stock     = Integer.parseInt(txtStock.getText().trim());

                // Instanciar la rueda según el tipo de rueda seleccionada en el selector
                Rueda rueda = switch (cmbTipo.getSelectedIndex()) {
                    case 1  -> new RuedaDeportivo(stock, ancho, diametro, carga, velocidad);
                    case 2  -> new RuedaTodoterreno(stock, ancho, diametro, carga, velocidad);
                    default -> new RuedaNormal(stock, ancho, diametro, carga, velocidad);
                };

                // almacenar la rueda y mostrarla. Actualizar cantidades de las etiquetas que muestran el total.
                ruedas.add(rueda);
                almacen.agregarRueda(rueda);
                modeloRuedas.addRow(new Object[]{rueda.getNombre(), ancho, diametro, carga, velocidad, stock});
                actualizarTotalRuedas();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                // Lanza este mensaje si falla alguno de los parseos al recoger los datos del formulario.
                JOptionPane.showMessageDialog(dialog, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ocnfiguraciones adicionales.
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnGuardar, gbc);
        dialog.setVisible(true);
    }

    // Igual que en las dos anteriores. Función para abrir el formulario de creación de nueva tapicería.
    private void abrirFormularioTapiceria() {
        JDialog dialog = new JDialog(this, "Nueva Tapicería", true);
        dialog.setSize(350, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = defaultGbc();

        // Tipos de tapicería disponibles
        String[] tipos = {"Tapicería de Tela", "Tapicería de Cuero", "Tapicería Alcántara"};
        JComboBox<String> cmbTipo = new JComboBox<>(tipos);
        JTextField txtColor  = new JTextField(10);
        JTextField txtMetros = new JTextField(10);
        JTextField txtStock  = new JTextField(10);

        // Agregar filas del formulario
        agregarFila(dialog, gbc, 0, "Tipo:", cmbTipo);
        agregarFila(dialog, gbc, 1, "Color:", txtColor);
        agregarFila(dialog, gbc, 2, "Metros cuadrados:", txtMetros);
        agregarFila(dialog, gbc, 3, "Cantidad a añadir:", txtStock);

        // Botón y su listener
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                // Recoger los campos del formulario
                String color  = txtColor.getText().trim();
                int metros    = Integer.parseInt(txtMetros.getText().trim());
                int stock     = Integer.parseInt(txtStock.getText().trim());

                // validación sencilla para color
                if (color.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El color no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Instanciar la tapiceria en función del tipo seleccionado.
                Tapiceria tapiceria = switch (cmbTipo.getSelectedIndex()) {
                    case 1  -> new TapiceriaCuero(stock, color, metros);
                    case 2  -> new TapiceriaAlcantara(stock, color, metros);
                    default -> new TapiceriaTela(stock, color, metros);
                };

                tapicerias.add(tapiceria);
                almacen.agregarTapiceria(tapiceria);
                modeloTapicerias.addRow(new Object[]{tapiceria.getNombre(), color, metros, stock});
                actualizarTotalTapicerias();
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

    // Función auxiliar de configuración
    private GridBagConstraints defaultGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    // Función auxiliar para añadir una fila al dialog pasado en los parámetros.
    private void agregarFila(JDialog dialog, GridBagConstraints gbc, int fila, String label, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(campo, gbc);
    }

    // Carga la lista de motores de esta instancia a partir de la dada en los parámetros. Carga la tabla de la UI con los datos
    @Override
    public void cargarMotores(List<Motor> motores) {
        this.motores = motores;
        modeloMotores.setRowCount(0);
        motores.forEach(m -> modeloMotores.addRow(new Object[]{
                m.getNombre(), m.getCilindrada(), m.getPotencia(), m.getNumeroCilindros(), m.getStock()
        }));
        actualizarTotalMotores();
    }

    // Carga la lista de ruedas de esta instancia a partir de la dada en los parámetros. Carga la tabla de la UI con los datos
    @Override
    public void cargarRuedas(List<Rueda> ruedas) {
        this.ruedas = ruedas;
        modeloRuedas.setRowCount(0);
        ruedas.forEach(r -> modeloRuedas.addRow(new Object[]{
                r.getNombre(), r.getAnchoMm(), r.getDiametroPulgadas(), r.getIndiceCargaKg(), r.getCodigoVelocidadKmh(), r.getStock()
        }));
        actualizarTotalRuedas();
    }


    // Carga la lista de tapicerías de esta instancia a partir de la dada en los parámetros. Carga la tabla de la UI con los datos
    @Override
    public void cargarTapicerias(List<Tapiceria> tapicerias) {
        this.tapicerias = tapicerias;
        modeloTapicerias.setRowCount(0);
        tapicerias.forEach(t -> modeloTapicerias.addRow(new Object[]{
                t.getNombre(), t.getColor(), t.getMetrosCuadrados(), t.getStock()
        }));
        actualizarTotalTapicerias();
    }

    @Override
    public void mostrarVentana() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}