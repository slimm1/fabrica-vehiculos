package models.planificador.simulacion;

import data.gestion.SistemaGestionFabrica;
import models.planificador.Planificador;

public class SimulacionSimple extends Planificador {

    public SimulacionSimple(SistemaGestionFabrica sistema) {
        super(sistema);
    }

    @Override
    protected void manejarProblemas() {
        // No hay problemas en simulación simple
    }
}