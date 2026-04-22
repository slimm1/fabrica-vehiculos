package models.trabajador;

import java.time.LocalDateTime;

public class TrabajadorGestorPlanta extends Trabajador {

    public TrabajadorGestorPlanta(String nombre, String apellidos, String dni, String direccion,
                        String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    @Override
    public void trabajar() {

    }
}
