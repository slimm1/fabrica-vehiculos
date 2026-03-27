package app.almacen.vehiculos;

import models.vehiculo.Vehiculo;

import java.util.List;

public interface IListaVehiculos {
    void cargarDatos(List<Vehiculo> vehiculos);

    void mostrarVentana();
}
