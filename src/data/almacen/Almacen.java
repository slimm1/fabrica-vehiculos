package data.almacen;

import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.*;

/**
 * Implementación de IAlmacen. Gestiona cuatro mapas (id → entidad) para
 * motores, tapicerías, ruedas y vehículos. La extracción de componentes
 * descuenta stock y elimina la entrada al llegar a 0; las ruedas se
 * consumen en grupos de 4.
 */
public class Almacen implements IAlmacen {

    private Map<String, Motor>     motores;
    private Map<String, Tapiceria> tapicerias;
    private Map<String, Rueda>     ruedas;
    private Map<String, Vehiculo>  vehiculos;

    public Almacen() {
        this.motores    = new HashMap<>();
        this.tapicerias = new HashMap<>();
        this.ruedas     = new HashMap<>();
        this.vehiculos  = new HashMap<>();
    }

    @Override
    public void agregarMotor(Motor motor) {
        motores.put(motor.getId(), motor);
    }

    @Override
    public void agregarTapiceria(Tapiceria tapiceria) {
        tapicerias.put(tapiceria.getId(), tapiceria);
    }

    @Override
    public void agregarRueda(Rueda rueda) {
        ruedas.put(rueda.getId(), rueda);
    }

    @Override
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.put(vehiculo.getId(), vehiculo);
    }

    public Motor extraerMotor(String id) {
        Motor m = motores.get(id);
        if (m == null || m.getStock() <= 0) return null;
        m.setStock(m.getStock() - 1);
        if (m.getStock() == 0) motores.remove(id);
        return m;
    }

    public Tapiceria extraerTapiceria(String id) {
        Tapiceria t = tapicerias.get(id);
        if (t == null || t.getStock() <= 0) return null;
        t.setStock(t.getStock() - 1);
        if (t.getStock() == 0) tapicerias.remove(id);
        return t;
    }

    public Rueda extraerRueda(String id) {
        Rueda r = ruedas.get(id);
        if (r == null || r.getStock() < 4) return null;
        r.setStock(r.getStock() - 4);
        if (r.getStock() == 0) ruedas.remove(id);
        return r;
    }

    @Override
    public Vehiculo obtenerVehiculo(String id) {
        return vehiculos.get(id);
    }

    @Override
    public ArrayList<Motor> obtenerMotores() {
        return new ArrayList<>(motores.values());
    }

    @Override
    public ArrayList<Tapiceria> obtenerTapicerias() {
        return new ArrayList<>(tapicerias.values());
    }

    @Override
    public ArrayList<Rueda> obtenerRuedas() {
        return new ArrayList<>(ruedas.values());
    }

    @Override
    public ArrayList<Vehiculo> obtenerVehiculos() {
        return new ArrayList<>(vehiculos.values());
    }

    @Override
    public ArrayList<Componente> obtenerComponentes() {
        ArrayList<Componente> todos = new ArrayList<>();
        todos.addAll(motores.values());
        todos.addAll(tapicerias.values());
        todos.addAll(ruedas.values());
        return todos;
    }

    public boolean hayStockMotor(String id, int cantidad) {
        Motor m = motores.get(id);
        return m != null && m.getStock() >= cantidad;
    }

    public boolean hayStockTapiceria(String id, int cantidad) {
        Tapiceria t = tapicerias.get(id);
        return t != null && t.getStock() >= cantidad;
    }

    public boolean hayStockRueda(String id, int cantidad) {
        Rueda r = ruedas.get(id);
        return r != null && r.getStock() >= cantidad * 4;
    }

    @Override
    public Map<String, Integer> obtenerResumenStockComponentes() {
        Map<String, Integer> resumen = new LinkedHashMap<>();
        motores.values().forEach(m    -> resumen.put(m.getNombre(), m.getStock()));
        ruedas.values().forEach(r     -> resumen.put(r.getNombre(), r.getStock()));
        tapicerias.values().forEach(t -> resumen.put(t.getNombre(), t.getStock()));
        return resumen;
    }
}