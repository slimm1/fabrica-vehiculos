package models.vehiculo.componente.rueda;

import models.vehiculo.componente.Componente;

public abstract class Rueda extends Componente {
    // Ancho de la sección en milímetros (ej: 205)
    protected int anchoMm;

    // Diámetro de la llanta en pulgadas (ej: 17.5 o 18)
    // Se usa double porque existen llantas de media pulgada
    protected double diametroPulgadas;

    // Índice de carga: Peso máximo que soporta el neumático en kg
    protected int indiceCargaKg;

    // Código de velocidad: Velocidad máxima permitida en km/h
    // Aunque es una letra (ej: 'V'), pides representarlo por su valor en km/h
    protected int codigoVelocidadKmh;

    public Rueda(String nombre, int anchoMm, int diametroLlantaPulgadas,
                 int indiceCargaKg, int codigoVelocidadKmh) {
        super(nombre);
        this.anchoMm = anchoMm;
        this.diametroPulgadas = diametroLlantaPulgadas;
        this.indiceCargaKg = indiceCargaKg;
        this.codigoVelocidadKmh = codigoVelocidadKmh;
    }

    @Override
    public String toString() {
        return anchoMm + "/" + diametroPulgadas + " [ Índice de carga (kg): " + indiceCargaKg + ", Cod. Velocidad: " + codigoVelocidadKmh +" ]";
    }
}
