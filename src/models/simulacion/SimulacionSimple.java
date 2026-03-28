package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.operario.Robot;
import models.vehiculo.Vehiculo;

public class SimulacionSimple extends Simulacion {

    public SimulacionSimple(GestorCadenasMontaje gestorCadenasMontaje) {
        super(gestorCadenasMontaje);
    }

    public void iniciar(CadenaMontaje cadenaMontaje, SimulacionListener listener) {
        Vehiculo vehiculo = cadenaMontaje.getVehiculo();
        while (!vehiculo.estaCompleto()) {
            Robot robot = cadenaMontaje.getRobotPorEstado(vehiculo.getEstadoActual());
            int tiempoPaso = robot.montarComponente(vehiculo);
            cadenaMontaje.anadirRegistro();
            tiempoActual += tiempoPaso;

            if (listener != null) {
                listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
            }

            try {
                Thread.sleep(tiempoPaso * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        gestorCadenasMontaje.getAlmacen().agregarVehiculo(vehiculo);

    }

    @Override
    protected void manejarProblemas() {
        // No hay problemas en simulación simple
    }
}