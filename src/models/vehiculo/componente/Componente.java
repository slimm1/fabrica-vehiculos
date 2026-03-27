package models.vehiculo.componente;

import java.util.UUID;

public abstract class Componente {
    protected String nombre;
    protected String id;

    public Componente(String nombre) {
        this.nombre = nombre;
        this.id = UUID.randomUUID().toString();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public abstract String toString();
}
