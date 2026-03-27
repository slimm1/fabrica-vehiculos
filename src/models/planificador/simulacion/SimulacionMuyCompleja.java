package models.planificador.simulacion;

import data.gestion.SistemaGestionFabrica;

public class SimulacionMuyCompleja extends SimulacionCompleja {
    private int caidas;

    public SimulacionMuyCompleja(SistemaGestionFabrica sistema) {
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
        sistema.manejarError();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        sistema.restaurarSistema();
        System.out.println("Sistema restaurado");
    }
}