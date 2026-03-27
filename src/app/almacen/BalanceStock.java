package app.almacen;

import data.almacen.IAlmacen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class BalanceStock extends JDialog {
    private IAlmacen almacen;

    public BalanceStock(JFrame frame, IAlmacen almacen) {
        super(frame, "Balance de Stock", true);
        this.almacen = almacen;
        setSize(850, 550);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        add(crearTabla(), BorderLayout.CENTER);
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Componente", "Cantidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        Map<String, Integer> resumen = almacen.obtenerResumenStockComponentes();
        resumen.forEach((nombre, cantidad) -> modelo.addRow(new Object[]{nombre, cantidad}));

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(tabla);
    }

    public void mostrarVentana() {
        setVisible(true);
    }
}