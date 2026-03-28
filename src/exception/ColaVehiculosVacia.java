package exception;

public class ColaVehiculosVacia extends Exception {

    public ColaVehiculosVacia() {
        super("No se puede iniciar la simulación sin Vehículos. Por favor, añade un vehículo y prueba otra vez.");
    }

    public ColaVehiculosVacia(String message) {
        super(message);
    }
}
