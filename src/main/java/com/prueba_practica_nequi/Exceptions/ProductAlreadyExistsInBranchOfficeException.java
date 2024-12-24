package com.prueba_practica_nequi.Exceptions;

public class ProductAlreadyExistsInBranchOfficeException extends RuntimeException {
    public ProductAlreadyExistsInBranchOfficeException(String name, String branchOfficeId) {
        super("El producto con el nombre '" + name + "' ya existe para sucursal '" + branchOfficeId +"'");
    }
}
