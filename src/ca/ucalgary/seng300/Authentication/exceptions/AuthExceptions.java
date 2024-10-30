package src.ca.ucalgary.seng300.Authentication.exceptions;

/**
 * Custom exception class for handling authentication-related errors.
 */
public class AuthExceptions extends Exception {

    public AuthExceptions(String message) {
        super(message);
    }

    public AuthExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
