package app.almacen.vehiculos;

import data.almacen.IAlmacen;
import models.vehiculo.Vehiculo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListaVehiculos extends JDialog implements IListaVehiculos {
    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBusqueda;
    private JTextField txtComponente;
    private IAlmacen almacen;
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private final String[] tableCols = {
            "ID", "Tipo", "Color", "Num. Plazas", "Tara", "Peso max. autorizado"
    };

    public ListaVehiculos(Frame padre, IAlmacen almacen) {
        super(padre, "Listado de Vehículos", true);
        this.almacen = almacen;
        setLayout(new BorderLayout());
        setSize(850, 550);

        // ── Panel de filtros ──────────────────────────────────────────────────
        JPanel panelFiltros = new JPanel(new GridLayout(2, 2, 10, 5));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        txtBusqueda = new JTextField();
        txtComponente = new JTextField();

        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(new JLabel("Filtrar por componente:"));
        panelFiltros.add(txtBusqueda);
        panelFiltros.add(txtComponente);

        // ── Tabla ─────────────────────────────────────────────────────────────
        modelo = new DefaultTableModel(this.tableCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) abrirDetalleVehiculo();
            }
        });
        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        // ── Listeners ─────────────────────────────────────────────────────────
        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
        };
        txtBusqueda.getDocument().addDocumentListener(dl);
        txtComponente.getDocument().addDocumentListener(dl);

        // ── Acciones ──────────────────────────────────────────────────────────
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelAcciones.add(btnCerrar);

        add(panelFiltros, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        setLocationRelativeTo(padre);
    }

    private void filtrar() {
        String textoBusqueda = txtBusqueda.getText().trim().toLowerCase();
        String textoComponente = txtComponente.getText().trim().toLowerCase();

        // Filtro por componente: reconstruimos la tabla con los vehículos que lo tienen
        if (!textoComponente.isEmpty()) {
            List<Vehiculo> filtrados = vehiculos.stream()
                    .filter(v -> v.getComponentes().stream()
                            .anyMatch(c -> c.getNombre().toLowerCase().contains(textoComponente)))
                    .collect(Collectors.toList());
            recargarTabla(filtrados);
        } else {
            recargarTabla(vehiculos);
        }

        // Filtro general sobre la tabla ya recargada
        sorter.setRowFilter(textoBusqueda.isEmpty() ? null : RowFilter.regexFilter("(?i)" + textoBusqueda));
    }

    private void recargarTabla(List<Vehiculo> lista) {
        modelo.setRowCount(0);
        for (Vehiculo v : lista) {
            modelo.addRow(new Object[]{
                    v.getId(), v.getTipo(), v.getColor(),
                    v.getNumPlazas(), v.getTara(), v.getPesoMaximoAutorizado()
            });
        }
    }

    @Override
    public void cargarDatos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
        recargarTabla(vehiculos);
    }

    @Override
    public void mostrarVentana() {
        setVisible(true);
    }

    private void abrirDetalleVehiculo() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            int modeloIndice = tabla.convertRowIndexToModel(filaSeleccionada);
            String idVehiculo = (String) modelo.getValueAt(modeloIndice, 0);
            new DetalleVehiculo((Frame) getParent(), idVehiculo, almacen).setVisible(true);
        }
    }
}