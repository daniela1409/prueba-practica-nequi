package com.prueba_practica_nequi.Exceptions;

public class BranchOfficeAlreadyExistsInFranchiseException extends RuntimeException{
    public BranchOfficeAlreadyExistsInFranchiseException(String name, String franchise) {
        super("La sucursal '" + name +  "' en la franquicia '" + franchise + "' ya existe.");
    }
}
