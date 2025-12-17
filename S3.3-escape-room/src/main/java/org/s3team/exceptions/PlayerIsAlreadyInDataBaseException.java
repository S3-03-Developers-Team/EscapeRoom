package org.s3team.exceptions;

public class PlayerIsAlreadyInDataBaseException extends RuntimeException {
    public PlayerIsAlreadyInDataBaseException(String message) {
        super(message);
    }
}
