package data.gestion;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.TrabajadorOperario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de IGestorTrabajadores. Mantiene un mapa dni → Trabajador
 * y ofrece consultas filtradas por rol: operarios, mecánicos, gestor de
 * planta y administrador.
 */
public class GestorTrabajadores implements IGestorTrabajadores {
    private Map<String, Trabajador> trabajadores;

    public GestorTrabajadores() {
        this.trabajadores = new HashMap<>();
    }

    @Override
    public void agregarTrabajador(Trabajador trabajador) {
        trabajadores.put(trabajador.getDni(), trabajador);
    }

    @Override
    public List<Trabajador> obtenerTodosTrabajadores() {
        return new ArrayList<>(trabajadores.values());
    }

    @Override
    // Devuelve una lista filtrando aquellos que sean instancias de TrabajadorOperario
    public List<TrabajadorOperario> obtenerOperarios() {
        return trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorOperario)
                .map(t -> (TrabajadorOperario) t)
                .toList();
    }

    @Override
    // Lo mismo para los mecánicos
    public List<TrabajadorMecanicoCinta> obtenerMecanicos() {
        return trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorMecanicoCinta)
                .map(t -> (TrabajadorMecanicoCinta) t)
                .toList();
    }

    @Override
    // Misma operación para los gestores de planta
    public TrabajadorGestorPlanta obtenerGestorPlanta() {
        return (TrabajadorGestorPlanta) trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorGestorPlanta)
                .findFirst()
                .orElse(null);
    }

    @Override
    // Recuperar el admin.
    public TrabajadorAdministrador obtenerAdministrador() {
        return (TrabajadorAdministrador) trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorAdministrador)
                .findFirst()
                .orElse(null);
    }
}