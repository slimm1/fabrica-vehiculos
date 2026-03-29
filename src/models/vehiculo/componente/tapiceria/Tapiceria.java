package models.vehiculo.componente.tapiceria;

import models.vehiculo.componente.Componente;

/**
 * Clase base de las tapicerías. Extiende Componente añadiendo color y
 * metros cuadrados. Las subclases concretan el material (tela, cuero,
 * alcántara).
 */
public abstract class Tapiceria extends Componente {
    protected String color;
    protected int metrosCuadrados;
    public Tapiceria(String nombre, int stock, String color, int metrosCuadrados) {
        super(nombre, stock);
        this.color = color;
        this.metrosCuadrados = metrosCuadrados;
    }

    @Override
    public String toString() {
        return "Cantidad: " + this.getStock() +  ", Color: " + color + ", Dimensiones: " + metrosCuadrados + ".";
    }

    public String getColor() {
        return color;
    }

    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }
}
