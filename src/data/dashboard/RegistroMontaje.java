package data.dashboard;

import models.vehiculo.EstadoVehiculo;

import java.time.LocalDateTime;

public class RegistroMontaje {
    private String idVehiculo;
    private String tipoVehiculo;
    private EstadoVehiculo estado;
    private LocalDateTime timestamp;
    private String componente;

    public RegistroMontaje(String idVehiculo, String tipoVehiculo, EstadoVehiculo estado, String componente) {
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

    public String getComponente() {
        return componente;
    }
}