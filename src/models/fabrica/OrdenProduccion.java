package models.fabrica;

import models.vehiculo.Vehiculo;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

public class OrdenProduccion {
    private Class<? extends Vehiculo> tipoVehiculo;
    private int cantidad;
    private Class<? extends Motor> motorTipo;
    private Class<? extends Tapiceria> tapiceriaTipo;
    private Class<? extends Rueda> ruedaTipo;
    private String id;

    public OrdenProduccion(String id, Class<? extends Vehiculo> tipoVehiculo, int cantidad,
                           Class<? extends Motor> motorTipo, Class<? extends Tapiceria> tapiceriaTipo,
                           Class<? extends Rueda> ruedaTipo) {
        this.id = id;
        this.tipoVehiculo = tipoVehiculo;
        this.cantidad = cantidad;
        this.motorTipo = motorTipo;
        this.tapiceriaTipo = tapiceriaTipo;
        this.ruedaTipo = ruedaTipo;
    }

    public String getId() {
        return id;
    }

    public Class<? extends Vehiculo> getTipoVehiculo() {
        return tipoVehiculo;
    }

    public int getCantidad() {
        return cantidad;
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
