package models.vehiculo.componente.motor;

import models.vehiculo.componente.Componente;

/**
 * Clase base de los motores. Extiende Componente con cilindrada (cc),
 * potencia (CV) y número de cilindros. Las subclases concretan el tipo
 * (eléctrico, gasolina, híbrido).
 */
public abstract class Motor extends Componente {
    protected int cilindrada;
    protected double potencia;
    protected int numeroCilindros;

    public Motor(String nombre, int stock, int cilindrada, int potencia, int numCilindros) {
        super(nombre, stock);
        this.cilindrada = cilindrada;
        this.potencia = potencia;
        this.numeroCilindros = numCilindros;
    }

    @Override
    public String toString() {
        return "Cantidad: " + this.getStock() +  ", Cilindrada: " + cilindrada + ", Potencia: " + potencia + ", Num. cilindros: " + numeroCilindros + ".";
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public double getPotencia() {
        return potencia;
    }

    public int getNumeroCilindros() {
        return numeroCilindros;
    }
}
