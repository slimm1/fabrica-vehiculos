package models.trabajador;

import data.dashboard.IDashboard;
import models.fabrica.OrdenProduccion;
import data.almacen.IAlmacen;
import models.fabrica.montaje.CadenaMontaje;
import models.fabrica.montaje.ConfiguracionCadenaMontaje;
import models.trabajador.operario.Robot;

import java.time.LocalDateTime;
import java.util.List;

public class TrabajadorGestorPlanta extends Trabajador {
    private List<Robot> robots;
    private IAlmacen almacen;
    private IDashboard dashboard;

    public TrabajadorGestorPlanta(String nombre, String apellidos, String dni, String direccion,
                        String numSeguridadSocial, double salario, LocalDateTime fechaIngreso) {
        super(nombre, apellidos, dni, direccion, numSeguridadSocial, salario, fechaIngreso);
    }

    public boolean validarOrden(OrdenProduccion orden) {
        int cantidad = orden.getCantidad();

        boolean hayMotores = almacen.hayStockMotor(orden.getMotorTipo(), cantidad);
        boolean hayTapiceria = almacen.hayStockTapiceria(orden.getTapiceriaTipo(), cantidad);
        boolean hayRuedas = almacen.hayStockRueda(orden.getRuedaTipo(), cantidad * 4);

        if (!hayMotores) {
            System.out.println("Stock insuficiente de motores");
            return false;
        }
        if (!hayTapiceria) {
            System.out.println("Stock insuficiente de tapicería");
            return false;
        }
        if (!hayRuedas) {
            System.out.println("Stock insuficiente de ruedas");
            return false;
        }

        return true;
    }

    public void configurarCadena(CadenaMontaje cadena, OrdenProduccion orden) {
        ConfiguracionCadenaMontaje config = new ConfiguracionCadenaMontaje(
                orden.getMotorTipo(),
                orden.getTapiceriaTipo(),
                orden.getRuedaTipo()
        );
        cadena.setConfiguracion(config);
    }

    public void monitorizar() {
        dashboard.verificarErrores();
    }

    public void llamarMecanico() {
        // Llamar a mecánico de cinta para reparación
    }

    @Override
    public void trabajar() {
        monitorizar();
    }
}
