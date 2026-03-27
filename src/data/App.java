package data;

import data.dashboard.Dashboard;
import data.dashboard.DashboardVisualizador;
import data.dashboard.IDashboard;
import data.dashboard.IDashboardVisualizador;
import models.fabrica.OrdenProduccion;
import data.almacen.Almacen;
import data.almacen.IAlmacen;
import data.gestion.GestorTrabajadores;
import data.gestion.IGestorTrabajadores;
import data.gestion.SistemaGestionFabrica;
import models.fabrica.montaje.CadenaMontaje;
import models.fabrica.montaje.CadenaMontajeTurismo;
import models.fabrica.montaje.ConfiguracionCadenaMontaje;
import models.planificador.Planificador;
import models.planificador.simulacion.SimulacionSimple;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.mecanico.TrabajadorMecanicoCintaEficiente;
import models.trabajador.operario.Robot;
import models.trabajador.operario.TrabajadorOperarioEficiente;
import models.vehiculo.*;
import models.vehiculo.componente.Componente;
import models.vehiculo.componente.motor.MotorElectrico;
import models.vehiculo.componente.motor.MotorGasolina;
import models.vehiculo.componente.motor.MotorHibrido;
import models.vehiculo.componente.rueda.RuedaDeportivo;
import models.vehiculo.componente.rueda.RuedaNormal;
import models.vehiculo.componente.rueda.RuedaTodoterreno;
import models.vehiculo.componente.tapiceria.TapiceriaAlcantara;
import models.vehiculo.componente.tapiceria.TapiceriaCuero;
import models.vehiculo.componente.tapiceria.TapiceriaTela;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    IGestorTrabajadores gestorTrabajadores;
    IAlmacen almacen;
    IDashboard dashboard;

    public App() {
        // 1. Crear almacén
        this.almacen = new Almacen();

        // 2. Crear dashboard
        IDashboardVisualizador visualizador = new DashboardVisualizador();
        this.dashboard = new Dashboard(visualizador, this.almacen);

        // 3. Crear gestor de trabajadores
        this.gestorTrabajadores = new GestorTrabajadores();

        // 4. Crear sistema de gestión
        SistemaGestionFabrica sistema = new SistemaGestionFabrica(this.almacen, dashboard, this.gestorTrabajadores);

        // 5. Crear trabajadores
        crearTrabajadores(this.gestorTrabajadores, this.almacen, this.dashboard);

        // 6. Llenar almacén
        llenarAlmacen(this.almacen);

        // 7. Crear cadenas de montaje
        crearCadenasMontaje(sistema, this.gestorTrabajadores);

        // 8. Crear planificador
        Planificador planificador = new SimulacionSimple(sistema);

        // 9. Crear órdenes de producción
//        crearOrdenes(planificador);
    }

    private static void crearTrabajadores(IGestorTrabajadores gestor, IAlmacen almacen, IDashboard dashboard) {
        // Operarios
        gestor.agregarTrabajador(
                new TrabajadorOperarioEficiente("Juan", "García", "12345678A", "Calle 1",
                        "SS123456", 1500, LocalDateTime.now())
        );

        // Gestor de planta
        gestor.agregarTrabajador(
                new TrabajadorGestorPlanta("María", "López", "87654321B", "Calle 2",
                        "SS654321", 2000, LocalDateTime.now())
        );

        // Administrador
        gestor.agregarTrabajador(
                new TrabajadorAdministrador("Carlos", "Martínez", "11111111C", "Calle 3",
                        "SS111111", 2200, LocalDateTime.now())
        );

        // Mecánicos
        gestor.agregarTrabajador(
                new TrabajadorMecanicoCintaEficiente("Pedro", "Fernández", "22222222D", "Calle 4",
                        "SS222222", 1800, LocalDateTime.now())
        );
    }

    private static void llenarAlmacen(IAlmacen almacen) {
        Random rnd = new Random();

        int cantMotores = rnd.nextInt(10) + 5;
        for (int i = 0; i < cantMotores; i++) {
            int tipo = rnd.nextInt(3);
            int cv = 100 + rnd.nextInt(200);

            switch (tipo) {
                case 0 -> almacen.agregarMotor(new MotorElectrico(cv, 150, 4));
                case 1 -> almacen.agregarMotor(new MotorGasolina(cv + 50, 180, 6));
                case 2 -> almacen.agregarMotor(new MotorHibrido(cv, 160, 4));
            }
        }

        String[] colores = {"Negro", "Beige", "Gris", "Marrón", "Rojo"};
        int cantTapicerias = rnd.nextInt(10) + 5;
        for (int i = 0; i < cantTapicerias; i++) {
            int tipo = rnd.nextInt(3);
            String color = colores[rnd.nextInt(colores.length)];
            int precio = 20 + rnd.nextInt(20);

            switch (tipo) {
                case 0 -> almacen.agregarTapiceria(new TapiceriaTela(color, precio));
                case 1 -> almacen.agregarTapiceria(new TapiceriaCuero(color, precio + 15));
                case 2 -> almacen.agregarTapiceria(new TapiceriaAlcantara(color, precio + 10));
            }
        }

        int cantRuedas = rnd.nextInt(20) + 10;
        for (int i = 0; i < cantRuedas; i++) {
            int tipo = rnd.nextInt(3);
            int ancho = 195 + (rnd.nextInt(5) * 10);
            int llanta = 15 + rnd.nextInt(5);

            switch (tipo) {
                case 0 -> almacen.agregarRueda(new RuedaNormal(ancho, llanta, 800, 190));
                case 1 -> almacen.agregarRueda(new RuedaDeportivo(ancho + 20, llanta + 1, 900, 240));
                case 2 -> almacen.agregarRueda(new RuedaTodoterreno(ancho, llanta - 1, 1200, 170));
            }
        }

        int cantVehiculosTerminados = rnd.nextInt(5) + 3;

        for (int i = 0; i < cantVehiculosTerminados; i++) {
            ArrayList<Componente> comps = new ArrayList<>();

            int cv = 80 + rnd.nextInt(300);
            switch (rnd.nextInt(3)) {
                case 0 -> comps.add(new MotorElectrico(cv, 150, 4));
                case 1 -> comps.add(new MotorGasolina(cv, 200, 6));
                case 2 -> comps.add(new MotorHibrido(cv, 180, 4));
            }

            String colTapiz = colores[rnd.nextInt(colores.length)];
            switch (rnd.nextInt(3)) {
                case 0 -> comps.add(new TapiceriaTela(colTapiz, 20));
                case 1 -> comps.add(new TapiceriaCuero(colTapiz, 50));
                case 2 -> comps.add(new TapiceriaAlcantara(colTapiz, 35));
            }

            switch (rnd.nextInt(3)) {
                case 0 -> comps.add(new RuedaNormal(205, 16, 800, 190));
                case 1 -> comps.add(new RuedaDeportivo(245, 19, 900, 280));
                case 2 -> comps.add(new RuedaTodoterreno(225, 17, 1200, 170));
            }

            Vehiculo v;
            String colorCoche = colores[rnd.nextInt(colores.length)];
            int tipoV = rnd.nextInt(3);

            v = switch (tipoV) {
                case 0 -> new Turismo(colorCoche, 5, 1300, 1900, comps);
                case 1 -> new Furgoneta(colorCoche, 3, 2100, 3500, comps);
                case 2 -> new BiplazaDeportivo(colorCoche, 2, 1100, 1400, comps);
                default -> null;
            };

            if (v != null) {
                while (!v.estaCompleto()) {
                    v.cambiarEstado();
                }
                almacen.agregarVehiculo(v);
            }
        }

        System.out.println("Almacén inicializado con stock aleatorio.");
    }

    private static void crearCadenasMontaje(SistemaGestionFabrica sistema,
                                            IGestorTrabajadores gestor) {
        List<Robot> robots = new ArrayList<>();
        robots.add(new Robot("R1", gestor.obtenerOperarios().get(0), EstadoVehiculo.CHASIS));
        robots.add(new Robot("R2", gestor.obtenerOperarios().get(0), EstadoVehiculo.MOTOR));
        robots.add(new Robot("R3", gestor.obtenerOperarios().get(0), EstadoVehiculo.RUEDAS));
        robots.add(new Robot("R4", gestor.obtenerOperarios().get(0), EstadoVehiculo.TAPICERIA));

        ConfiguracionCadenaMontaje config = new ConfiguracionCadenaMontaje(
                MotorGasolina.class,
                TapiceriaTela.class,
                RuedaNormal.class
        );

        CadenaMontaje cadena1 = new CadenaMontajeTurismo(robots, config);
        sistema.agregarCadenaMontaje(cadena1);
    }

//    private static void crearOrdenes(Planificador planificador) {
//        OrdenProduccion orden = new OrdenProduccion(
//                "ORD001",
//                Turismo.class,
//                5,
//                MotorGasolina.class,
//                TapiceriaTela.class,
//                RuedaNormal.class
//        );
//
//        for (int i = 0; i < orden.getCantidad(); i++) {
//            planificador.agregarVehiculo(new Turismo("Rojo", 1200, 1500, 1000));
//        }
//    }

    public IGestorTrabajadores getGestorTrabajadores() {
        return gestorTrabajadores;
    }

    public IAlmacen getAlmacen() {
        return almacen;
    }
}
