package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.Robot;
import models.vehiculo.Vehiculo;

import java.util.List;

/**
 * Simulación con errores de mecánico. En los pasos marcados como erróneos,
 * detiene la cadena, asigna un mecánico aleatorio para reparar (sumando su
 * tiempo al total) y la reanuda antes de continuar.
 */
public class SimulacionCompleja extends Simulacion {

    public SimulacionCompleja(GestorCadenasMontaje sistema) {
        super(sistema);
    }

    public void iniciar(CadenaMontaje cadenaMontaje, SimulacionListener listener) {
        // Obtener vehículo actual en la cadena de montaje
        Vehiculo vehiculo = cadenaMontaje.getVehiculo();
        // obtener mecánicos
        List<TrabajadorMecanicoCinta> mecanicos = gestorCadenasMontaje.getGestorTrabajadores().obtenerMecanicos();

        // Mientras el estado del vehículo no este completo, iteramos
        while (!vehiculo.estaCompleto()) {
            // recuperar el robot correspondiente al paso actual de la cadena a partir del estado del vehículo
            Robot robot = cadenaMontaje.getRobotPorEstado(vehiculo.getEstadoActual());
            // Si en este paso había programado un error
            if (cadenaMontaje.hayErrorEnPasoActual()) {
                // se detiene la cadena de montaje...
                cadenaMontaje.detener();
                // cogemos un mecánico al azar
                TrabajadorMecanicoCinta mecanico = mecanicos.get((int)(Math.random() * mecanicos.size()));
                // Le hacemos trabajar, incrementando su experiencia
                int tiempoReparacion = mecanico.getTiempoReparacion();
                mecanico.trabajar();
                // Incremental el tiempo actual de la simulacion con el tiempo que tardó el mecánico en reparar.
                tiempoActual += tiempoReparacion;
                // Añadimos el registro de error.
                cadenaMontaje.anadirRegistro(mecanico);
                // Ejecutamos el listener para logar el error. Los elementos de la UI que escuchan, actualizarán sus componentes con estos logs.
                listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
                listener.onPaso("⚠ ERROR en " + cadenaMontaje.getTipo()
                                + " — Reparando con " + mecanico.getNombre()
                                + " (" + mecanico.getPuesto() + ") — "
                                + tiempoReparacion + "s",
                        vehiculo.getId(), vehiculo.getEstadoActual());

                // La ejecución se detiene durante el tiempo de la reparación...
                try { Thread.sleep(tiempoReparacion * 1000L); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                cadenaMontaje.reanudar();
            }
            // Después de manejar el error, continuamos con la construcción.
            // Recuperar el tiempo teórico del paso. Esta llamada también incrementa la experiencia del operario.
            int tiempoPaso = robot.montarComponente();
            tiempoActual += tiempoPaso;
            cadenaMontaje.incrementarPasoActual();
            // Logar estado en el listener
            listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
            vehiculo.cambiarEstado();
            cadenaMontaje.anadirRegistro(robot.getOperario());
            // dormir el hilo mientras se ensambla el componente
            try { Thread.sleep(tiempoPaso * 1000L); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        // Finalmente, añadimos el vehículo al almacén.
        gestorCadenasMontaje.getAlmacen().agregarVehiculo(vehiculo);
    }
}
