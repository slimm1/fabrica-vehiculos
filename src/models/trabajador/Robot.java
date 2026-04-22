package models.trabajador;

import models.montaje.CadenaMontaje;
import models.vehiculo.EstadoVehiculo;

/**
 * Robot asociado a un estado de montaje (chasis, ruedas, tapicería o motor).
 * Delega el trabajo en su operario asignado y devuelve el tiempo empleado en el paso.
 */
public class Robot {
    private TrabajadorOperario operario;
    private EstadoVehiculo estadoVehiculo;

    public Robot(EstadoVehiculo estadoVehiculo, CadenaMontaje cadenaMontaje) {
        this.estadoVehiculo  = estadoVehiculo;
    }

    public int montarComponente() {
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
