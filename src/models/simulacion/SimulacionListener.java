package models.simulacion;

import models.vehiculo.EstadoVehiculo;

public interface SimulacionListener {
    void onPaso(String log, String idVehiculo, EstadoVehiculo estado);
}