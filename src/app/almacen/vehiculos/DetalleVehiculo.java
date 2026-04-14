package app.almacen.vehiculos;

import data.almacen.IAlmacen;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Diálogo Swing que muestra los datos técnicos de un vehículo concreto
 * (id, tipo, color, estado) y la tabla de componentes instalados,
 * consultando el almacén por id.
 */
public class DetalleVehiculo extends JDialog {
    private Vehiculo vehiculo;

    // Inicializa la ventana con el ID del vehículo y el gestor de almacén
    public DetalleVehiculo(Frame padre, String idVehiculo, IAlmacen almacen) {
        super(padre, "Detalles Técnicos - " + idVehiculo, true);

        // Recupera el objeto Vehículo del almacén
        this.vehiculo = almacen.obtenerVehiculo(idVehiculo);

        // Si no se encuentra, mostrar un diálogo
        if (vehiculo == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el vehículo seleccionado.");
            dispose();
            return;
        }

        setLayout(new BorderLayout(15, 15));
        setSize(700, 450);
        setLocationRelativeTo(padre);

        // Configuraciones del panel de información.
        JPanel panelInfo = new JPanel(new GridLayout(0, 2, 5, 5));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Vehículo"));
        panelInfo.add(new JLabel("ID:"));
        panelInfo.add(new JLabel(vehiculo.getId()));
        panelInfo.add(new JLabel("Modelo:"));
        panelInfo.add(new JLabel(vehiculo.getTipo()));
        panelInfo.add(new JLabel("Color:"));
        panelInfo.add(new JLabel(vehiculo.getColor()));
        panelInfo.add(new JLabel("Estado:"));
        panelInfo.add(new JLabel(vehiculo.getEstadoActual().toString()));

        // Table model para los componentes del vehículo
        String[] columnas = {"Tipo de Componente", "Detalles / Especificaciones"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // Por cada componente en el vehículo, añado una fila a la tabla de componentes
        for (Componente c : vehiculo.getComponentes()) {
            modeloTabla.addRow(new Object[]{
                    c.getNombre(),
                    c.toString()
            });
        }

        JTable tablaComp = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaComp);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Componentes Instalados"));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.add(btnCerrar);

        add(panelInfo, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}