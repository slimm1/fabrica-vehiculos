package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.Robot;
import models.vehiculo.Vehiculo;

import java.util.List;

public class SimulacionMuyCompleja extends SimulacionCompleja {
    private int caidas;

    public SimulacionMuyCompleja(GestorCadenasMontaje sistema) {
        super(sistema);
        this.caidas = 0;
    }

    public void iniciar(CadenaMontaje cadenaMontaje, SimulacionListener listener) {
        Vehiculo vehiculo = cadenaMontaje.getVehiculo();
        List<TrabajadorMecanicoCinta> mecanicos = gestorCadenasMontaje.getGestorTrabajadores().obtenerMecanicos();
        TrabajadorAdministrador admin = gestorCadenasMontaje.getGestorTrabajadores().obtenerAdministrador();

        while (!vehiculo.estaCompleto()) {
            Robot robot = cadenaMontaje.getRobotPorEstado(vehiculo.getEstadoActual());
            cadenaMontaje.anadirRegistro(robot.getOperario());

            if (cadenaMontaje.hayCaidaEnPasoActual()) {
                cadenaMontaje.detener();
                if (listener != null) {
                    listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
                    listener.onPaso("⚡ APAGÓN en " + cadenaMontaje.getTipo()
                                    + " — Administrador: " + admin.getNombre()
                                    + " restaurando sistema (5s)...",
                            vehiculo.getId(), vehiculo.getEstadoActual());
                }
                try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                if (listener != null) {
                    listener.onPaso("✅ Sistema restaurado por " + admin.getNombre(),
                            vehiculo.getId(), vehiculo.getEstadoActual());
                }
                cadenaMontaje.anadirRegistro(admin);
                cadenaMontaje.reanudar();
            }

            // Error de mecánico
            if (cadenaMontaje.hayErrorEnPasoActual()) {
                cadenaMontaje.detener();
                TrabajadorMecanicoCinta mecanico = mecanicos.get((int)(Math.random() * mecanicos.size()));
                int tiempoReparacion = mecanico.getTiempoReparacion();
                mecanico.incrementarReparaciones();
                cadenaMontaje.anadirRegistro(mecanico);
                tiempoActual += tiempoReparacion;
                if (listener != null) {
                    listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
                    listener.onPaso("⚠ ERROR en " + cadenaMontaje.getTipo()
                                    + " — Reparando con " + mecanico.getNombre()
                                    + " " + mecanico.getApellidos()
                                    + " (" + mecanico.getPuesto() + ") — " + tiempoReparacion + "s",
                            vehiculo.getId(), vehiculo.getEstadoActual());
                }
                try { Thread.sleep(tiempoReparacion * 1000L); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                cadenaMontaje.reanudar();
            }

            int tiempoPaso = robot.montarComponente(vehiculo);
            tiempoActual += tiempoPaso;
            cadenaMontaje.incrementarPasoActual();

            if (listener != null) {
                listener.onPaso(cadenaMontaje.toString(), vehiculo.getId(), vehiculo.getEstadoActual());
            }

            try { Thread.sleep(tiempoPaso * 1000L); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        gestorCadenasMontaje.getAlmacen().agregarVehiculo(vehiculo);
    }

    @Override
    protected void manejarProblemas() {
        if (Math.random() < 0.1 && caidas < 1) {
            generarCaidaSistema();
            caidas++;
        }

        super.manejarProblemas();
    }

    private void generarCaidaSistema() {
        System.out.println("¡APAGÓN! Sistema en pausa...");
        gestorCadenasMontaje.manejarError();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        gestorCadenasMontaje.restaurarSistema();
        System.out.println("Sistema restaurado");
    }
}