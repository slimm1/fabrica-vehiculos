package models.trabajador;

import data.dashboard.IDashboard;
import data.almacen.IAlmacen;
import models.trabajador.operario.Robot;

import java.time.LocalDateTime;
import java.util.List;

public class TrabajadorGestorPlanta extends Trabajador {
    private List<Robot> robots;
    private IAlmacen almacen;
    private IDashboard dashboard;

    public TrabajadorGestorPlanta(String nombre, String apellidos, String dni, String direccion,
                        String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    public void monitorizar() {
        dashboard.verificarErrores();
    }

    public void llamarMecanico() {
        // Llamar a mecánico de cinta para reparación
    }

    @Override
    public void trabajar() {
        monitorizar();
    }
}
