package data.dashboard;

import models.vehiculo.EstadoVehiculo;

import java.util.Map;

/**
 * Esta clase será la que implemente las diferentes funciones visuales del Dashboard...
 * Interactuará con el almacén y el gestor de trabajadores, con el planificador.
 */
public class DashboardVisualizador implements IDashboardVisualizador {
    @Override
    public void mostrarEstadoDashboard(Dashboard dashboard) {
        System.out.println("\n=== ESTADO ACTUAL DEL DASHBOARD ===");

        System.out.println("\n--- Vehículos en Producción ---");
        Map<String, EstadoVehiculo> vehiculos = dashboard.obtenerEstadoVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos en producción");
        } else {
            vehiculos.forEach((id, estado) ->
                    System.out.println("  Vehículo " + id + ": " + estado.getNombre())
            );
        }

        System.out.println("\n--- Balance de Componentes ---");
        Map<String, Integer> balance = dashboard.obtenerBalanceComponentes();
        if (balance.isEmpty()) {
            System.out.println("Almacén vacío");
        } else {
            balance.forEach((componente, cantidad) ->
                    System.out.println("  " + componente + ": " + cantidad + " unidades")
            );
        }

        System.out.println();
    }

    public void mostrarInfoTrabajadores() {

    }
}