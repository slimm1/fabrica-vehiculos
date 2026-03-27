package models.fabrica.montaje;

import models.trabajador.operario.Robot;
import models.vehiculo.Vehiculo;

import java.util.List;

public abstract class CadenaMontaje {
    protected List<Robot> robots;
    protected boolean funcionando;
    protected ConfiguracionCadenaMontaje configuracion;

    public CadenaMontaje(List<Robot> robots, ConfiguracionCadenaMontaje configuracion) {
        this.robots = robots;
        this.funcionando = true;
        this.configuracion = configuracion;
    }

    public void detener() {
        funcionando = false;
    }

    public void reanudar() {
        funcionando = true;
    }

    public boolean esteFuncionando() {
        return funcionando;
    }

    public ConfiguracionCadenaMontaje getConfiguracion() {
        return configuracion;
    }

    public abstract void construirVehiculo(Vehiculo vehiculo);

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

    public void setConfiguracion(ConfiguracionCadenaMontaje configuracion) {
        this.configuracion = configuracion;
    }
}