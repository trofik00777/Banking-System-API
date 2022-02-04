package org.trofik.banking_system.banks;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException() {
        super();
    }
}
