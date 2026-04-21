package data.almacen;

import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.ArrayList;
import java.util.Map;

/**
 * Contrato para la gestión del almacén: alta y extracción de componentes
 * (Motor, Rueda, Tapicería) y vehículos, consulta de stock y listados.
 */
public interface IAlmacen {
    void agregarMotor(Motor motor);
    void agregarTapiceria(Tapiceria tapiceria);
    void agregarRueda(Rueda rueda);
    void agregarVehiculo(Vehiculo vehiculo);

    Motor extraerMotor(String nombre);
    Tapiceria extraerTapiceria(String nombre);
    Rueda extraerRueda(String nombre);
    Vehiculo obtenerVehiculo(String id);

    Map<String, Integer> obtenerResumenStockComponentes();

    ArrayList<Vehiculo> obtenerVehiculos();
    ArrayList<Motor> obtenerMotores();
    ArrayList<Tapiceria> obtenerTapicerias();
    ArrayList<Rueda> obtenerRuedas();
}