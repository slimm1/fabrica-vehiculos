package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.operario.Robot;
import models.vehiculo.Vehiculo;

public class SimulacionSimple extends Simulacion {

    public SimulacionSimple(GestorCadenasMontaje gestorCadenasMontaje) {
        super(gestorCadenasMontaje);
    }

    public void iniciar(CadenaMontaje cadenaMontaje) {
        Vehiculo vehiculo = cadenaMontaje.getVehiculo();
        while (!vehiculo.estaCompleto()) {
            Robot robot = cadenaMontaje.getRobotPorEstado(vehiculo.getEstadoActual());
            int tiempoPaso = robot.montarComponente(vehiculo);
            cadenaMontaje.anadirRegistro();
            tiempoActual += tiempoPaso;
            try {
                Thread.sleep(tiempoPaso);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void manejarProblemas() {
        // No hay problemas en simulación simple
    }
}