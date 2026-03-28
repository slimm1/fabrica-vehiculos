package data.gestion;

import data.almacen.IAlmacen;
import exception.ColaVehiculosVacia;
import models.montaje.CadenaMontaje;
import models.montaje.CadenaMontajeBiplazaDeportivo;
import models.montaje.CadenaMontajeFurgoneta;
import models.montaje.CadenaMontajeTurismo;
import models.simulacion.*;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.operario.Robot;
import models.trabajador.operario.TrabajadorOperario;
import models.vehiculo.BiplazaDeportivo;
import models.vehiculo.Furgoneta;
import models.vehiculo.Turismo;
import models.vehiculo.Vehiculo;

import java.util.*;

public class GestorCadenasMontaje implements IGestorCadenasMontaje {
    private IAlmacen almacen;
    private IGestorTrabajadores gestorTrabajadores;
    private List<CadenaMontaje> cadenasMontaje;
    private Queue<Vehiculo> colaVehiculos;
    private TrabajadorAdministrador administrador;
    private boolean sistemaActivo;

    public GestorCadenasMontaje(IAlmacen almacen, IGestorTrabajadores gestorTrabajadores) {
        this.almacen = almacen;
        this.gestorTrabajadores = gestorTrabajadores;
        this.cadenasMontaje = inicializarCadenasMontaje();
        this.colaVehiculos = new LinkedList<>();
        this.administrador = gestorTrabajadores.obtenerAdministrador();
        this.sistemaActivo = true;
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

    public IGestorTrabajadores getGestorTrabajadores() {
        return gestorTrabajadores;
    }

    public boolean isSistemaActivo() {
        return sistemaActivo;
    }

    public List<CadenaMontaje> getCadenasMontaje() {
        return cadenasMontaje;
    }

    @Override
    public void encolarVehiculo(Vehiculo vehiculo) {
        this.colaVehiculos.add(vehiculo);
    }

    @Override
    public void iniciarSimulacionSimple(SimulacionListener listener) throws ColaVehiculosVacia {
        if (this.colaVehiculos.isEmpty()) throw new ColaVehiculosVacia();
        SimulacionSimple simulacion = new SimulacionSimple(this);
        while (!this.colaVehiculos.isEmpty()) {
            Vehiculo v = this.colaVehiculos.poll();
            CadenaMontaje cadenaMontaje = this.obtenerCadenaDeMontaje(v);
            cadenaMontaje.setVehiculo(v);
            this.asignarOperarios(cadenaMontaje);
            simulacion.iniciar(cadenaMontaje, listener);
        }
    }

    @Override
    public void iniciarSimulacionCompleja(SimulacionListener listener) throws ColaVehiculosVacia {
        if (this.colaVehiculos.isEmpty()) throw new ColaVehiculosVacia();

        SimulacionCompleja simulacion = new SimulacionCompleja(this);
        Random rnd = new Random();
        cadenasMontaje.forEach(CadenaMontaje::resetearEstadoSimulacion);

        Map<CadenaMontaje, Integer> pasosPorCadena = new HashMap<>();
        for (Vehiculo v : this.colaVehiculos) {
            CadenaMontaje cadena = obtenerCadenaDeMontaje(v);
            pasosPorCadena.merge(cadena, 4, Integer::sum);
        }

        pasosPorCadena.forEach((cadena, totalPasos) -> {
            int numErrores = 2 + rnd.nextInt(2);
            Set<Integer> pasosConError = new HashSet<>();
            while (pasosConError.size() < numErrores) {
                pasosConError.add(rnd.nextInt(totalPasos));
            }
            cadena.setErroresALanzar(numErrores);
            cadena.setPasosConError(pasosConError);
        });

        while (!this.colaVehiculos.isEmpty()) {
            Vehiculo v = this.colaVehiculos.poll();
            CadenaMontaje cadenaMontaje = obtenerCadenaDeMontaje(v);
            cadenaMontaje.setVehiculo(v);
            this.asignarOperarios(cadenaMontaje);
            simulacion.iniciar(cadenaMontaje, listener);
        }
    }

    @Override
    public void iniciarSimulacionMuyCompleja(SimulacionListener listener) throws ColaVehiculosVacia {
        if (this.colaVehiculos.isEmpty()) {
            throw new ColaVehiculosVacia();
        }
        SimulacionMuyCompleja simulacion = new SimulacionMuyCompleja(this);
        for(Vehiculo v : this.colaVehiculos) {
            CadenaMontaje cadenaMontaje = this.obtenerCadenaDeMontaje(v);
            this.asignarOperarios(cadenaMontaje);
            simulacion.iniciar(cadenaMontaje, listener);
        }
    }

    @Override
    public List<CadenaMontaje> inicializarCadenasMontaje() {
        CadenaMontajeTurismo montajeTurismo = new CadenaMontajeTurismo();
        CadenaMontajeBiplazaDeportivo montajeBiplazaDeportivo = new CadenaMontajeBiplazaDeportivo();
        CadenaMontajeFurgoneta montajeFurgoneta = new CadenaMontajeFurgoneta();
        return new ArrayList<>(List.of(montajeTurismo, montajeBiplazaDeportivo, montajeFurgoneta));
    }

    // asigna un operario random a cada robot de la cadena de montaje
    public void asignarOperarios(CadenaMontaje cadenaMontaje) {
        List<TrabajadorOperario> operarios = new ArrayList<>(gestorTrabajadores.obtenerOperarios());
        Collections.shuffle(operarios);
        List<Robot> robots = cadenaMontaje.getRobots();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).setOperario(operarios.get(i));
        }
    }

    public CadenaMontaje obtenerCadenaDeMontaje(Vehiculo vehiculo) {
        return this.cadenasMontaje.stream()
                .filter(c -> (vehiculo instanceof Turismo && c instanceof CadenaMontajeTurismo)
                        || (vehiculo instanceof BiplazaDeportivo && c instanceof CadenaMontajeBiplazaDeportivo)
                        || (vehiculo instanceof Furgoneta && c instanceof CadenaMontajeFurgoneta))
                .findFirst()
                .orElse(null);
    }
}
