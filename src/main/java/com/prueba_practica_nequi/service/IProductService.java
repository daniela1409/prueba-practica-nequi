package com.prueba_practica_nequi.service;

import com.prueba_practica_nequi.entity.Product;
import com.prueba_practica_nequi.model.ProductDTO;
import reactor.core.publisher.Mono;

public interface IProductService {
    public Mono<Product> saveProductInSucursal(ProductDTO product);
}
