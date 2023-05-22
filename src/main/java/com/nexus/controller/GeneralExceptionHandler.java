package com.nexus.controller;

import com.nexus.exception.ExceptionResponseBody;
import com.nexus.exception.MissingDataException;
import com.nexus.exception.NotFoundException;
import com.nexus.exception.UnHandledCustomException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@SuppressWarnings("squid:S1192")
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(MissingDataException.class)
    public ResponseEntity<Object> handleDataIssuesException(
            MissingDataException ex, WebRequest request) {
        return buildCommonResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException ex, WebRequest request) {
        return buildCommonResponse(ex, HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(UnHandledCustomException.class)
    public ResponseEntity<Object> handleUnHandledCustomException(
            UnHandledCustomException ex, WebRequest request) {
        return buildCommonResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return buildCommonResponse(ex, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildCommonResponse(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildCommonResponse(ex, headers, status, request);
    }

    private ResponseEntity<Object> buildCommonResponse(Exception ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponseBody res = new ExceptionResponseBody();
        res.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        res.setError(ex.getMessage());
        res.setStatus(status.value());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(res);
    }

    private ResponseEntity<Object> buildCommonResponse(Exception ex, HttpStatusCode status, WebRequest request) {
        ExceptionResponseBody res = new ExceptionResponseBody();
        res.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        res.setError(ex.getMessage());
        res.setStatus(status.value());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(res);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildCommonResponse(ex, headers, status, request);
    }
}
