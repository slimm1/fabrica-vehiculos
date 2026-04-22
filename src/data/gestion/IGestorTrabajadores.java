package data.gestion;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.TrabajadorOperario;

import java.util.List;

/**
 * Contrato para la gestión de trabajadores: alta, baja, consulta por DNI
 * y obtención filtrada por rol (operario, mecánico, gestor de planta,
 * administrador).
 */
public interface IGestorTrabajadores {
    void agregarTrabajador(Trabajador trabajador);
    List<Trabajador> obtenerTodosTrabajadores();
    List<TrabajadorOperario> obtenerOperarios();
    List<TrabajadorMecanicoCinta> obtenerMecanicos();
    TrabajadorGestorPlanta obtenerGestorPlanta();
    TrabajadorAdministrador obtenerAdministrador();
}
