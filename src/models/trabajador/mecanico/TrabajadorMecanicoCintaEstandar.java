package models.trabajador.mecanico;

import java.time.LocalDateTime;

public class TrabajadorMecanicoCintaEstandar extends TrabajadorMecanicoCinta {
    public TrabajadorMecanicoCintaEstandar(String nombre, String apellidos, String dni, String direccion, String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    @Override
    public int getTiempoReparacion() {
        return (int)(Math.random() * 4) + 2;
    }

    @Override
    public void trabajar() {

    }
}
