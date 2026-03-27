package models.vehiculo;

import models.vehiculo.componente.Componente;

import java.util.ArrayList;

public class Furgoneta extends Vehiculo {
    public Furgoneta(String color, int numPlazas, int tara, int pesoMaximoAutorizado, ArrayList<Componente> componentes) {
        super(color, numPlazas, tara, pesoMaximoAutorizado, componentes);
    }
}
