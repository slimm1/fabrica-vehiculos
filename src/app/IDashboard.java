package app;

/**
 * Interfaz para definir el contrato del Dashboard. La clase que implemente esta interfaz debe tener estos métodos.
 * Esta estrategia permite cambiar la implementación sin hacer grandes cambios en la arquitectura de la app.
 */
public interface IDashboard {

    void configurarAcciones();

    void init();
}