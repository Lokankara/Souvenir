package com.storage.service.exception;

public class BrandIsAlreadyExists extends RuntimeException {

    public BrandIsAlreadyExists(String msg) {
        super(msg);
    }
}
