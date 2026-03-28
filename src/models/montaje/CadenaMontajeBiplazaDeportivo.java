package models.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.BiplazaDeportivo;
import models.vehiculo.Vehiculo;

import java.util.List;

public class CadenaMontajeBiplazaDeportivo extends CadenaMontaje {

    @Override
    public void avanzar() {
        if (this.vehiculo instanceof BiplazaDeportivo) {
            for (Robot robot : robots) {
                robot.montarComponente(vehiculo);
            }
        }
    }
}
