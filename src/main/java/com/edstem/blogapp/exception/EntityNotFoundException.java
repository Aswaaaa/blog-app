package com.edstem.blogapp.exception;

public class EntityNotFoundException extends RuntimeException {
    private final String entity;
    private final Long id;
    private final String message;

    public EntityNotFoundException(String entity, Long id) {
        super("No Entity " + entity + " found with id " + id);
        this.entity = entity;
        this.id = id;
        this.message = null;
    }

    public EntityNotFoundException(String message) {
        super(message);
        this.id = 0L;
        this.message = message;
        this.entity = null;
    }
}
