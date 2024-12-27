package com.prueba_practica_nequi.controller;

import com.prueba_practica_nequi.model.ProductDTO;
import com.prueba_practica_nequi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/")
    public Mono<ResponseEntity<String>> createProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProductInSucursal(productDTO)
                .map(franchise -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Producto creado con exito"));
    }

    @DeleteMapping("/{productId}")
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable String productId) {
        return productService.deleteProduct(productId)
                .then(Mono.fromCallable(() -> ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Producto eliminado con exito")));
    }

    @PutMapping("/updateStock")
    public Mono<ResponseEntity<String>> UpdateStockOfProduct(@RequestBody ProductDTO productDTO) {
        return productService.updateProductInSucursal(productDTO)
                .map(franchise -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Inventario de producto actualizado con exito"));
    }

    @GetMapping("/{franchiseId}/topProductsByFranchise")
    public Flux<ProductDTO> getTopStockProductsByFranchise(@PathVariable String franchiseId) {
        return productService.getTopStockProductsByFranchise(franchiseId);
    }

    @PutMapping("/updateName/{productId}")
    public Mono<ResponseEntity<String>> UpdateNameOfProduct(@RequestBody ProductDTO productDTO, @PathVariable String productId) {
        return productService.updateProductInSucursal(productDTO, productId)
                .map(franchise -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Nombre de producto actualizado con exito"));
    }

    @GetMapping("/healtCheck")
    public ResponseEntity<String> getTopStockProductsByFranchise() {
        return ResponseEntity.status(200).body("Todo ok");
    }
}
