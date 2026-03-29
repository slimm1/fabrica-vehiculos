package models.vehiculo.componente;

import java.util.UUID;

public abstract class Componente {
    protected String nombre;
    protected String id;
    protected int stock;

    public Componente(String nombre, int stock) {
        this.nombre = nombre;
        this.id = UUID.randomUUID().toString();
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    @Override
    public abstract String toString();

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
