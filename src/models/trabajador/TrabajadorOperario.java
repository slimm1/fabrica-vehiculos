package models.trabajador;

import java.time.LocalDateTime;

/**
 * Operario de montaje. Si ha realizado más de 10 montajes es eficiente
 * (1s por tarea), en caso contrario tarda 3s. Cada llamada a trabajar()
 * incrementa su contador de montajes.
 */
public class TrabajadorOperario extends Trabajador {
    protected int montajesRealizados;

    public TrabajadorOperario(String nombre, String apellidos, String dni, String direccion,
                              String numSeguridadSocial, double salario, LocalDateTime fechaIngreso, int montajesRealizados) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
        this.montajesRealizados = montajesRealizados;
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

    public String getPuesto() {
        if(this.esEficiente()) {
            return "Operario Eficiente";
        }
        return "Operario Estándar";
    }

    public int getMontajesRealizados() {
        return montajesRealizados;
    }

    @Override
    public void trabajar() {
        incrementarMontajes();
    }

    @Override
    public String toString() {
        return "Operario: " + nombre + ", " + apellidos;
    }
}
