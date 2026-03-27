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

    Motor extraerMotor(Class<? extends Motor> tipo);
    Tapiceria extraerTapiceria(Class<? extends Tapiceria> tipo);
    Rueda extraerRueda(Class<? extends Rueda> tipo);
    Vehiculo obtenerVehiculo(String id);

    boolean hayStockMotor(Class<? extends Motor> tipo, int cantidad);
    boolean hayStockTapiceria(Class<? extends Tapiceria> tipo, int cantidad);
    boolean hayStockRueda(Class<? extends Rueda> tipo, int cantidad);

    Map<String, Integer> obtenerResumenStockComponentes();

    ArrayList<Vehiculo> obtenerVehiculos();
    ArrayList<Motor> obtenerMotores();
    ArrayList<Tapiceria> obtenerTapicerias();
    ArrayList<Rueda> obtenerRuedas();
    ArrayList<Componente> obtenerComponentes();
}
