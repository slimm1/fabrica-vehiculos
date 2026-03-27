package models.fabrica.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.BiplazaDeportivo;
import models.vehiculo.Vehiculo;

import java.util.List;

class CadenaMontajeBiplazaDeportivo extends CadenaMontaje {

    public CadenaMontajeBiplazaDeportivo(List<Robot> robots, ConfiguracionCadenaMontaje configuracion) {
        super(robots, configuracion);
    }

    @Override
    public void construirVehiculo(Vehiculo vehiculo) {
        if (vehiculo instanceof BiplazaDeportivo) {
            for (Robot robot : robots) {
                robot.montarComponente(vehiculo);
            }
        }
    }
}
