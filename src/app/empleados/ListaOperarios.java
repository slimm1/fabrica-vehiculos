package app.empleados;

import data.gestion.IGestorTrabajadores;
import models.trabajador.Trabajador;
import models.trabajador.TrabajadorOperario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diálogo Swing que lista únicamente los operarios con su número de montajes,
 * ordenados de mayor a menor por esa columna. Permite filtrar por texto.
 */
public class ListaOperarios extends JDialog implements IListaEmpleados {
    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBusqueda;
    private IGestorTrabajadores gestorTrabajadores;
    private final String[] tableCols = {
            "Tipo de operario", "Nombre", "Apellidos", "DNI", "Num. Montajes"
    };

    public ListaOperarios(Frame padre, IGestorTrabajadores gestorTrabajadores) {
        super(padre, "Listado de Trabajadores", true);
        this.gestorTrabajadores = gestorTrabajadores;
        setLayout(new BorderLayout());
        setSize(850, 550);

        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 10));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBusqueda.add(new JLabel("Buscar: "), BorderLayout.WEST);
        txtBusqueda = new JTextField();
        panelBusqueda.add(txtBusqueda, BorderLayout.CENTER);

        this.modelo = new DefaultTableModel(
                new String[]{"Puesto", "Nombre", "Apellidos", "DNI", "Montajes"},
                0
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) return Integer.class;
                return String.class;
            }
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
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelAcciones.add(btnCerrar);

        add(panelBusqueda, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        setLocationRelativeTo(padre);
        cargarDatos(new ArrayList<>(this.gestorTrabajadores.obtenerOperarios()));
    }

    @Override
    public void cargarDatos(List<Trabajador> empleados) {
        modelo.setRowCount(0);
        for (Trabajador t : empleados) {
            TrabajadorOperario to = (TrabajadorOperario) t;
            modelo.addRow(new Object[]{
                to.getPuesto(), to.getNombre(), to.getApellidos(), to.getDni(), to.getMontajesRealizados()
            });
        }
        ordenar();
    }

    @Override
    public void ordenar() {
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    @Override
    public void mostrarVentana() {
        this.setVisible(true);
    }
}
