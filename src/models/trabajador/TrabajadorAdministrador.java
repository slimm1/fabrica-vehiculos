package models.trabajador;

import models.montaje.CadenaMontaje;

import java.time.LocalDateTime;
import java.util.List;

public class TrabajadorAdministrador extends Trabajador {
    private static final int TIEMPO_RESTAURAR_GESTION = 2; // segundos
    private static final int TIEMPO_RESTAURAR_CADENA = 3; // segundos
    private boolean sistemaActivo;

    public TrabajadorAdministrador(String nombre, String apellidos, String dni, String direccion,
                                String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
        this.sistemaActivo = true;
    }

    public void manejarError() {
        sistemaActivo = false;
    }

    public void restaurarSistema(TrabajadorGestorPlanta gestorPlanta, List<CadenaMontaje> cadenas) {
        restaurarGestionFabrica();
        restaurarCadenasMontaje(cadenas);
        sistemaActivo = true;
    }

    private void restaurarGestionFabrica() {
        try {
            Thread.sleep(TIEMPO_RESTAURAR_GESTION * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void restaurarCadenasMontaje(List<CadenaMontaje> cadenas) {
        try {
            Thread.sleep(TIEMPO_RESTAURAR_CADENA * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        cadenas.forEach(CadenaMontaje::reanudar);
    }

    public boolean isSistemaActivo() {
        return sistemaActivo;
    }

    @Override
    public void trabajar() {
        // El administrador no trabaja en cadena de montaje
    }
}
