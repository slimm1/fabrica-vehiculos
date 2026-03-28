package models.vehiculo.componente.motor;

import models.vehiculo.componente.Componente;

public abstract class Motor extends Componente {
    // Cilindrada en centímetros cúbicos (cm³ o cc)
    // Se usa int porque comercialmente se redondea (ej: 1998 cc)
    protected int cilindrada;

    // Potencia máxima (podría ser en CV o kW)
    // Se usa double porque en fichas técnicas de ingeniería aparecen decimales (ej: 110.5 CV)
    protected double potencia;

    // Número de cilindros (ej: 3, 4, 6, 8, 12)
    // Es un valor discreto y exacto, por lo que siempre es int
    protected int numeroCilindros;

    public Motor(String nombre, int stock, int cilindrada, int potencia, int numCilindros) {
        super(nombre, stock);
        this.cilindrada = cilindrada;
        this.potencia = potencia;
        this.numeroCilindros = numCilindros;
    }

    @Override
    public String toString() {
        return "Cilindrada: " + cilindrada + ", Potencia: " + potencia + ", Num. cilindros: " + numeroCilindros + ".";
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
