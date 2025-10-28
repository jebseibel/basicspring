package com.seibel.basicspring.database.database.exceptions;

public class DatabaseAccessException extends DatabaseException {

    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}