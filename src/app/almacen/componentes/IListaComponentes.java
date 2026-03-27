package app.almacen.componentes;

import models.vehiculo.componente.motor.Motor;
import models.vehiculo.componente.rueda.Rueda;
import models.vehiculo.componente.tapiceria.Tapiceria;

import java.util.List;

public interface IListaComponentes {
    void cargarMotores(List<Motor> motores);
    void cargarRuedas(List<Rueda> ruedas);
    void cargarTapicerias(List<Tapiceria> tapicerias);

    void mostrarVentana();
}
