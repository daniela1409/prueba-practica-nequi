package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseApplicationException.class)
    public ProblemDetail handleBaseApplicationException(BaseApplicationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                ex.getStatus(),
                ex.getMessage()
        );

        problemDetail.setTitle("Ocurrió un error en la aplicación");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }
}
