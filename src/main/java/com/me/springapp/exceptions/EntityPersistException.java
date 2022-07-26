package com.me.springapp.exceptions;

public class EntityPersistException  extends RuntimeException{
    public EntityPersistException(String message) {
        super(message);
    }
}
