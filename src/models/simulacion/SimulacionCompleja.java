package models.simulacion;

import data.gestion.GestorCadenasMontaje;
import models.montaje.CadenaMontaje;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.vehiculo.Vehiculo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulacionCompleja extends Simulacion {
    private Map<CadenaMontaje, Integer> problemasReparados;

    public SimulacionCompleja(GestorCadenasMontaje sistema) {
        super(sistema);
        this.problemasReparados = new HashMap<>();
    }

    @Override
    public void iniciar(CadenaMontaje cadenaMontaje) {

    }

    @Override
    protected void manejarProblemas() {
        List<CadenaMontaje> cadenas = gestorCadenasMontaje.getCadenasMontaje();

        for (CadenaMontaje cadena : cadenas) {
            if (Math.random() < 0.2) {
                generarProblema(cadena);
            }
        }
    }

    private void generarProblema(CadenaMontaje cadena) {
        cadena.detener();
        List<TrabajadorMecanicoCinta> mecanicos = gestorCadenasMontaje.getGestorTrabajadores().obtenerMecanicos();

        if (!mecanicos.isEmpty()) {
            TrabajadorMecanicoCinta mecanico = mecanicos.get(new Random().nextInt(mecanicos.size()));
            mecanico.trabajar();
            int reparaciones = problemasReparados.getOrDefault(cadena, 0);
            problemasReparados.put(cadena, reparaciones + 1);

            try {
                Thread.sleep(mecanico.getTiempoReparacion() * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            cadena.reanudar();
        }
    }
}
