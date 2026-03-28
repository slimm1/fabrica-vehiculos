package models.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.Turismo;
import models.vehiculo.Vehiculo;

import java.util.List;

public class CadenaMontajeTurismo extends CadenaMontaje {

    @Override
    public void avanzar() {
        if (this.vehiculo instanceof Turismo) {
            for (Robot robot : robots) {
                robot.montarComponente(vehiculo);
            }
        }
    }
}