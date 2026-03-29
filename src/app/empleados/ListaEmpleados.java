package app.empleados;

import data.gestion.IGestorTrabajadores;
import models.trabajador.Trabajador;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diálogo Swing que lista todos los trabajadores en una tabla ordenable y
 * filtrable por texto. Permite añadir nuevos empleados abriendo el
 * FormularioNuevoEmpleado y recargando la tabla tras el alta.
 */
public class ListaEmpleados extends JDialog implements IListaEmpleados {
    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBusqueda;
    private IGestorTrabajadores gestorTrabajadores;
    private final String[] tableCols = {
        "Tipo de empleado", "Nombre", "Apellidos", "DNI", "Dirección", "Num. SS", "Salario", "Fecha de ingreso"
    };

    public ListaEmpleados(Frame padre, IGestorTrabajadores gestorTrabajadores) {
        super(padre, "Listado de Trabajadores", true);
        this.gestorTrabajadores = gestorTrabajadores;
        setLayout(new BorderLayout());
        setSize(850, 550);

        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 10));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBusqueda.add(new JLabel("Buscar: "), BorderLayout.WEST);
        txtBusqueda = new JTextField();
        panelBusqueda.add(txtBusqueda, BorderLayout.CENTER);

        modelo = new DefaultTableModel(this.tableCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);
        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
            private void filtrar() {
                String texto = txtBusqueda.getText();
                sorter.setRowFilter(texto.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + texto));
            }
        });

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnAnadir = new JButton("Añadir Nuevo");
        JButton btnCerrar = new JButton("Cerrar");

        btnAnadir.addActionListener(e -> {
            FormularioNuevoEmpleado form = new FormularioNuevoEmpleado((Frame) getParent());
            form.setVisible(true);

            if (form.getTrabajador() != null) {
                this.gestorTrabajadores.agregarTrabajador(form.getTrabajador());
                cargarDatos(this.gestorTrabajadores.obtenerTodosTrabajadores());
            }
        });

        btnCerrar.addActionListener(e -> dispose());

        panelAcciones.add(btnAnadir);
        panelAcciones.add(btnCerrar);

        add(panelBusqueda, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        setLocationRelativeTo(padre);
    }

    @Override
    public void cargarDatos(List<Trabajador> empleados) {
        modelo.setRowCount(0);
        for (Trabajador t : empleados) {
            modelo.addRow(new Object[]{
                t.getPuesto(), t.getNombre(), t.getApellidos(), t.getDni(), t.getDireccion(),
                t.getNumSeguridadSocial(), t.getSalario(), t.getPrintableFechaIngreso()
            });
        }
        ordenar();
    }

    @Override
    public void ordenar() {
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    @Override
    public void mostrarVentana() {
        this.setVisible(true);
    }
}