package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.vehiculo.Vehiculo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Simulacion {
    protected GestorCadenasMontaje gestorCadenasMontaje;
    protected int tiempoActual;
    protected boolean enEjecucion;

    public Simulacion(GestorCadenasMontaje gestorCadenasMontaje) {
        this.gestorCadenasMontaje = gestorCadenasMontaje;
        this.tiempoActual = 0;
        this.enEjecucion = false;
    }

    public abstract void iniciar(CadenaMontaje cadenaMontaje, SimulacionListener listener);

    public void detener() {
        enEjecucion = false;
    }

    protected void avanzarCadenas() {
        List<CadenaMontaje> cadenas = gestorCadenasMontaje.getCadenasMontaje();

        for (CadenaMontaje cadena : cadenas) {

        }
    }

    protected abstract void manejarProblemas();

    protected void tick() {
        if (!gestorCadenasMontaje.isSistemaActivo()) {
            return;
        }

        manejarProblemas();
        avanzarCadenas();
        System.out.println("Segundo " + tiempoActual);
    }

    public int getTiempoActual() {
        return tiempoActual;
    }
}