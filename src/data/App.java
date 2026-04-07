package data;

import app.Dashboard;
import app.IDashboard;
import data.almacen.Almacen;
import data.almacen.IAlmacen;
import data.gestion.GestorTrabajadores;
import data.gestion.IGestorCadenasMontaje;
import data.gestion.IGestorTrabajadores;
import data.gestion.GestorCadenasMontaje;
import models.trabajador.TrabajadorAdministrador;
import models.trabajador.TrabajadorGestorPlanta;
import models.trabajador.TrabajadorMecanicoCinta;
import models.trabajador.TrabajadorOperario;
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
import java.util.Random;

/**
 * Punto de entrada y composición raíz de la aplicación. Inicializa el
 * almacén con stock aleatorio, crea los trabajadores de todos los roles
 * y construye el gestor de cadenas de montaje integrando ambas dependencias.
 */
public class App {

    IGestorTrabajadores gestorTrabajadores;
    IAlmacen almacen;
    IGestorCadenasMontaje gestorCadenasMontaje;

    public App() {
        // 1. Crear almacén
        this.almacen = new Almacen();
        llenarAlmacen(this.almacen);

        this.gestorTrabajadores = new GestorTrabajadores();
        crearTrabajadores(this.gestorTrabajadores);

        this.gestorCadenasMontaje = new GestorCadenasMontaje(this.almacen, this.gestorTrabajadores);
    }

    private static void crearTrabajadores(IGestorTrabajadores gestor) {
        gestor.agregarTrabajador(
                new TrabajadorOperario("Juan", "García", "12345678A", "Calle 1",
                        "SS123456", 1500, LocalDateTime.now(), (int)(Math.random() * 20) + 1)
        );
        gestor.agregarTrabajador(
                new TrabajadorOperario("Ana", "Pérez", "33333333E", "Calle 5",
                        "SS333333", 1500, LocalDateTime.now().minusMonths(6), (int)(Math.random() * 20) + 1)
        );
        gestor.agregarTrabajador(
                new TrabajadorOperario("Luis", "Sánchez", "44444444F", "Calle 6",
                        "SS444444", 1500, LocalDateTime.now().minusYears(1), (int)(Math.random() * 20) + 1)
        );

        gestor.agregarTrabajador(
                new TrabajadorOperario("Elena", "Ruiz", "55555555G", "Calle 7",
                        "SS555555", 1700, LocalDateTime.now().minusYears(2), (int)(Math.random() * 20) + 1)
        );
        gestor.agregarTrabajador(
                new TrabajadorOperario("Miguel", "Torres", "66666666H", "Calle 8",
                        "SS666666", 1700, LocalDateTime.now().minusYears(3), (int)(Math.random() * 20) + 1)
        );

        gestor.agregarTrabajador(
                new TrabajadorGestorPlanta("María", "López", "87654321B", "Calle 2",
                        "SS654321", 2000, LocalDateTime.now())
        );

        gestor.agregarTrabajador(
                new TrabajadorAdministrador("Carlos", "Martínez", "11111111C", "Calle 3",
                        "SS111111", 2200, LocalDateTime.now())
        );

        gestor.agregarTrabajador(
                new TrabajadorMecanicoCinta("Pedro", "Fernández", "22222222D", "Calle 4",
                        "SS222222", 1800, LocalDateTime.now())
        );
        gestor.agregarTrabajador(
                new TrabajadorMecanicoCinta("Rosa", "Jiménez", "77777777I", "Calle 9",
                        "SS777777", 1800, LocalDateTime.now().minusMonths(3))
        );

        gestor.agregarTrabajador(
                new TrabajadorMecanicoCinta("Jorge", "Moreno", "88888888J", "Calle 10",
                        "SS888888", 1950, LocalDateTime.now().minusYears(4))
        );
        gestor.agregarTrabajador(
                new TrabajadorMecanicoCinta("Laura", "Navarro", "99999999K", "Calle 11",
                        "SS999999", 1950, LocalDateTime.now().minusYears(5))
        );
    }

    private static void llenarAlmacen(IAlmacen almacen) {
        Random rnd = new Random();

        int cantMotores = rnd.nextInt(10) + 5;
        for (int i = 0; i < cantMotores; i++) {
            int tipo = rnd.nextInt(3);
            int cv = 100 + rnd.nextInt(200);
            int stock = 1 + rnd.nextInt(10);

            switch (tipo) {
                case 0 -> almacen.agregarMotor(new MotorElectrico(stock, cv, 150, 4));
                case 1 -> almacen.agregarMotor(new MotorGasolina(stock, cv + 50, 180, 6));
                case 2 -> almacen.agregarMotor(new MotorHibrido(stock, cv, 160, 4));
            }
        }

        String[] colores = {"Negro", "Beige", "Gris", "Marrón", "Rojo"};
        int cantTapicerias = rnd.nextInt(10) + 5;
        for (int i = 0; i < cantTapicerias; i++) {
            int tipo = rnd.nextInt(3);
            String color = colores[rnd.nextInt(colores.length)];
            int precio = 20 + rnd.nextInt(20);
            int stock = 1 + rnd.nextInt(15);

            switch (tipo) {
                case 0 -> almacen.agregarTapiceria(new TapiceriaTela(stock, color, precio));
                case 1 -> almacen.agregarTapiceria(new TapiceriaCuero(stock, color, precio + 15));
                case 2 -> almacen.agregarTapiceria(new TapiceriaAlcantara(stock, color, precio + 10));
            }
        }

        int cantRuedas = rnd.nextInt(20) + 10;
        for (int i = 0; i < cantRuedas; i++) {
            int tipo = rnd.nextInt(3);
            int ancho = 195 + (rnd.nextInt(5) * 10);
            int llanta = 15 + rnd.nextInt(5);
            int stock = 1 + rnd.nextInt(25);

            switch (tipo) {
                case 0 -> almacen.agregarRueda(new RuedaNormal(stock, ancho, llanta, 800, 190));
                case 1 -> almacen.agregarRueda(new RuedaDeportivo(stock, ancho + 20, llanta + 1, 900, 240));
                case 2 -> almacen.agregarRueda(new RuedaTodoterreno(stock, ancho, llanta - 1, 1200, 170));
            }
        }

        int cantVehiculosTerminados = rnd.nextInt(5) + 3;

        for (int i = 0; i < cantVehiculosTerminados; i++) {
            ArrayList<Componente> comps = new ArrayList<>();

            int cv = 80 + rnd.nextInt(300);
            switch (rnd.nextInt(3)) {
                case 0 -> comps.add(new MotorElectrico(1, cv, 150, 4));
                case 1 -> comps.add(new MotorGasolina(1, cv, 200, 6));
                case 2 -> comps.add(new MotorHibrido(1, cv, 180, 4));
            }

            String colTapiz = colores[rnd.nextInt(colores.length)];
            switch (rnd.nextInt(3)) {
                case 0 -> comps.add(new TapiceriaTela(1, colTapiz, 20));
                case 1 -> comps.add(new TapiceriaCuero(1, colTapiz, 50));
                case 2 -> comps.add(new TapiceriaAlcantara(1, colTapiz, 35));
            }

            switch (rnd.nextInt(3)) {
                case 0 -> comps.add(new RuedaNormal(4, 205, 16, 800, 190));
                case 1 -> comps.add(new RuedaDeportivo(4, 245, 19, 900, 280));
                case 2 -> comps.add(new RuedaTodoterreno(4, 225, 17, 1200, 170));
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

    public IGestorTrabajadores getGestorTrabajadores() {
        return gestorTrabajadores;
    }

    public IAlmacen getAlmacen() {
        return almacen;
    }

    public IGestorCadenasMontaje getGestorCadenasMontaje() {
        return gestorCadenasMontaje;
    }

    public void startApp() {
        IDashboard dashboard = new Dashboard(this);
        dashboard.init();
    }
}
