package models.montaje;

import models.trabajador.Trabajador;
import models.trabajador.TrabajadorAdministrador;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import java.time.format.DateTimeFormatter;

public class RegistroError extends Registro {

    public RegistroError(Vehiculo vehiculo, Componente componente, Trabajador trabajador, EstadoVehiculo estadoVehiculo) {
        super(vehiculo, componente, trabajador, estadoVehiculo);
    }

    // Pinta de manera bonita el detalle del registro de error a partir de esta instancia
    @Override
    public String detalles() {
        String tipoError = this.trabajador instanceof TrabajadorAdministrador
                ? "⚡ Apagón del sistema"
                : "🔧 Avería en la cinta";

        return "⚠ REGISTRO DE ERROR — " + tipoError + "\n"
                + "-----------------------------------------------------------\n"
                + "🕐 Timestamp:   " + this.timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n"
                + "🚗 Vehículo:    " + this.vehiculo.toString() + "\n"
                + "🔩 Estado:      " + this.estadoVehiculo.getNombre() + "\n"
                + "🔧 Componente:  " + (this.componente != null ? this.componente.getNombre() : "—") + "\n"
                + "👷 Responsable: " + this.trabajador.getNombre() + " " + this.trabajador.getApellidos() + "\n"
                + "📋 Puesto:      " + this.trabajador.getPuesto();
    }
}
