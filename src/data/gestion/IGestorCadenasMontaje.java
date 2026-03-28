package data.gestion;

import exception.ColaVehiculosVacia;
import models.montaje.CadenaMontaje;
import models.vehiculo.Vehiculo;

import java.util.List;

public interface IGestorCadenasMontaje {
    void encolarVehiculo(Vehiculo vehiculo);
    void iniciarSimulacionSimple() throws ColaVehiculosVacia;
    void iniciarSimulacionCompleja() throws ColaVehiculosVacia;
    void iniciarSimulacionMuyCompleja() throws ColaVehiculosVacia;
    List<CadenaMontaje> inicializarCadenasMontaje();
}
