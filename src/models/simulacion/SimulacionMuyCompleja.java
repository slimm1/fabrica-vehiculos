package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.Robot;
import models.vehiculo.Vehiculo;

import java.util.List;

/**
 * Extiende SimulacionCompleja añadiendo caídas de luz. Antes de cada paso
 * comprueba si toca apagón (administrador restaura en 5s) y luego si toca
 * error de mecánico, resolviendo ambos eventos antes de montar el componente.
 */
public class SimulacionMuyCompleja extends SimulacionCompleja {

    public SimulacionMuyCompleja(GestorCadenasMontaje sistema) {
        super(sistema);
    }

    public void iniciar(CadenaMontaje cadenaMontaje, SimulacionListener listener) {
        // obtener el vehículo actual en la cadena de montaje.
        Vehiculo vehiculo = cadenaMontaje.getVehiculo();
        // Obtener mecánicos y admin
        List<TrabajadorMecanicoCinta> mecanicos = gestorCadenasMontaje.getGestorTrabajadores().obtenerMecanicos();
        TrabajadorAdministrador admin = gestorCadenasMontaje.getGestorTrabajadores().obtenerAdministrador();

        // Mientras el vehículo esté sin terminar, iteramos.
        while (!vehiculo.estaCompleto()) {
            // obtener el robot correspondiente al vehículo
            Robot robot = cadenaMontaje.getRobotPorEstado(vehiculo.getEstadoActual());

            // si hay caída de sistema en este paso...
            if (cadenaMontaje.hayCaidaEnPasoActual()) {
                // detenemos la cadena
                cadenaMontaje.detener();
                // Logar estado del sistema...
                listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
                listener.onPaso("⚡ APAGÓN en " + cadenaMontaje.getTipo()
                                + " — Administrador: " + admin.getNombre()
                                + " restaurando sistema (5s)...",
                        vehiculo.getId(), vehiculo.getEstadoActual());

                // parar la ejecución mientras se restaura el sistema
                try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                // Logar info después de restaurar.
                listener.onPaso("✅ Sistema restaurado por " + admin.getNombre(),
                        vehiculo.getId(), vehiculo.getEstadoActual());

                // Registrar error y continuar
                cadenaMontaje.anadirRegistro(admin);
                cadenaMontaje.reanudar();
            }

            // Error de mecánico
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

            int tiempoPaso = robot.montarComponente(vehiculo);
            tiempoActual += tiempoPaso;
            cadenaMontaje.incrementarPasoActual();

            listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
            vehiculo.cambiarEstado();
            cadenaMontaje.anadirRegistro(robot.getOperario());

            try { Thread.sleep(tiempoPaso * 1000L); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        gestorCadenasMontaje.getAlmacen().agregarVehiculo(vehiculo);
    }
}