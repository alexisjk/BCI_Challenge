package com.bci.controller;

import com.bci.dto.ErrorDTO;
import com.bci.dto.ErrorResponseDTO;
import com.bci.exception.ErrorEnum;
import com.bci.exception.UserAlreadyExistAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<Object> handleThrowable(Throwable ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                Collections.emptyList(),
                LocalDateTime.now()
        );
        return handleExceptionInternal((Exception) ex, new ErrorResponseDTO(errorDTO), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserAlreadyExistAuthenticationException.class})
    protected ResponseEntity<Object> handleUserAlreadyExist(RuntimeException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.BAD_REQUEST,
                ErrorEnum.USERALREADYEXIST.getMensaje(),
                Collections.emptyList(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, new ErrorResponseDTO(errorDTO), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<String>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + " -> " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + " -> " + error.getDefaultMessage());
        }

        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.BAD_REQUEST,
                ErrorEnum.INVALID_REQUEST.getMensaje(),
                errors,
                LocalDateTime.now()
        );
        return new ResponseEntity(new ErrorResponseDTO(errorDTO), HttpStatus.BAD_REQUEST);
    }

}
