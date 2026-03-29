package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;

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
    protected abstract void manejarProblemas();
}