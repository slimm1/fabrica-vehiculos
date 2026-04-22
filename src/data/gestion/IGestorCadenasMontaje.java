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
    // recibe un vehículo y lo encola
    void encolarVehiculo(Vehiculo vehiculo);
    // Métodos para iniciar las simulaciones. Debe recibir una instancia de SimulacionListener
    void iniciarSimulacionSimple(SimulacionListener listener) throws ColaVehiculosVacia;
    void iniciarSimulacionCompleja(SimulacionListener listener) throws ColaVehiculosVacia;
    void iniciarSimulacionMuyCompleja(SimulacionListener listener) throws ColaVehiculosVacia;
    // Inicializar y obtener las cadenas de montaje
    List<CadenaMontaje> inicializarCadenasMontaje();
    List<CadenaMontaje> getCadenasMontaje();
}
