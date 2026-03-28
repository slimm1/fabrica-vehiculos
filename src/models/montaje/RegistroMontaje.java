package models.montaje;

import models.vehiculo.EstadoVehiculo;
import models.vehiculo.componente.Componente;

import java.time.LocalDateTime;

public class RegistroMontaje {
    private String idVehiculo;
    private String tipoVehiculo;
    private EstadoVehiculo estado;
    private LocalDateTime timestamp;
    private Componente componente;

    public RegistroMontaje(String idVehiculo, String tipoVehiculo, EstadoVehiculo estado, Componente componente) {
        this.idVehiculo = idVehiculo;
        this.tipoVehiculo = tipoVehiculo;
        this.estado = estado;
        this.componente = componente;
        this.timestamp = LocalDateTime.now();
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Componente getComponente() {
        return componente;
    }
}