package app.empleados;

import models.trabajador.Trabajador;

import java.util.List;

// Interfaz creada para abstraer la implementación del listado de empleados.
public interface IListaEmpleados {
    // Inicializa el listado con los datos
    void cargarDatos(List<Trabajador> empleados);

    // Ordena la lista
    void ordenar();

    // Muestra la lista
    void mostrarVentana();
}
