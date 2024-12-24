package com.prueba_practica_nequi.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseApplicationException extends RuntimeException {

    private final HttpStatus status;

    public BaseApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}