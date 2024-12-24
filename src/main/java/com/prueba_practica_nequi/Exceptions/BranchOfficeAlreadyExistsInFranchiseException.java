package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.HttpStatus;

public class BranchOfficeAlreadyExistsInFranchiseException extends BaseApplicationException{
    public BranchOfficeAlreadyExistsInFranchiseException(String name, String franchise) {
        super("La sucursal '" + name +  "' en la franquicia '" + franchise + "' ya existe.", HttpStatus.CONFLICT);
    }
}
