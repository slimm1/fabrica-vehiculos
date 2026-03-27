package models.fabrica.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.Turismo;
import models.vehiculo.Vehiculo;

import java.util.List;

public class CadenaMontajeTurismo extends CadenaMontaje {

    public CadenaMontajeTurismo(List<Robot> robots, ConfiguracionCadenaMontaje configuracion) {
        super(robots, configuracion);
    }

    @Override
    public void construirVehiculo(Vehiculo vehiculo) {
        if (vehiculo instanceof Turismo) {
            for (Robot robot : robots) {
                robot.montarComponente(vehiculo);
            }
        }
    }
}