package com.edstem.blogapp.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final String entity;
    private final long id;

    public EntityAlreadyExistsException(String entity) {
        super(entity);
        this.entity = entity;
        this.id = 0L;
    }
}
