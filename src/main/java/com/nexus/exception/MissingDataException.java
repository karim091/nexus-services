package com.nexus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MissingDataException extends RuntimeException {
    public MissingDataException(String msg){
        super(msg);
    }
}
