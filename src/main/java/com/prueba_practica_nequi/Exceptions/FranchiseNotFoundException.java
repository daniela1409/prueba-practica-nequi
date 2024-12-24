package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.HttpStatus;

public class FranchiseNotFoundException extends BaseApplicationException{
    public FranchiseNotFoundException(String franchiseId) {
        super("Franquicia con id '" + franchiseId + "' no encontrada", HttpStatus.CONFLICT);
    }
}
