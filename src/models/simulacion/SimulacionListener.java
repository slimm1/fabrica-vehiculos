package models.simulacion;

import models.vehiculo.EstadoVehiculo;

/**
 * Callback para recibir notificaciones de cada paso de la simulación,
 * con el log del paso, el id del vehículo y su estado actual.
 */
public interface SimulacionListener {
    void onPaso(String log, String idVehiculo, EstadoVehiculo estado);
}