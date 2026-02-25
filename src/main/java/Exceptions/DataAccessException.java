package Exceptions;

/**
 * Egen runtime-exception så vi kan skelne DB-fejl fra valideringsfejl.
 * SRP: Beskriver fejl fra data-laget.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message) {
        super(message); }
    public DataAccessException(String message, Throwable cause) {
        super(message, cause); }
}
