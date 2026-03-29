package models.trabajador;

import java.time.LocalDateTime;

/**
 * Mecánico de cinta. Su tiempo de reparación depende de si es eficiente
 * (más de 20 reparaciones realizadas): 1s si lo es, entre 2 y 5s si no.
 * Cada reparación incrementa su contador.
 */
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


    public int getTiempoReparacion() {
        if(this.esEficiente()) {
            return 1;
        }
        return (int)(Math.random() * 4) + 2;
    }

    @Override
    public void trabajar() {
        incrementarReparaciones();
    }

    public String getPuesto() {
        if(this.esEficiente()) {
            return "Mecánico Eficiente";
        }
        return "Mecánico Estándar";
    }
}
