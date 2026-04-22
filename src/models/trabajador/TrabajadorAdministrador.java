package models.trabajador;

import java.time.LocalDateTime;

public class TrabajadorAdministrador extends Trabajador {

    public TrabajadorAdministrador(String nombre, String apellidos, String dni, String direccion,
                                String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    @Override
    public void trabajar() {
        // El administrador no trabaja en cadena de montaje
    }
}
