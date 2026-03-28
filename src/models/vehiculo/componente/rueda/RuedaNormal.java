package models.vehiculo.componente.rueda;

public class RuedaNormal extends Rueda {
    public RuedaNormal(int stock, int anchoMm, int diametroLlantaPulgadas, int indiceCargaKg, int codigoVelocidadKmh) {
        super("Rueda Normal", stock, anchoMm, diametroLlantaPulgadas, indiceCargaKg, codigoVelocidadKmh);
    }
}
