package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.Robot;
import models.vehiculo.Vehiculo;

import java.util.List;

public class SimulacionCompleja extends Simulacion {

    public SimulacionCompleja(GestorCadenasMontaje sistema) {
        super(sistema);
    }

    public void iniciar(CadenaMontaje cadenaMontaje, SimulacionListener listener) {
        Vehiculo vehiculo = cadenaMontaje.getVehiculo();
        List<TrabajadorMecanicoCinta> mecanicos = gestorCadenasMontaje.getGestorTrabajadores().obtenerMecanicos();

        while (!vehiculo.estaCompleto()) {
            Robot robot = cadenaMontaje.getRobotPorEstado(vehiculo.getEstadoActual());
            int tiempoPaso = robot.montarComponente(vehiculo);
            cadenaMontaje.anadirRegistro(robot.getOperario());
            tiempoActual += tiempoPaso;

            if (cadenaMontaje.hayErrorEnPasoActual()) {
                cadenaMontaje.detener();
                TrabajadorMecanicoCinta mecanico = mecanicos.get((int)(Math.random() * mecanicos.size()));
                int tiempoReparacion = mecanico.getTiempoReparacion();
                mecanico.incrementarReparaciones();
                tiempoActual += tiempoReparacion;
                cadenaMontaje.anadirRegistro(mecanico);
                if (listener != null) {
                    listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
                    listener.onPaso("⚠ ERROR en " + cadenaMontaje.getTipo()
                                    + " — Reparando con " + mecanico.getNombre()
                                    + " (" + mecanico.getPuesto() + ") — "
                                    + tiempoReparacion + "s",
                            vehiculo.getId(), vehiculo.getEstadoActual());
                }

                try { Thread.sleep(tiempoReparacion * 1000L); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                cadenaMontaje.reanudar();
            }

            cadenaMontaje.incrementarPasoActual();

            if (listener != null) {
                listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
            }

            try { Thread.sleep(tiempoPaso * 1000L); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        gestorCadenasMontaje.getAlmacen().agregarVehiculo(vehiculo);
    }

    @Override
    protected void manejarProblemas() {
        // Gestionado inline en iniciar()
    }
}
