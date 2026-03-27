package data.gestion;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.TrabajadorOperario;

import java.util.List;

public interface IGestorTrabajadores {
    void agregarTrabajador(Trabajador trabajador);
    void eliminarTrabajador(String dni);
    Trabajador obtenerTrabajador(String dni);
    List<Trabajador> obtenerTodosTrabajadores();
    List<TrabajadorOperario> obtenerOperarios();
    List<TrabajadorMecanicoCinta> obtenerMecanicos();
    TrabajadorGestorPlanta obtenerGestorPlanta();
    TrabajadorAdministrador obtenerAdministrador();
}
