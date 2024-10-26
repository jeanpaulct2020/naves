package com.jeancot.naves.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExcpetionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest req){
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex, WebRequest req){
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    //
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValueNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValueNotValidException(ValueNotValidException ex, WebRequest req){
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField()+ ": " + e.getDefaultMessage()).collect(Collectors.joining(",")));
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), errorMessage.toString(), request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    /*
   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField()+ ": " + e.getDefaultMessage()).collect(Collectors.joining(",")));
        ErrorResponse res = new ErrorResponse(LocalDateTime.now(), errorMessage.toString(), request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }*/

}
