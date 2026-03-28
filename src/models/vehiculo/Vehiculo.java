package models.vehiculo;

import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.TrabajadorOperario;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class Vehiculo {
    public String id;
    protected String color;
    protected int numPlazas;
    protected int tara;
    protected int pesoMaximoAutorizado;
    protected EstadoVehiculo estadoActual;
    protected int indiceEstado;
    protected ArrayList<Componente> componentes;

    private static final List<EstadoVehiculo> ESTADOS_ORDEN =
            Arrays.asList(EstadoVehiculo.CHASIS, EstadoVehiculo.MOTOR,
                EstadoVehiculo.TAPICERIA, EstadoVehiculo.RUEDAS, EstadoVehiculo.FINALIZADO);

    public Vehiculo(String color, int numPlazas, int tara, int pesoMaximoAutorizado, ArrayList<Componente> componentes) {
        this.id = UUID.randomUUID().toString();
        this.color = color;
        this.numPlazas = numPlazas;
        this.tara = tara;
        this.pesoMaximoAutorizado = pesoMaximoAutorizado;
        this.estadoActual = EstadoVehiculo.CHASIS;
        this.indiceEstado = 0;
        this.componentes = componentes;
    }

    public EstadoVehiculo getEstadoActual() {
        return estadoActual;
    }

    public void cambiarEstado() {
        if (indiceEstado < ESTADOS_ORDEN.size() - 1) {
            indiceEstado++;
            estadoActual = ESTADOS_ORDEN.get(indiceEstado);
        }
    }

    public boolean estaCompleto() {
        return indiceEstado == ESTADOS_ORDEN.size() - 1;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        if(this instanceof Furgoneta) {
            return "Furgoneta";
        } else if (this instanceof BiplazaDeportivo) {
            return "Biplaza Deportivo";
        } else if (this instanceof Turismo) {
            return "Turismo";
        }
        return "Desconocido";
    }

    public String getColor() {
        return color;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public int getTara() {
        return tara;
    }

    public int getPesoMaximoAutorizado() {
        return pesoMaximoAutorizado;
    }

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }

    public Componente getComponentePorEstado() {
        return switch (this.estadoActual) {
            case MOTOR     -> componentes.stream().filter(c -> c instanceof Motor).findFirst().orElse(null);
            case TAPICERIA -> componentes.stream().filter(c -> c instanceof Tapiceria).findFirst().orElse(null);
            case RUEDAS    -> componentes.stream().filter(c -> c instanceof Rueda).findFirst().orElse(null);
            default        -> null;
        };
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Color: %s | Plazas: %d | Tara: %d kg | PMA: %d kg | Estado: %s",
                id.substring(0, 8), getTipo(), color, numPlazas, tara, pesoMaximoAutorizado, estadoActual);
    }
}
