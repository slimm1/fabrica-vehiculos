package data.gestion;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.TrabajadorOperario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void eliminarTrabajador(String dni) {
        trabajadores.remove(dni);
    }

    @Override
    public Trabajador obtenerTrabajador(String dni) {
        return trabajadores.get(dni);
    }

    @Override
    public List<Trabajador> obtenerTodosTrabajadores() {
        return new ArrayList<>(trabajadores.values());
    }

    @Override
    public List<TrabajadorOperario> obtenerOperarios() {
        return trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorOperario)
                .map(t -> (TrabajadorOperario) t)
                .toList();
    }

    @Override
    public List<TrabajadorMecanicoCinta> obtenerMecanicos() {
        return trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorMecanicoCinta)
                .map(t -> (TrabajadorMecanicoCinta) t)
                .toList();
    }

    @Override
    public TrabajadorGestorPlanta obtenerGestorPlanta() {
        return (TrabajadorGestorPlanta) trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorGestorPlanta)
                .findFirst()
                .orElse(null);
    }

    @Override
    public TrabajadorAdministrador obtenerAdministrador() {
        return (TrabajadorAdministrador) trabajadores.values().stream()
                .filter(t -> t instanceof TrabajadorAdministrador)
                .findFirst()
                .orElse(null);
    }
}