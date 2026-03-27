package data.gestion;

import data.dashboard.IDashboard;
import data.almacen.IAlmacen;
import models.fabrica.montaje.CadenaMontaje;
import models.trabajador.TrabajadorAdministrador;
import models.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class SistemaGestionFabrica {
    private IAlmacen almacen;
    private IDashboard dashboard;
    private IGestorTrabajadores gestorTrabajadores;
    private List<CadenaMontaje> cadenasMontaje;
    private TrabajadorAdministrador administrador;
    private boolean sistemaActivo;

    public SistemaGestionFabrica(IAlmacen almacen, IDashboard dashboard,
                                 IGestorTrabajadores gestorTrabajadores) {
        this.almacen = almacen;
        this.dashboard = dashboard;
        this.gestorTrabajadores = gestorTrabajadores;
        this.cadenasMontaje = new ArrayList<>();
        this.administrador = gestorTrabajadores.obtenerAdministrador();
        this.sistemaActivo = true;
    }

    public void agregarCadenaMontaje(CadenaMontaje cadena) {
        cadenasMontaje.add(cadena);
    }

    public void producirVehiculo(Vehiculo vehiculo, CadenaMontaje cadena) {
        if (!sistemaActivo) {
            System.out.println("Sistema pausado. Esperando restauración...");
            return;
        }
        cadena.construirVehiculo(vehiculo);
    }

    public void manejarError() {
        sistemaActivo = false;
        cadenasMontaje.forEach(CadenaMontaje::detener);
        System.out.println("ERROR: Sistema pausado");
    }

    public void restaurarSistema() {
        administrador.restaurarSistema(gestorTrabajadores.obtenerGestorPlanta(), cadenasMontaje);
        sistemaActivo = true;
    }

    public IAlmacen getAlmacen() {
        return almacen;
    }

    public IDashboard getDashboard() {
        return dashboard;
    }

    public IGestorTrabajadores getGestorTrabajadores() {
        return gestorTrabajadores;
    }

    public boolean isSistemaActivo() {
        return sistemaActivo;
    }

    public List<CadenaMontaje> getCadenasMontaje() {
        return cadenasMontaje;
    }

    public void setCadenasMontaje(List<CadenaMontaje> cadenasMontaje) {
        this.cadenasMontaje = cadenasMontaje;
    }
}
