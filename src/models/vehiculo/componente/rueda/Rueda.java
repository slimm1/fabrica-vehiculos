package models.vehiculo.componente.rueda;

import models.vehiculo.componente.Componente;

/**
 * Clase base de las ruedas. Extiende Componente con ancho (mm), diámetro
 * de llanta (pulgadas), índice de carga (kg) y velocidad máxima (km/h).
 * Las subclases concretan el tipo (normal, deportivo, todoterreno).
 */
public abstract class Rueda extends Componente {
    protected int anchoMm;
    protected double diametroPulgadas;
    protected int indiceCargaKg;
    protected int codigoVelocidadKmh;

    public Rueda(String nombre, int stock, int anchoMm, int diametroLlantaPulgadas,
                 int indiceCargaKg, int codigoVelocidadKmh) {
        super(nombre, stock);
        this.anchoMm = anchoMm;
        this.diametroPulgadas = diametroLlantaPulgadas;
        this.indiceCargaKg = indiceCargaKg;
        this.codigoVelocidadKmh = codigoVelocidadKmh;
    }

    @Override
    public String toString() {
        return "Cantidad: " + this.getStock() +  ", " + anchoMm + "/" + diametroPulgadas + " [ Índice de carga (kg): " + indiceCargaKg + ", Cod. Velocidad: " + codigoVelocidadKmh +" ]";
    }

    public int getAnchoMm() {
        return anchoMm;
    }

    public double getDiametroPulgadas() {
        return diametroPulgadas;
    }

    public int getIndiceCargaKg() {
        return indiceCargaKg;
    }

    public int getCodigoVelocidadKmh() {
        return codigoVelocidadKmh;
    }
}
