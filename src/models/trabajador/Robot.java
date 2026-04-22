package models.trabajador;

import models.montaje.CadenaMontaje;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;

import java.util.UUID;

/**
 * Robot asociado a un estado de montaje (chasis, ruedas, tapicería o motor).
 * Delega el trabajo en su operario asignado, avanza el estado del vehículo
 * y devuelve el tiempo empleado en el paso.
 */
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
