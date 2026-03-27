package models.trabajador.operario;

import java.time.LocalDateTime;

public class TrabajadorOperarioEficiente extends TrabajadorOperario {

    public TrabajadorOperarioEficiente(String nombre, String apellidos, String dni, String direccion,
                              String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
        this.montajesRealizados = 0;
    }

    @Override
    public void trabajar() {
        incrementarMontajes();
    }
}
