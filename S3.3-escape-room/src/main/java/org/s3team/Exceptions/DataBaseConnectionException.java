package org.s3team.Exceptions;

public class DataBaseConnectionException extends RuntimeException {
    public DataBaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
