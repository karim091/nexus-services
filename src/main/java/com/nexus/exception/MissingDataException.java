package com.nexus.exception;

public class MissingDataException extends RuntimeException {
    public MissingDataException(String msg){
        super(msg);
    }
}
