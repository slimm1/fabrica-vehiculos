package models.trabajador.mecanico;

import models.trabajador.Trabajador;

import java.time.LocalDateTime;

public abstract class TrabajadorMecanicoCinta extends Trabajador {
    protected int reparacionesRealizadas;

    public TrabajadorMecanicoCinta(String nombre, String apellidos, String dni, String direccion,
                          String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
        this.reparacionesRealizadas = 0;
    }

    public boolean esEficiente() {
        return reparacionesRealizadas > 20;
    }

    public void incrementarReparaciones() {
        reparacionesRealizadas++;
    }

    public abstract int getTiempoReparacion();
}
