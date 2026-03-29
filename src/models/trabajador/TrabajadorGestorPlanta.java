package models.trabajador;

import data.almacen.IAlmacen;

import java.time.LocalDateTime;
import java.util.List;

public class TrabajadorGestorPlanta extends Trabajador {
    private List<Robot> robots;
    private IAlmacen almacen;

    public TrabajadorGestorPlanta(String nombre, String apellidos, String dni, String direccion,
                        String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    public void llamarMecanico() {
    }

    @Override
    public void trabajar() {

    }
}
