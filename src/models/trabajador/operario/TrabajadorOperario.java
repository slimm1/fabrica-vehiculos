package models.trabajador.operario;

import models.trabajador.Trabajador;

import java.time.LocalDateTime;

public abstract class TrabajadorOperario extends Trabajador {
    protected int montajesRealizados;

    public TrabajadorOperario(String nombre, String apellidos, String dni, String direccion,
                              String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
        this.montajesRealizados = 0;
    }

    public boolean esEficiente() {
        return montajesRealizados > 10;
    }

    public int getTiempoTarea() {
        return esEficiente() ? 1 : 3;
    }

    public void incrementarMontajes() {
        montajesRealizados++;
    }
}
