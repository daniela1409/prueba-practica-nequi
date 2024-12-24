package com.prueba_practica_nequi.service.imp;

import com.prueba_practica_nequi.Exceptions.FranchiseAlreadyExistsException;
import com.prueba_practica_nequi.Exceptions.ProductAlreadyExistsInBranchOfficeException;
import com.prueba_practica_nequi.entity.Franchise;
import com.prueba_practica_nequi.entity.Product;
import com.prueba_practica_nequi.model.ProductDTO;
import com.prueba_practica_nequi.repository.ProductRepository;
import com.prueba_practica_nequi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<Product> saveProductInSucursal(ProductDTO productDTO) {
        return productRepository.findByNameAndBranchOfficeId(productDTO.getName(), productDTO.getBranchOfficeId() )
                .flatMap(existingProduct -> {
                    return Mono.<Product>error(new ProductAlreadyExistsInBranchOfficeException(productDTO.getName(), productDTO.getBranchOfficeId()));
                })
                .switchIfEmpty(
                        productRepository.save(new Product(productDTO.getName(), productDTO.getStock(), productDTO.getBranchOfficeId()))

                );
    }
}
