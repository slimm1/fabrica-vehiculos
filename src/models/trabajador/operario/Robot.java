package models.trabajador.operario;

import models.montaje.CadenaMontaje;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;

import java.util.UUID;

public class Robot {
    private String idRobot;
    private TrabajadorOperario operario;
    private EstadoVehiculo estadoVehiculo;
    private CadenaMontaje cadenaMontaje;

    public Robot(EstadoVehiculo estadoVehiculo, CadenaMontaje cadenaMontaje) {
        this.idRobot = UUID.randomUUID().toString();
        this.estadoVehiculo  = estadoVehiculo;
        this.cadenaMontaje = cadenaMontaje;
    }

    public int montarComponente(Vehiculo vehiculo) {
        operario.trabajar();
        vehiculo.cambiarEstado();
        return operario.getTiempoTarea();
    }

    public TrabajadorOperario getOperario() {
        return operario;
    }

    public void setOperario(TrabajadorOperario operario) {
        this.operario = operario;
    }

    public EstadoVehiculo getEstadoVehiculo() {
        return estadoVehiculo;
    }

    @Override
    public String toString() {
        return "Robot de " + this.estadoVehiculo.getNombre() + ". \n" +
                this.operario.toString();
    }
}
