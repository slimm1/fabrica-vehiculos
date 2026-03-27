package data.almacen;

import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.*;

public class Almacen implements IAlmacen {
    // Ahora guardamos LISTAS de objetos reales para no perder sus IDs ni datos
    private Map<Class<? extends Motor>, List<Motor>> motores;
    private Map<Class<? extends Tapiceria>, List<Tapiceria>> tapicerias;
    private Map<Class<? extends Rueda>, List<Rueda>> ruedas;
    private Map<String, Vehiculo> vehiculos;

    public Almacen() {
        this.motores = new HashMap<>();
        this.tapicerias = new HashMap<>();
        this.ruedas = new HashMap<>();
        this.vehiculos = new HashMap<>();
    }

    // --- MÉTODOS DE AGREGAR ---
    @Override
    public void agregarMotor(Motor motor) {
        motores.computeIfAbsent(motor.getClass(), k -> new ArrayList<>()).add(motor);
    }

    @Override
    public void agregarTapiceria(Tapiceria tapiceria) {
        tapicerias.computeIfAbsent(tapiceria.getClass(), k -> new ArrayList<>()).add(tapiceria);
    }

    @Override
    public void agregarRueda(Rueda rueda) {
        ruedas.computeIfAbsent(rueda.getClass(), k -> new ArrayList<>()).add(rueda);
    }

    @Override
    public void agregarVehiculo(Vehiculo vehiculo) {
        // Usamos el ID del vehículo como clave para encontrarlo rápido
        vehiculos.put(vehiculo.getId(), vehiculo);
    }

    // --- MÉTODOS DE EXTRAER (Saca el objeto del almacén) ---
    @Override
    public Motor extraerMotor(Class<? extends Motor> tipo) {
        List<Motor> lista = motores.get(tipo);
        if (lista != null && !lista.isEmpty()) {
            return lista.remove(lista.size() - 1); // Extraemos el último (más eficiente)
        }
        return null;
    }

    @Override
    public Tapiceria extraerTapiceria(Class<? extends Tapiceria> tipo) {
        List<Tapiceria> lista = tapicerias.get(tipo);
        if (lista != null && !lista.isEmpty()) {
            return lista.remove(lista.size() - 1);
        }
        return null;
    }

    @Override
    public Rueda extraerRueda(Class<? extends Rueda> tipo) {
        List<Rueda> lista = ruedas.get(tipo);
        if (lista != null && !lista.isEmpty()) {
            return lista.remove(lista.size() - 1);
        }
        return null;
    }

    @Override
    public Vehiculo obtenerVehiculo(String id) {
        return vehiculos.get(id);
    }

    // --- MÉTODOS DE STOCK ---
    @Override
    public boolean hayStockMotor(Class<? extends Motor> tipo, int cantidad) {
        return motores.containsKey(tipo) && motores.get(tipo).size() >= cantidad;
    }

    @Override
    public boolean hayStockTapiceria(Class<? extends Tapiceria> tipo, int cantidad) {
        return tapicerias.containsKey(tipo) && tapicerias.get(tipo).size() >= cantidad;
    }

    @Override
    public boolean hayStockRueda(Class<? extends Rueda> tipo, int cantidad) {
        return ruedas.containsKey(tipo) && ruedas.get(tipo).size() >= cantidad;
    }

    // --- MÉTODOS DE LISTADO (Para las Tablas de la UI) ---
    @Override
    public ArrayList<Motor> obtenerMotores() {
        ArrayList<Motor> todos = new ArrayList<>();
        motores.values().forEach(todos::addAll);
        return todos;
    }

    @Override
    public ArrayList<Tapiceria> obtenerTapicerias() {
        ArrayList<Tapiceria> todas = new ArrayList<>();
        tapicerias.values().forEach(todas::addAll);
        return todas;
    }

    @Override
    public ArrayList<Rueda> obtenerRuedas() {
        ArrayList<Rueda> todas = new ArrayList<>();
        ruedas.values().forEach(todas::addAll);
        return todas;
    }

    @Override
    public ArrayList<Vehiculo> obtenerVehiculos() {
        return new ArrayList<>(vehiculos.values());
    }

    @Override
    public ArrayList<Componente> obtenerComponentes() {
        ArrayList<Componente> todos = new ArrayList<>();

        todos.addAll(obtenerMotores());
        todos.addAll(obtenerTapicerias());
        todos.addAll(obtenerRuedas());

        return todos;
    }

    @Override
    public Map<String, Integer> obtenerResumenStockComponentes() {
        Map<String, Integer> resumen = new LinkedHashMap<>();

        motores.forEach((clase, lista) -> resumen.put(
                lista.isEmpty() ? clase.getSimpleName() : lista.get(0).getNombre(), lista.size()));
        ruedas.forEach((clase, lista) -> resumen.put(
                lista.isEmpty() ? clase.getSimpleName() : lista.get(0).getNombre(), lista.size()));
        tapicerias.forEach((clase, lista) -> resumen.put(
                lista.isEmpty() ? clase.getSimpleName() : lista.get(0).getNombre(), lista.size()));

        return resumen;
    }
}