package models.trabajador.mecanico;

import models.trabajador.Trabajador;

import java.time.LocalDateTime;

public class TrabajadorMecanicoCinta extends Trabajador {
    private int reparacionesRealizadas;

    public TrabajadorMecanicoCinta(String nombre, String apellidos, String dni, String direccion,
                          String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
        this.reparacionesRealizadas = (int)(Math.random() * 30) + 1;;
    }

    public boolean esEficiente() {
        return this.reparacionesRealizadas > 20;
    }

    public void incrementarReparaciones() {
        this.reparacionesRealizadas++;
    }

    public int getReparacionesRealizadas() {
        return this.reparacionesRealizadas;
    }

    public int getTiempoReparacion() {
        if(this.esEficiente()) {
            return 1;
        }
        return (int)(Math.random() * 4) + 2;
    }

    @Override
    public void trabajar() {

    }

    public String getPuesto() {
        if(this.esEficiente()) {
            return "Mecánico Eficiente";
        }
        return "Mecánico Estándar";
    }
}
