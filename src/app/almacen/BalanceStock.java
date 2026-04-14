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

    // Construye la ventana a partir del gestor del almacén.
    public BalanceStock(JFrame frame, IAlmacen almacen) {
        super(frame, "Balance de Stock", true);
        this.almacen = almacen;
        setSize(850, 550);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        add(crearTabla(), BorderLayout.CENTER);
    }

    // Crea la tabla. Utiliza los datos del almacén.
    private JScrollPane crearTabla() {
        // Definimos el modelo
        String[] columnas = {"Componente", "Cantidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Uso de lambda para recorrer el mapa de stock del almacén
        Map<String, Integer> resumen = almacen.obtenerResumenStockComponentes();
        resumen.forEach((nombre, cantidad) -> modelo.addRow(new Object[]{nombre, cantidad}));

        // Crear la tabla a aprtir del modelo definido.
        JTable tabla = new JTable(modelo) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                int stock = (int) getValueAt(row, 1);
                // si el stock es menor que 10, pintamos la fila en rojo.
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