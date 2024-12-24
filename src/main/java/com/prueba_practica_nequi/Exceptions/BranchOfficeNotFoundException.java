package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.HttpStatus;

public class BranchOfficeNotFoundException extends BaseApplicationException{
    public BranchOfficeNotFoundException(String branchOfficeId) {
        super("Sucursal con id '" + branchOfficeId + "´ no encontrada", HttpStatus.CONFLICT);
    }
}
