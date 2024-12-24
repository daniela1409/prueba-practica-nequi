package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsInBranchOfficeException extends BaseApplicationException {
    public ProductAlreadyExistsInBranchOfficeException(String name, String branchOfficeId) {
        super("El producto con el nombre '" + name + "' ya existe para sucursal '" + branchOfficeId +"'",
                HttpStatus.CONFLICT);
    }
}
