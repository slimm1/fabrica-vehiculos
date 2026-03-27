package models.fabrica.montaje;

import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

public class ConfiguracionCadenaMontaje {
    private Class<? extends Motor> motorTipo;
    private Class<? extends Tapiceria> tapiceriaTipo;
    private Class<? extends Rueda> ruedaTipo;

    public ConfiguracionCadenaMontaje(Class<? extends Motor> motorTipo,
                               Class<? extends Tapiceria> tapiceriaTipo,
                               Class<? extends Rueda> ruedaTipo) {
        this.motorTipo = motorTipo;
        this.tapiceriaTipo = tapiceriaTipo;
        this.ruedaTipo = ruedaTipo;
    }

    public Class<? extends Motor> getMotorTipo() {
        return motorTipo;
    }

    public Class<? extends Tapiceria> getTapiceriaTipo() {
        return tapiceriaTipo;
    }

    public Class<? extends Rueda> getRuedaTipo() {
        return ruedaTipo;
    }
}
