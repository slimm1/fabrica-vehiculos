package data.gestion;

import data.almacen.IAlmacen;
import exception.ColaVehiculosVacia;
import models.montaje.CadenaMontaje;
import models.montaje.CadenaMontajeBiplazaDeportivo;
import models.montaje.CadenaMontajeFurgoneta;
import models.montaje.CadenaMontajeTurismo;
import models.simulacion.*;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.Robot;
import models.trabajador.TrabajadorOperario;
import models.vehiculo.BiplazaDeportivo;
import models.vehiculo.Furgoneta;
import models.vehiculo.Turismo;
import models.vehiculo.Vehiculo;

import java.util.*;

/**
 * Gestiona las tres cadenas de montaje (Turismo, BiplazaDeportivo, Furgoneta)
 * y la cola de vehículos pendientes. Ofrece tres modos de simulación:
 * simple (sin errores), compleja (2-3 errores de mecánico por cadena) y
 * muy compleja (errores de mecánico + caída de luz). Permite pausar y
 * restaurar el sistema ante fallos.
 */
public class GestorCadenasMontaje implements IGestorCadenasMontaje {
    private IAlmacen almacen;
    private IGestorTrabajadores gestorTrabajadores;
    private List<CadenaMontaje> cadenasMontaje;
    private Queue<Vehiculo> colaVehiculos;

    // Inicializar el gestor de cadenas
    public GestorCadenasMontaje(IAlmacen almacen, IGestorTrabajadores gestorTrabajadores) {
        this.almacen = almacen;
        this.gestorTrabajadores = gestorTrabajadores;
        // inicia la lista de cadenas de montaje
        this.cadenasMontaje = inicializarCadenasMontaje();
        // la cola inicialmente está vacía
        this.colaVehiculos = new LinkedList<>();
    }

    public IAlmacen getAlmacen() {
        return almacen;
    }

    public IGestorTrabajadores getGestorTrabajadores() {
        return gestorTrabajadores;
    }

    public List<CadenaMontaje> getCadenasMontaje() {
        return cadenasMontaje;
    }

    // Implementación de la interfaz. Añade el vehículo dado a la cola.
    @Override
    public void encolarVehiculo(Vehiculo vehiculo) {
        this.colaVehiculos.add(vehiculo);
    }

    @Override
    public void iniciarSimulacionSimple(SimulacionListener listener) throws ColaVehiculosVacia {
        // Si por lo que sea la cola se intenta iniciar sin vehículos, lanzamos excepción.
        if (this.colaVehiculos.isEmpty()) throw new ColaVehiculosVacia();
        // Instanciamos una nueva simulación simple pasándole esta instancia del Gestor de cadenas de montaje
        SimulacionSimple simulacion = new SimulacionSimple(this);
        // Mientras haya vehículos en cola, iteramos.
        while (!this.colaVehiculos.isEmpty()) {
            // Recuperar el primer vehículo en cola
            Vehiculo v = this.colaVehiculos.poll();
            // Recuperamos la cadena de montaje que corresponde a este vehículo y se lo asignamos
            CadenaMontaje cadenaMontaje = this.obtenerCadenaDeMontaje(v);
            cadenaMontaje.setVehiculo(v);
            // asignar operarios a la cadena de montaje
            this.asignarOperarios(cadenaMontaje);
            simulacion.iniciar(cadenaMontaje, listener);
        }
    }

    @Override
    public void iniciarSimulacionCompleja(SimulacionListener listener) throws ColaVehiculosVacia {
        if (this.colaVehiculos.isEmpty()) throw new ColaVehiculosVacia();

        // Instanciar una nueva Simulación compleja
        SimulacionCompleja simulacion = new SimulacionCompleja(this);
        Random rnd = new Random();
        // Resetear cualquier estado anterior en todas las cadenas de montaje
        cadenasMontaje.forEach(CadenaMontaje::resetearEstadoSimulacion);
        // Definir un mapa que asocia la instancia de cada cadena con su numero de pasos.
        Map<CadenaMontaje, Integer> pasosPorCadena = new HashMap<>();
        for (Vehiculo v : this.colaVehiculos) {
            // por cada vehículo en cola, iniciamos la cadena que corresponda y la añadimos al mapa.
            // Si el mapa ya contenía esa cola, simplemente sumamos los pasos adicionales para el vehículo
            // de esta iteración
            CadenaMontaje cadena = obtenerCadenaDeMontaje(v);
            pasosPorCadena.merge(cadena, 4, Integer::sum);
        }
        // Iteramos por el mapa. Por cada cadena de montaje hay un número de pasos definidos
        pasosPorCadena.forEach((cadena, totalPasos) -> {
            // Definir el número de pasos de errores en la cadena. Como mínimo 2 por ser simulación compleja
            int numErrores = 2 + rnd.nextInt(2);
            Set<Integer> pasosConError = new HashSet<>();
            // Mientras haya hueco en el set de pasos con error, añadimos un random entre 0 y totalPasos
            while (pasosConError.size() < numErrores) {
                // El uso de set garantiza que no haya posiciones repetidas en pasosConError.
                // Seleccionamos un paso aleatorio dentro del total de pasos para lanzar error.
                pasosConError.add(rnd.nextInt(totalPasos));
            }
            // Seteamos las variables de errores en la instancia de cadena de montaje de esta iteración
            cadena.setPasosConError(pasosConError);
        });

        // Mientras haya vehículos en cola iterar
        while (!this.colaVehiculos.isEmpty()) {
            // recuperar el primer vehículo, asignarlo a la cadena, asignar operarios e iniciar.
            Vehiculo v = this.colaVehiculos.poll();
            CadenaMontaje cadenaMontaje = obtenerCadenaDeMontaje(v);
            cadenaMontaje.setVehiculo(v);
            this.asignarOperarios(cadenaMontaje);
            simulacion.iniciar(cadenaMontaje, listener);
        }
    }

    @Override
    public void iniciarSimulacionMuyCompleja(SimulacionListener listener) throws ColaVehiculosVacia {
        if (this.colaVehiculos.isEmpty()) throw new ColaVehiculosVacia();

        SimulacionMuyCompleja simulacion = new SimulacionMuyCompleja(this);
        Random rnd = new Random();

        // Limpiamos cualquier estado que haya quedado de simulaciones anteriores
        cadenasMontaje.forEach(CadenaMontaje::resetearEstadoSimulacion);

        // Mismo proceso que en la simulación compleja para los pasos por cadena de montaje.
        Map<CadenaMontaje, Integer> pasosPorCadena = new HashMap<>();
        for (Vehiculo v : this.colaVehiculos) {
            CadenaMontaje cadena = obtenerCadenaDeMontaje(v);
            pasosPorCadena.merge(cadena, 3, Integer::sum);
        }

        pasosPorCadena.forEach((cadena, totalPasos) -> {
            // Elegimos cuántos errores va a haber (2 o 3, al azar)
            int numErrores = 2 + rnd.nextInt(2);

            // Seleccionamos en qué pasos ocurrirán esos errores.
            Set<Integer> pasosConError = new HashSet<>();
            while (pasosConError.size() < numErrores) {
                pasosConError.add(rnd.nextInt(totalPasos));
            }

            // El error de la caída de luz tiene que caer en un paso diferente a los otros errores,
            // Usamos do while para seleccionar un paso aleatorio en el cual no haya ya errores.
            int pasoCaida;
            do { pasoCaida = rnd.nextInt(totalPasos); } while (pasosConError.contains(pasoCaida));
            cadena.setPasoCaida(pasoCaida);

            cadena.setPasosConError(pasosConError);
        });

        // Misma dinámica que en la simulación compleja.
        while (!this.colaVehiculos.isEmpty()) {
            Vehiculo v = this.colaVehiculos.poll();
            CadenaMontaje cadenaMontaje = obtenerCadenaDeMontaje(v);
            cadenaMontaje.setVehiculo(v);
            this.asignarOperarios(cadenaMontaje); // cada cadena necesita su equipo antes de arrancar
            simulacion.iniciar(cadenaMontaje, listener);
        }
    }


    @Override
    public List<CadenaMontaje> inicializarCadenasMontaje() {
        // Inicia las cadenas de montaje y las encapsula en una lista.
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

    // Devuelve la cadena de montaje correspondiente a cada vehículo.
    public CadenaMontaje obtenerCadenaDeMontaje(Vehiculo vehiculo) {
        return this.cadenasMontaje.stream()
                .filter(c -> (vehiculo instanceof Turismo && c instanceof CadenaMontajeTurismo)
                        || (vehiculo instanceof BiplazaDeportivo && c instanceof CadenaMontajeBiplazaDeportivo)
                        || (vehiculo instanceof Furgoneta && c instanceof CadenaMontajeFurgoneta))
                .findFirst()
                .orElse(null);
    }
}
