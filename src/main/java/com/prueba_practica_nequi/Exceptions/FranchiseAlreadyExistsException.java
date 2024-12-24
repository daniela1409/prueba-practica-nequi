package com.prueba_practica_nequi.Exceptions;

public class FranchiseAlreadyExistsException extends RuntimeException {
    public FranchiseAlreadyExistsException(String name) {
        super("La franquicia con el nombre '" + name + "' ya existe.");
    }
}
