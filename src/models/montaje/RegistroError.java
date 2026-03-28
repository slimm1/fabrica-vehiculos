package models.montaje;

import models.trabajador.Trabajador;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import java.time.LocalDateTime;

public class RegistroError extends Registro {

    public RegistroError(Vehiculo vehiculo, Componente componente, Trabajador trabajador, EstadoVehiculo estadoVehiculo) {
        super(vehiculo, componente, trabajador, estadoVehiculo);
    }

    @Override
    public String detalles() {
        String tipoError = trabajador instanceof models.trabajador.TrabajadorAdministrador
                ? "⚡ Apagón del sistema"
                : "🔧 Avería en la cinta";

        return "⚠ REGISTRO DE ERROR — " + tipoError + "\n"
                + "-----------------------------------------------------------\n"
                + "🕐 Timestamp:   " + timestamp.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n"
                + "🚗 Vehículo:    " + vehiculo.toString() + "\n"
                + "🔩 Estado:      " + estadoVehiculo.getNombre() + "\n"
                + "🔧 Componente:  " + (componente != null ? componente.getNombre() : "—") + "\n"
                + "👷 Responsable: " + trabajador.getNombre() + " " + trabajador.getApellidos() + "\n"
                + "📋 Puesto:      " + trabajador.getPuesto();
    }
}
