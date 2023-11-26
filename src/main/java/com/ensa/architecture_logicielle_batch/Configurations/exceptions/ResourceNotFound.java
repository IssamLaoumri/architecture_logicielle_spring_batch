package com.ensa.architecture_logicielle_batch.Configurations.exceptions;


public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
