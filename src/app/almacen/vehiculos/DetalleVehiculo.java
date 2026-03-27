package app.almacen.vehiculos;

import data.almacen.IAlmacen;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DetalleVehiculo extends JDialog {
    private Vehiculo vehiculo;

    public DetalleVehiculo(Frame padre, String idVehiculo, IAlmacen almacen) {
        super(padre, "Detalles Técnicos - " + idVehiculo, true);

        this.vehiculo = almacen.obtenerVehiculo(idVehiculo);

        if (vehiculo == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el vehículo seleccionado.");
            dispose();
            return;
        }

        setLayout(new BorderLayout(15, 15));
        setSize(700, 450);
        setLocationRelativeTo(padre);

        // --- 1. CABECERA: Info General ---
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

        String[] columnas = {"Tipo de Componente", "Detalles / Especificaciones"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Componente c : vehiculo.getComponentes()) {
            modeloTabla.addRow(new Object[]{
                    c.getNombre(),
                    c.toString() // Asegúrate de tener el toString() en tus clases Componente
            });
        }

        JTable tablaComp = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaComp);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Componentes Instalados"));

        // --- 3. BOTÓN CERRAR ---
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.add(btnCerrar);

        // --- MONTAJE ---
        add(panelInfo, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}