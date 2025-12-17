package org.s3team.exceptions;

public class DataBaseConnectionException extends RuntimeException {
    public DataBaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
