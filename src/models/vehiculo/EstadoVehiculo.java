package models.vehiculo;

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
