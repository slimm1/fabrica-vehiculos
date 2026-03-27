package models.planificador;

import data.gestion.SistemaGestionFabrica;
import models.fabrica.montaje.CadenaMontaje;
import models.vehiculo.Vehiculo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Planificador {
    protected SistemaGestionFabrica sistema;
    protected int tiempoActual;
    protected boolean enEjecucion;
    protected Queue<Vehiculo> vehiculosPendientes;

    public Planificador(SistemaGestionFabrica sistema) {
        this.sistema = sistema;
        this.tiempoActual = 0;
        this.enEjecucion = false;
        this.vehiculosPendientes = new LinkedList<>();
    }

    public void iniciar() {
        enEjecucion = true;
        while (enEjecucion) {
            tick();
            sistema.getDashboard().mostrar();
            tiempoActual++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void detener() {
        enEjecucion = false;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculosPendientes.add(vehiculo);
    }

    protected void avanzarCadenas() {
        List<CadenaMontaje> cadenas = sistema.getCadenasMontaje();

        for (CadenaMontaje cadena : cadenas) {
            if (!vehiculosPendientes.isEmpty() && cadena.esteFuncionando()) {
                Vehiculo vehiculo = vehiculosPendientes.poll();
                cadena.construirVehiculo(vehiculo);
            }
        }
    }

    protected abstract void manejarProblemas();

    protected void tick() {
        if (!sistema.isSistemaActivo()) {
            return;
        }

        manejarProblemas();
        avanzarCadenas();
        System.out.println("Segundo " + tiempoActual);
    }

    public int getTiempoActual() {
        return tiempoActual;
    }
}