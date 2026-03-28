package models.montaje;

import models.trabajador.Trabajador;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import java.time.LocalDateTime;

public class RegistroError extends Registro {

    public RegistroError(Vehiculo vehiculo, Componente componente, Trabajador trabajador, EstadoVehiculo estadoVehiculo) {
        super(vehiculo, componente, trabajador, estadoVehiculo);
    }

    @Override
    String detalles() {
        return "";
    }
}
