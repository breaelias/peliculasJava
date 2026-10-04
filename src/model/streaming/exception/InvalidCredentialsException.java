package model.streaming.exception;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}