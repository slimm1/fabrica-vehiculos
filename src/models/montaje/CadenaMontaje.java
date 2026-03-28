package models.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public abstract class CadenaMontaje {
    protected List<Robot> robots;
    protected List<RegistroMontaje> registros;
    protected boolean funcionando;
    protected Vehiculo vehiculo;

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

    public void avanzar() {

    }

    public List<Robot> getRobots() {
        return robots;
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

    public void anadirRegistro() {
        registros.add(new RegistroMontaje(this.vehiculo.getId(), this.vehiculo.getTipo(), this.vehiculo.getEstadoActual(), this.vehiculo.getComponentePorEstado()));
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

    @Override
    public String toString() {
        return this.getTipo() + "\n" +
            "Estado: " + (this.funcionando ? "Funcionando.\n" : "Detenida.\n") +
            "Vehiculo: " + this.vehiculo.toString() + "\n" +
            this.getRobotPorEstado(this.vehiculo.getEstadoActual()).toString();

    }
}