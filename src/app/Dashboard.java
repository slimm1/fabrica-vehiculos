package app;

import app.almacen.componentes.IListaComponentes;
import app.almacen.componentes.ListaComponentes;
import app.empleados.IListaEmpleados;
import app.empleados.ListaEmpleados;
import app.almacen.vehiculos.IListaVehiculos;
import app.almacen.vehiculos.ListaVehiculos;
import app.empleados.ListaOperarios;
import app.montaje.CadenaMontajeUI;
import app.planificador.IPlanificadorUI;
import app.planificador.PlanificadorUI;
import data.App;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Panel principal de navegación. Agrupa los accesos a las tres secciones
 * (Personal, Almacén, Producción) y actúa como punto de entrada a todos
 * los diálogos de la aplicación.
 */
public class Dashboard extends JPanel implements IDashboard {
    private JFrame parentFrame;
    private App app;

    // Constructor del Dashboard, inicializa el frame principal y recibe la instancia de la App en los argumentos.
    public Dashboard(App app) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(Color.LIGHT_GRAY);

        // inicializar el frame principal de la app.
        this.parentFrame = initFrame();
        this.app = app;
        // inicializar los eventos y componentes del dashboard
        configurarAcciones();
    }

    /**
     * Función para iniciar los eventos de los botones del Dashboard
     */
    @Override
    public void configurarAcciones() {
        // Grupo de botones panel superior
        JPanel panelEmpleados = crearSeccion("Gestión de Personal");
        JButton btnEmpleados = new JButton("Gestión de empleados");
        JButton btnOperarios = new JButton("Operarios");
        panelEmpleados.add(btnEmpleados);
        panelEmpleados.add(btnOperarios);

        // Grupo de botones panel central
        JPanel panelAlmacen = crearSeccion("Inventario y Almacén");
        JButton btnVehiculos = new JButton("Vehículos");
        JButton btnComponentes = new JButton("Componentes");
        panelAlmacen.add(btnVehiculos);
        panelAlmacen.add(btnComponentes);

        // Grupo de botones panel inferior
        JPanel panelOperaciones = crearSeccion("Producción");
        JButton btnPlanificador = new JButton("Planificador");
        JButton btnCadenasMontaje = new JButton("Cadenas de montaje");
        panelOperaciones.add(btnPlanificador);
        panelOperaciones.add(btnCadenasMontaje);

        // Botón de salir
        JButton btnSalir = new JButton("Salir de la Aplicación");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setForeground(Color.RED);

        // Añadir eventos a cada botón
        btnEmpleados.addActionListener(e -> {
            IListaEmpleados ventanaEmpleados = new ListaEmpleados(this.parentFrame, this.app.getGestorTrabajadores());
            ventanaEmpleados.cargarDatos(this.app.getGestorTrabajadores().obtenerTodosTrabajadores());
            ventanaEmpleados.mostrarVentana();
        });
        btnVehiculos.addActionListener(e -> {
            IListaVehiculos ventanaVehiculos = new ListaVehiculos(this.parentFrame, this.app.getAlmacen());
            ventanaVehiculos.cargarDatos(this.app.getAlmacen().obtenerVehiculos());
            ventanaVehiculos.mostrarVentana();
        });
        btnComponentes.addActionListener(e -> {
            IListaComponentes listaComponentes = new ListaComponentes(this.parentFrame, this.app.getAlmacen());
            listaComponentes.cargarMotores(this.app.getAlmacen().obtenerMotores());
            listaComponentes.cargarRuedas(this.app.getAlmacen().obtenerRuedas());
            listaComponentes.cargarTapicerias(this.app.getAlmacen().obtenerTapicerias());
            listaComponentes.mostrarVentana();
        });
        btnOperarios.addActionListener(e -> {
            IListaEmpleados operarios = new ListaOperarios(this.parentFrame, this.app.getGestorTrabajadores());
            operarios.mostrarVentana();
        });
        btnPlanificador.addActionListener(e -> {
            IPlanificadorUI planificadorUI = new PlanificadorUI(this.parentFrame, this.app.getGestorCadenasMontaje(), this.app.getAlmacen());
            planificadorUI.mostrarVentana();
        });
        btnCadenasMontaje.addActionListener(e -> {
            CadenaMontajeUI cadenaMontajeUI = new CadenaMontajeUI(this.parentFrame, this.app.getGestorCadenasMontaje().getCadenasMontaje());
            cadenaMontajeUI.mostrarVentana();
        });

        btnSalir.addActionListener(e -> System.exit(0));

        this.add(panelEmpleados);
        this.add(Box.createVerticalStrut(15));
        this.add(panelAlmacen);
        this.add(Box.createVerticalStrut(15));
        this.add(panelOperaciones);
        this.add(Box.createVerticalGlue());
        this.add(btnSalir);
    }

    @Override
    public void init() {
        SwingUtilities.invokeLater(() -> {
            this.parentFrame.add(this);
            this.parentFrame.setVisible(true);
        });
    }

    // Función auxiliar para inicializar el frame principal de la aplicación
    private JFrame initFrame() {
        JFrame frame = new JFrame("Sistema de Gestión de Fábrica de Vehículos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    // Función auxiliar para crear un panel con borde y título
    private JPanel crearSeccion(String titulo) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        p.setOpaque(false);

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), titulo
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 12));
        p.setBorder(border);

        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        return p;
    }
}