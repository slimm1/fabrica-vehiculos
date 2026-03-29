package app.almacen;

import data.almacen.IAlmacen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Map;

/**
 * Diálogo Swing que muestra el stock de todos los componentes en una tabla.
 * Las filas con stock inferior a 10 se resaltan en rojo.
 */
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

        JTable tabla = new JTable(modelo) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                int stock = (int) getValueAt(row, 1);
                c.setBackground(stock < 10 ? new Color(255, 180, 180) : getBackground());
                return c;
            }
        };        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(tabla);
    }

    public void mostrarVentana() {
        setVisible(true);
    }
}