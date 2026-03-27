package data.dashboard;

import data.almacen.IAlmacen;
import models.vehiculo.EstadoVehiculo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard implements IDashboard {
    private List<RegistroMontaje> historial;
    private List<RegistroError> historialErrores;
    private Map<String, EstadoVehiculo> vehiculosEnProduccion;
    private IDashboardVisualizador visualizador;
    private IAlmacen almacen;

    public Dashboard(IDashboardVisualizador visualizador, IAlmacen almacen) {
        this.historial = new ArrayList<>();
        this.historialErrores = new ArrayList<>();
        this.vehiculosEnProduccion = new HashMap<>();
        this.visualizador = visualizador;
        this.almacen = almacen;
    }

    @Override
    public void registrarCambio(RegistroMontaje registro) {
        historial.add(registro);
        vehiculosEnProduccion.put(registro.getIdVehiculo(), registro.getEstado());
        mostrar();
    }

    @Override
    public void registrarError(String error) {
        RegistroError registro = new RegistroError(error);
        historialErrores.add(registro);
        System.out.println("Error registrado: " + error);
    }

    @Override
    public List<RegistroError> obtenerErroresPorFecha(LocalDateTime fecha) {
        return historialErrores.stream()
                .filter(r -> r.getTimestamp().toLocalDate().equals(fecha.toLocalDate()))
                .toList();
    }

    @Override
    public void verificarErrores() {
        List<RegistroError> erroresHoy = obtenerErroresPorFecha(LocalDateTime.now());
        if (!erroresHoy.isEmpty()) {
            System.out.println("=== ERRORES DETECTADOS HOY ===");
            for (RegistroError error : erroresHoy) {
                System.out.println("ERROR: " + error.getDescripcion());
            }
        }
    }

    @Override
    public List<RegistroMontaje> obtenerHistorialPorFecha(LocalDateTime fecha) {
        return historial.stream()
                .filter(r -> r.getTimestamp().toLocalDate().equals(fecha.toLocalDate()))
                .toList();
    }

    @Override
    public List<RegistroMontaje> obtenerHistorialPorComponente(String componente) {
        return historial.stream()
                .filter(r -> r.getComponente().equals(componente))
                .toList();
    }

    @Override
    public Map<String, EstadoVehiculo> obtenerEstadoVehiculos() {
        return new HashMap<>(vehiculosEnProduccion);
    }

    public Map<String, Integer> obtenerBalanceComponentes() {
        return almacen.obtenerResumenStockComponentes();
    }

    @Override
    public void mostrar() {
        visualizador.mostrarEstadoDashboard(this);
    }
}