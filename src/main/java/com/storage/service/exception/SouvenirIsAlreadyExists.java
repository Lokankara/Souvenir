package com.storage.service.exception;

public class SouvenirIsAlreadyExists extends RuntimeException {
    public SouvenirIsAlreadyExists(String msg) {
        super(msg);
    }
}
