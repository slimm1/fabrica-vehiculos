package models.montaje;

import models.trabajador.Trabajador;
import models.vehiculo.EstadoVehiculo;
import models.vehiculo.Vehiculo;
import models.vehiculo.componente.Componente;

import java.time.LocalDateTime;

public class RegistroMontaje extends Registro {

    public RegistroMontaje(Vehiculo vehiculo, Componente componente, Trabajador trabajador, EstadoVehiculo estadoVehiculo) {
        super(vehiculo, componente, trabajador, estadoVehiculo);
    }

    @Override
    public String detalles() {
        return "🔩 REGISTRO DE MONTAJE\n"
                + "-----------------------------------------------------------\n"
                + "🕐 Timestamp:   " + timestamp.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n"
                + "🚗 Vehículo:    " + vehiculo.toString() + "\n"
                + "🔩 Estado:      " + estadoVehiculo.getNombre() + "\n"
                + "🔧 Componente:  " + (componente != null ? componente.getNombre() : "—") + "\n"
                + "👷 Operario:    " + trabajador.getNombre() + " " + trabajador.getApellidos() + "\n"
                + "📋 Puesto:      " + trabajador.getPuesto();
    }
}