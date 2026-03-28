package models.montaje;

import models.trabajador.Trabajador;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import java.time.LocalDateTime;

public abstract class Registro {
    protected Vehiculo vehiculo;
    protected EstadoVehiculo estadoVehiculo;
    protected Trabajador trabajador;
    protected LocalDateTime timestamp;
    protected Componente componente;

    public Registro(Vehiculo vehiculo, Componente componente, Trabajador trabajador, EstadoVehiculo estadoVehiculo) {
        this.vehiculo = vehiculo;
        this.componente = componente;
        this.trabajador = trabajador;
        this.timestamp = LocalDateTime.now();
        this.estadoVehiculo = estadoVehiculo;
    }

    abstract String detalles();

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public EstadoVehiculo getEstadoVehiculo() {
        return estadoVehiculo;
    }
}
