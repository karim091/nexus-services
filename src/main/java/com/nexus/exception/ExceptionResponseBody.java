package com.nexus.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseBody {

    private int status;
    private String error;
    private OffsetDateTime timestamp;

    private String path;

    public  ExceptionResponseBody(){
        timestamp = OffsetDateTime.now();

    }
}
