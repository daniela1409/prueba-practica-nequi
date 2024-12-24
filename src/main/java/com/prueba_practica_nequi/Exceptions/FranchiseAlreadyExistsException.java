package com.prueba_practica_nequi.Exceptions;

public class FranchiseAlreadyExistsException extends RuntimeException {
    public FranchiseAlreadyExistsException(String nombre) {
        super("La franquicia con el nombre '" + nombre + "' ya existe.");
    }
}
