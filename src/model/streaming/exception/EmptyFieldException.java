package model.streaming.exception;

@SuppressWarnings("serial")
public class EmptyFieldException extends Exception {
    public EmptyFieldException(String fieldName) {
        super("El campo '" + fieldName + "' no puede estar vacío.");
    }
}