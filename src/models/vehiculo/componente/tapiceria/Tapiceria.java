package models.vehiculo.componente.tapiceria;

import models.vehiculo.componente.Componente;

public abstract class Tapiceria extends Componente {
    protected String color;
    protected int metrosCuadrados;
    public Tapiceria(String nombre, String color, int metrosCuadrados) {
        super(nombre);
        this.color = color;
        this.metrosCuadrados = metrosCuadrados;
    }

    @Override
    public String toString() {
        return "Color: " + color + ", Dimensiones: " + metrosCuadrados + ".";
    }
}
