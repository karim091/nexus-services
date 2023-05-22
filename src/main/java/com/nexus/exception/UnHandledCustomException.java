package com.nexus.exception;

import org.springframework.http.HttpStatus;

public class UnHandledCustomException extends RuntimeException {

    public UnHandledCustomException(String msg){
        super(msg);
    }

}
