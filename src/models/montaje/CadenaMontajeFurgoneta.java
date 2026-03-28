package models.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.Furgoneta;
import models.vehiculo.Vehiculo;

import java.util.List;

public class CadenaMontajeFurgoneta extends CadenaMontaje {

    @Override
    public void avanzar() {
        if (this.vehiculo instanceof Furgoneta) {
            for (Robot robot : robots) {
                robot.montarComponente(vehiculo);
            }
        }
    }
}
