package models.trabajador.operario;

import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;

public class Robot {
    private String idRobot;
    private TrabajadorOperario operario;
    private EstadoVehiculo estadoVehiculo;

    public Robot(String idRobot, TrabajadorOperario operario, EstadoVehiculo estadoVehiculo) {
        this.idRobot = idRobot;
        this.operario = operario;
        this.estadoVehiculo  = estadoVehiculo;
    }

    public int montarComponente(Vehiculo vehiculo) {
        operario.trabajar();
        vehiculo.cambiarEstado();
        return operario.getTiempoTarea();
    }
}
