package com.prueba_practica_nequi.controller;

import com.prueba_practica_nequi.Exceptions.BranchOfficeAlreadyExistsInFranchiseException;
import com.prueba_practica_nequi.model.BranchOfficeDTO;
import com.prueba_practica_nequi.model.ProductDTO;
import com.prueba_practica_nequi.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/")
    public Mono<ResponseEntity<String>> createFranchise(@Valid @RequestBody ProductDTO productDTO) {
        return productService.saveProductInSucursal(productDTO)
                .map(createdFranquicia -> ResponseEntity.ok("Producto creado con éxito en sucursal"))
                .onErrorResume(BranchOfficeAlreadyExistsInFranchiseException.class, e ->
                        Mono.just(ResponseEntity.status(409).body("El producto '" + productDTO.getName() + "' ya existe en sucursal ' " + productDTO.getBranchOfficeId() + "'"))
                );
    }
}
