package models.trabajador.mecanico;

import java.time.LocalDateTime;

public class TrabajadorMecanicoCintaEficiente extends TrabajadorMecanicoCinta {
    private static final int TIEMPO_REPARACION = 1;

    public TrabajadorMecanicoCintaEficiente(String nombre, String apellidos, String dni, String direccion, String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    @Override
    public int getTiempoReparacion() {
        return TIEMPO_REPARACION;
    }

    @Override
    public void trabajar() {

    }
}
