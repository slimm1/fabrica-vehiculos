package models.fabrica.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.Furgoneta;
import models.vehiculo.Vehiculo;

import java.util.List;

public class CadenaMontajeFurgoneta extends CadenaMontaje {

    public CadenaMontajeFurgoneta(List<Robot> robots, ConfiguracionCadenaMontaje configuracion) {
        super(robots, configuracion);
    }

    @Override
    public void construirVehiculo(Vehiculo vehiculo) {
        if (vehiculo instanceof Furgoneta) {
            for (Robot robot : robots) {
                robot.montarComponente(vehiculo);
            }
        }
    }
}
