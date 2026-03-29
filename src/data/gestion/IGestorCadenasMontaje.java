package data.gestion;

import exception.ColaVehiculosVacia;
import models.montaje.CadenaMontaje;
import models.simulacion.SimulacionListener;
import models.vehiculo.Vehiculo;

import java.util.List;

/**
 * Contrato para la gestión de cadenas de montaje: encolar vehículos e
 * iniciar los tres modos de simulación (simple, compleja, muy compleja).
 */
public interface IGestorCadenasMontaje {
    void encolarVehiculo(Vehiculo vehiculo);
    void iniciarSimulacionSimple(SimulacionListener listener) throws ColaVehiculosVacia;
    void iniciarSimulacionCompleja(SimulacionListener listener) throws ColaVehiculosVacia;
    void iniciarSimulacionMuyCompleja(SimulacionListener listener) throws ColaVehiculosVacia;
    List<CadenaMontaje> inicializarCadenasMontaje();
    List<CadenaMontaje> getCadenasMontaje();
}
