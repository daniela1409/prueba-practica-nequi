package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.HttpStatus;

public class FranchiseAlreadyExistsException extends BaseApplicationException{
    public FranchiseAlreadyExistsException(String name) {
        super("La franquicia con el nombre '" + name + "' ya existe.", HttpStatus.CONFLICT);
    }
}
