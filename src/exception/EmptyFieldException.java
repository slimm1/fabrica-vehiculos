package exception;

public class EmptyFieldException extends Exception {

    public EmptyFieldException() {
        super("El campo no puede estar vacío");
    }

    public EmptyFieldException(String message) {
        super(message);
    }

    public static EmptyFieldException forField(String fieldName) {
        return new EmptyFieldException("El campo '" + fieldName + "' es obligatorio.");
    }
}