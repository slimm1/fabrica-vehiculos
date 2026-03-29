package exception;

/**
 * Excepción lanzada cuando un campo obligatorio está vacío.
 */
public class EmptyFieldException extends Exception {

    public EmptyFieldException() {
        super("El campo no puede estar vacío");
    }
}