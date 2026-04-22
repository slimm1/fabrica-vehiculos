package models.montaje;

import models.trabajador.Trabajador;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import java.time.format.DateTimeFormatter;


public class RegistroMontaje extends Registro {

    public RegistroMontaje(Vehiculo vehiculo, Componente componente, Trabajador trabajador, EstadoVehiculo estadoVehiculo) {
        super(vehiculo, componente, trabajador, estadoVehiculo);
    }

    // Pinta de manera bonita el detalle del registro de montaje a partir de esta instancia
    @Override
    public String detalles() {
        return "🔩 REGISTRO DE MONTAJE\n"
                + "-----------------------------------------------------------\n"
                + "🕐 Timestamp:   " + this.timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n"
                + "🚗 Vehículo:    " + this.vehiculo.toString() + "\n"
                + "🔩 Estado:      " + this.estadoVehiculo.getNombre() + "\n"
                + "🔧 Componente:  " + (this.componente != null ? this.componente.getNombre() : "—") + "\n"
                + "👷 Operario:    " + this.trabajador.getNombre() + " " + this.trabajador.getApellidos() + "\n"
                + "📋 Puesto:      " + this.trabajador.getPuesto();
    }
}