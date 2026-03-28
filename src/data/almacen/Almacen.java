package data.almacen;

import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.*;

public class Almacen implements IAlmacen {

    private Map<Class<? extends Motor>,    Motor>    motores;
    private Map<Class<? extends Tapiceria>, Tapiceria> tapicerias;
    private Map<Class<? extends Rueda>,    Rueda>    ruedas;
    private Map<String, Vehiculo>                    vehiculos;

    public Almacen() {
        this.motores    = new HashMap<>();
        this.tapicerias = new HashMap<>();
        this.ruedas     = new HashMap<>();
        this.vehiculos  = new HashMap<>();
    }

    // ── Agregar ───────────────────────────────────────────────────────────────

    @Override
    public void agregarMotor(Motor motor) {
        motores.merge(motor.getClass(), motor, (existing, nuevo) -> {
            existing.setStock(existing.getStock() + nuevo.getStock());
            return existing;
        });
    }

    @Override
    public void agregarTapiceria(Tapiceria tapiceria) {
        tapicerias.merge(tapiceria.getClass(), tapiceria, (existing, nuevo) -> {
            existing.setStock(existing.getStock() + nuevo.getStock());
            return existing;
        });
    }

    @Override
    public void agregarRueda(Rueda rueda) {
        ruedas.merge(rueda.getClass(), rueda, (existing, nuevo) -> {
            existing.setStock(existing.getStock() + nuevo.getStock());
            return existing;
        });
    }

    @Override
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.put(vehiculo.getId(), vehiculo);
    }

    // ── Extraer ───────────────────────────────────────────────────────────────

    @Override
    public Motor extraerMotor(Class<? extends Motor> tipo) {
        Motor m = motores.get(tipo);
        if (m == null || m.getStock() <= 0) return null;
        m.setStock(m.getStock() - 1);
        if (m.getStock() == 0) motores.remove(tipo);
        return m;
    }

    @Override
    public Tapiceria extraerTapiceria(Class<? extends Tapiceria> tipo) {
        Tapiceria t = tapicerias.get(tipo);
        if (t == null || t.getStock() <= 0) return null;
        t.setStock(t.getStock() - 1);
        if (t.getStock() == 0) tapicerias.remove(tipo);
        return t;
    }

    @Override
    public Rueda extraerRueda(Class<? extends Rueda> tipo) {
        Rueda r = ruedas.get(tipo);
        if (r == null || r.getStock() < 4) return null;
        r.setStock(r.getStock() - 4);
        if (r.getStock() == 0) ruedas.remove(tipo);
        return r;
    }

    // ── Obtener ───────────────────────────────────────────────────────────────

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

    // ── Stock ─────────────────────────────────────────────────────────────────

    @Override
    public boolean hayStockMotor(Class<? extends Motor> tipo, int cantidad) {
        Motor m = motores.get(tipo);
        return m != null && m.getStock() >= cantidad;
    }

    @Override
    public boolean hayStockTapiceria(Class<? extends Tapiceria> tipo, int cantidad) {
        Tapiceria t = tapicerias.get(tipo);
        return t != null && t.getStock() >= cantidad;
    }

    @Override
    public boolean hayStockRueda(Class<? extends Rueda> tipo, int cantidad) {
        Rueda r = ruedas.get(tipo);
        return r != null && r.getStock() >= cantidad * 4;
    }

    // ── Resumen ───────────────────────────────────────────────────────────────

    @Override
    public Map<String, Integer> obtenerResumenStockComponentes() {
        Map<String, Integer> resumen = new LinkedHashMap<>();
        motores.values().forEach(m    -> resumen.put(m.getNombre(), m.getStock()));
        ruedas.values().forEach(r     -> resumen.put(r.getNombre(), r.getStock()));
        tapicerias.values().forEach(t -> resumen.put(t.getNombre(), t.getStock()));
        return resumen;
    }
}