package app.almacen.vehiculos;

import models.vehiculo.Vehiculo;

import java.util.List;

// Interface para definir la estructura del Listado de Vehículos.
// Pretende actuar como una capa de abstracción
public interface IListaVehiculos {
    void cargarDatos(List<Vehiculo> vehiculos);

    void mostrarVentana();
}
