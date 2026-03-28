package models.simulacion;

import data.gestion.GestorCadenasMontaje;

public class SimulacionMuyCompleja extends SimulacionCompleja {
    private int caidas;

    public SimulacionMuyCompleja(GestorCadenasMontaje sistema) {
        super(sistema);
        this.caidas = 0;
    }

    @Override
    protected void manejarProblemas() {
        if (Math.random() < 0.1 && caidas < 1) {
            generarCaidaSistema();
            caidas++;
        }

        super.manejarProblemas();
    }

    private void generarCaidaSistema() {
        System.out.println("¡APAGÓN! Sistema en pausa...");
        gestorCadenasMontaje.manejarError();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        gestorCadenasMontaje.restaurarSistema();
        System.out.println("Sistema restaurado");
    }
}