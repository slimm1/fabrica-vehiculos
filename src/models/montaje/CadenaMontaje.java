package models.montaje;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.Robot;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase base de las cadenas de montaje. Gestiona los cuatro robots
 * (chasis, ruedas, tapicería, motor), el historial de registros y el
 * estado de simulación (pasos con error y caída de luz). Las subclases
 * especializan el tipo de vehículo que procesan.
 */
public abstract class CadenaMontaje {
    protected List<Robot> robots;
    protected List<Registro> registros;
    protected boolean funcionando;
    protected Vehiculo vehiculo;
    private Set<Integer> pasosConError = new HashSet<>();
    private int pasoActual = 0;
    private int pasoCaida = -1;

    public CadenaMontaje() {
        this.funcionando = true;
        this.robots = inicializarRobots();
        // Lista de registros de la cadena de montaje.
        this.registros = new ArrayList<>();
    }

    public void detener() {
        this.funcionando = false;
    }

    public void reanudar() {
        this.funcionando = true;
    }

    public List<Robot> getRobots() {
        return this.robots;
    }

    public List<Registro> getRegistros() {
        return this.registros;
    }

    public Vehiculo getVehiculo() {
        return this.vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    // Función para añadir un registro de ejecución de paso de cinta.
    // Si recibe un trabajador Mecánico o Admin, significa que ha ocurrido un error.
    // Si recibe un Operario, significa que se ha realizado un proceso de montaje.
    public void anadirRegistro(Trabajador trabajador) {
        if(trabajador instanceof TrabajadorMecanicoCinta || trabajador instanceof TrabajadorAdministrador) {
            registros.add(new RegistroError(this.vehiculo, this.vehiculo.getComponentePorEstado(), trabajador, this.vehiculo.getEstadoActual()));
            return;
        }
        registros.add(new RegistroMontaje(this.vehiculo, this.vehiculo.getComponentePorEstado(), trabajador, this.vehiculo.getEstadoActual()));
    }

    // Inicia la lista de robots de la cadena de montaje. Una por cada estado.
    public List<Robot> inicializarRobots() {
        Robot robotChasis    = new Robot(EstadoVehiculo.CHASIS,    this);
        Robot robotRuedas    = new Robot(EstadoVehiculo.RUEDAS,    this);
        Robot robotTapiceria = new Robot(EstadoVehiculo.TAPICERIA, this);
        Robot robotMotor     = new Robot(EstadoVehiculo.MOTOR,     this);
        return new ArrayList<>(List.of(robotChasis, robotRuedas, robotTapiceria, robotMotor));
    }

    // Devuelve el tipo de cadena de esta instancia a modo de string.
    public String getTipo() {
        if(this instanceof CadenaMontajeTurismo) {
            return "Cadena de montaje Turismos";
        } else if (this instanceof CadenaMontajeFurgoneta) {
            return "Cadena de montaje de Furgonetas";
        } else if (this instanceof CadenaMontajeBiplazaDeportivo) {
            return "Cadena de montaje de Biplaza deportivo";
        }
        return "Desconocido";
    }

    // Devuelve el robot de la lista correspondiente al estado pasado por los argumentos
    public Robot getRobotPorEstado(EstadoVehiculo estadoVehiculo) {
        return this.robots.stream()
                .filter(r -> r.getEstadoVehiculo().equals(estadoVehiculo))
                .findFirst()
                .orElse(null);
    }

    public void setPasosConError(Set<Integer> pasos) { this.pasosConError = pasos; }

    public void incrementarPasoActual() { this.pasoActual++; }
    public boolean hayErrorEnPasoActual() { return this.pasosConError.contains(this.pasoActual); }

    public void setPasoCaida(int pasoCaida) { this.pasoCaida = pasoCaida; }
    public boolean hayCaidaEnPasoActual() { return this.pasoActual == this.pasoCaida; }

    public void resetearEstadoSimulacion() {
        this.pasoActual = 0;
        this.pasosConError = new HashSet<>();
    }

    // Pinta el estado actual de la cadena de montaje.
    @Override
    public String toString() {
        Robot robot = this.getRobotPorEstado(this.vehiculo.getEstadoActual());
        String robotInfo = robot != null
                ? "🔧 Robot: " + robot.getEstadoVehiculo().getNombre()
                + " — Operario: " + robot.getOperario().getNombre()
                + " " + robot.getOperario().getApellidos()
                + " (" + robot.getOperario().getPuesto() + ")"
                + " — " + robot.getOperario().getTiempoTarea() + "s"
                : "";

        return "-----------------------------------------------------------\n"
                + "🏭 " + this.getTipo() + " — " + (this.funcionando ? "✅ Funcionando" : "🛑 Detenida") + "\n"
                + "🚗 " + this.vehiculo.toString() + "\n"
                + robotInfo;
    }
}