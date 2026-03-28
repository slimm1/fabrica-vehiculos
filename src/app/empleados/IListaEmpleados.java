package app.empleados;

import models.trabajador.Trabajador;

import java.util.List;

public interface IListaEmpleados {
    void cargarDatos(List<Trabajador> empleados);

    void ordenar();

    void mostrarVentana();
}
