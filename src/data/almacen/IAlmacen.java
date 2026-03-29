package data.almacen;

import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.ArrayList;
import java.util.Map;

public interface IAlmacen {
    void agregarMotor(Motor motor);
    void agregarTapiceria(Tapiceria tapiceria);
    void agregarRueda(Rueda rueda);
    void agregarVehiculo(Vehiculo vehiculo);

    Motor extraerMotor(String nombre);
    Tapiceria extraerTapiceria(String nombre);
    Rueda extraerRueda(String nombre);
    Vehiculo obtenerVehiculo(String id);

    boolean hayStockMotor(String nombre, int cantidad);
    boolean hayStockTapiceria(String nombre, int cantidad);
    boolean hayStockRueda(String nombre, int cantidad);

    Map<String, Integer> obtenerResumenStockComponentes();

    ArrayList<Vehiculo> obtenerVehiculos();
    ArrayList<Motor> obtenerMotores();
    ArrayList<Tapiceria> obtenerTapicerias();
    ArrayList<Rueda> obtenerRuedas();
    ArrayList<Componente> obtenerComponentes();
}