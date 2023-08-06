package com.storage.service.exception;

public class BrandIsAlreadyExistsException extends RuntimeException {

    public BrandIsAlreadyExistsException(String msg) {
        super(msg);
    }
}
