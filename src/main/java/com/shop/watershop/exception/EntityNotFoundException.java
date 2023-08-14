package com.shop.watershop.exception;

public class EntityNotFoundException extends RuntimeException { 

    public EntityNotFoundException(Long id) {
            super("The user with id " + id + " does not exist in our records");
    }

}