package models.montaje;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.mecanico.TrabajadorMecanicoCinta;
import models.trabajador.operario.Robot;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CadenaMontaje {
    protected List<Robot> robots;
    protected List<Registro> registros;
    protected boolean funcionando;
    protected Vehiculo vehiculo;
    private Set<Integer> pasosConError = new HashSet<>();
    private int pasoActual = 0;
    private int erroresALanzar = 0;
    private int pasoCaida = -1;

    public CadenaMontaje() {
        this.funcionando = true;
        this.robots = inicializarRobots();
        this.registros = new ArrayList<>();
    }

    public void detener() {
        funcionando = false;
    }

    public void reanudar() {
        funcionando = true;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public boolean isFuncionando() {
        return funcionando;
    }

    public void setFuncionando(boolean funcionando) {
        this.funcionando = funcionando;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void anadirRegistro(Trabajador trabajador) {
        if(trabajador instanceof TrabajadorMecanicoCinta || trabajador instanceof TrabajadorAdministrador) {
            registros.add(new RegistroError(this.vehiculo, this.vehiculo.getComponentePorEstado(), trabajador, this.vehiculo.getEstadoActual()));
            return;
        }
        registros.add(new RegistroMontaje(this.vehiculo, this.vehiculo.getComponentePorEstado(), trabajador, this.vehiculo.getEstadoActual()));
    }

    public List<Robot> inicializarRobots() {
        Robot robotChasis    = new Robot(EstadoVehiculo.CHASIS,    this);
        Robot robotRuedas    = new Robot(EstadoVehiculo.RUEDAS,    this);
        Robot robotTapiceria = new Robot(EstadoVehiculo.TAPICERIA, this);
        Robot robotMotor     = new Robot(EstadoVehiculo.MOTOR,     this);
        return new ArrayList<>(List.of(robotChasis, robotRuedas, robotTapiceria, robotMotor));
    }

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

    public Robot getRobotPorEstado(EstadoVehiculo estadoVehiculo) {
        return robots.stream()
                .filter(r -> r.getEstadoVehiculo().equals(estadoVehiculo))
                .findFirst()
                .orElse(null);
    }

    public Set<Integer> getPasosConError() { return pasosConError; }
    public void setPasosConError(Set<Integer> pasos) { this.pasosConError = pasos; }
    public int getPasoActual() { return pasoActual; }
    public void incrementarPasoActual() { this.pasoActual++; }
    public boolean hayErrorEnPasoActual() { return pasosConError.contains(pasoActual); }
    public int getErroresALanzar() { return erroresALanzar; }
    public void setErroresALanzar(int errores) { this.erroresALanzar = errores; }

    public int getPasoCaida() { return pasoCaida; }
    public void setPasoCaida(int pasoCaida) { this.pasoCaida = pasoCaida; }
    public boolean hayCaidaEnPasoActual() { return pasoActual == pasoCaida; }

    public void resetearEstadoSimulacion() {
        this.pasoActual = 0;
        this.pasosConError = new HashSet<>();
        this.erroresALanzar = 0;
    }

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