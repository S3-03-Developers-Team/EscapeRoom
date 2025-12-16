package org.s3team.Exceptions;

public class PlayerIsAlreadyInDataBaseException extends RuntimeException {
    public PlayerIsAlreadyInDataBaseException(String message) {
        super(message);
    }
}
