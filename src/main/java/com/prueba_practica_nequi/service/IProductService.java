package com.prueba_practica_nequi.service;

import com.prueba_practica_nequi.entity.Product;
import com.prueba_practica_nequi.model.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    public Mono<Product> saveProductInSucursal(ProductDTO product);
    public Mono<Product> updateProductInSucursal(ProductDTO product);
    public Mono<Void> deleteProduct(String id);
    public Flux<ProductDTO> getTopStockProductsByFranchise(String franchiseId);
    public Mono<Product> updateProductInSucursal(ProductDTO product, String productId);
}
