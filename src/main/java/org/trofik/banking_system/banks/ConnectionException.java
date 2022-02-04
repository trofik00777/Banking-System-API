package org.trofik.banking_system.banks;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException() {
        super();
    }
}
