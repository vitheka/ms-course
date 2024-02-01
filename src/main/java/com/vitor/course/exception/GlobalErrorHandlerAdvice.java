package com.vitor.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalErrorHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(NotFoundException ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getFieldError().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<DefaultErrorMessage> handleSQLIntegrityConstraintViolationException() {
        var errorResponse = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), "Integrity exception, one of the fields should be unique");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<DefaultErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
