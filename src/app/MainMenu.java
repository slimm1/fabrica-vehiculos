package app;

import app.almacen.BalanceStock;
import app.almacen.componentes.IListaComponentes;
import app.almacen.componentes.ListaComponentes;
import app.empleados.IListaEmpleados;
import app.empleados.ListaEmpleados;
import app.almacen.vehiculos.IListaVehiculos;
import app.almacen.vehiculos.ListaVehiculos;
import app.empleados.ListaOperarios;
import app.planificador.IPlanificadorUI;
import app.planificador.PlanificadorUI;
import data.App;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainMenu extends JPanel implements IMainMenu {
    private JFrame parentFrame;
    private App app;

    public MainMenu(JFrame parentFrame, App app) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(Color.LIGHT_GRAY);

        this.parentFrame = parentFrame;
        this.app = app;
        configurarAcciones();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void configurarAcciones() {
        JPanel panelEmpleados = crearSeccion("Gestión de Personal");
        JButton btnEmpleados = new JButton("Gestión de empleados");
        JButton btnOperarios = new JButton("Operarios");
        panelEmpleados.add(btnEmpleados);
        panelEmpleados.add(btnOperarios);

        JPanel panelAlmacen = crearSeccion("Inventario y Almacén");
        JButton btnVehiculos = new JButton("Vehículos");
        JButton btnComponentes = new JButton("Componentes");
        panelAlmacen.add(btnVehiculos);
        panelAlmacen.add(btnComponentes);

        JPanel panelOperaciones = crearSeccion("Producción");
        JButton btnPlanificador = new JButton("Planificador");
        JButton btnCadenasMontaje = new JButton("Cadenas de montaje");
        panelOperaciones.add(btnPlanificador);
        panelOperaciones.add(btnCadenasMontaje);

        JButton btnSalir = new JButton("Salir de la Aplicación");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setForeground(Color.RED);

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

        btnSalir.addActionListener(e -> System.exit(0));

        this.add(panelEmpleados);
        this.add(Box.createVerticalStrut(15));
        this.add(panelAlmacen);
        this.add(Box.createVerticalStrut(15));
        this.add(panelOperaciones);
        this.add(Box.createVerticalGlue());
        this.add(btnSalir);
    }

    /**
     * Método auxiliar para crear un panel con borde y título
     */
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

    @Override
    public void init() {
        this.revalidate();
        this.repaint();
    }
}