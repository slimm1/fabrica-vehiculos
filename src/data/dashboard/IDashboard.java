package data.dashboard;

import models.vehiculo.EstadoVehiculo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IDashboard {
    void registrarCambio(RegistroMontaje registro);
    void registrarError(String error);
    List<RegistroMontaje> obtenerHistorialPorFecha(LocalDateTime fecha);
    List<RegistroMontaje> obtenerHistorialPorComponente(String componente);
    List<RegistroError> obtenerErroresPorFecha(LocalDateTime fecha);
    Map<String, EstadoVehiculo> obtenerEstadoVehiculos();
    Map<String, Integer> obtenerBalanceComponentes();
    void verificarErrores();
    void mostrar();
}