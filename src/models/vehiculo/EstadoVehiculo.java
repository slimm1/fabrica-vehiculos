package models.vehiculo;

/**
 * Enum que representa las fases del proceso de montaje de un vehículo:
 * CHASIS → MOTOR → TAPICERIA → RUEDAS → FINALIZADO.
 */
public enum EstadoVehiculo {
    CHASIS("Chasis"),
    MOTOR("Motor"),
    TAPICERIA("Tapicería"),
    RUEDAS("Ruedas"),
    FINALIZADO("Finalizado");

    private final String nombre;

    EstadoVehiculo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
