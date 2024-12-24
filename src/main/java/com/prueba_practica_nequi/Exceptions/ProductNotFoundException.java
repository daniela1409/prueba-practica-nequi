package com.prueba_practica_nequi.Exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseApplicationException{

    public ProductNotFoundException(String productName) {
        super("Producto con nombre '" + productName + "' no encontrado", HttpStatus.CONFLICT);
    }
}
