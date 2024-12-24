package com.prueba_practica_nequi.service.imp;

import com.prueba_practica_nequi.Exceptions.BranchOfficeAlreadyExistsInFranchiseException;
import com.prueba_practica_nequi.Exceptions.BranchOfficeNotFoundException;
import com.prueba_practica_nequi.Exceptions.ProductAlreadyExistsInBranchOfficeException;
import com.prueba_practica_nequi.Exceptions.ProductNotFoundException;
import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.entity.Product;
import com.prueba_practica_nequi.model.ProductDTO;
import com.prueba_practica_nequi.repository.BranchOfficeRepository;
import com.prueba_practica_nequi.repository.ProductRepository;
import com.prueba_practica_nequi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Override
    public Mono<Product> saveProductInSucursal(ProductDTO productDTO) {

        return branchOfficeRepository.findById(productDTO.getBranchOfficeId())
                .switchIfEmpty(Mono.error(new BranchOfficeNotFoundException(productDTO.getBranchOfficeId())))
                .flatMap(branchOffice ->
                        productRepository.findByNameAndBranchOfficeId(productDTO.getName(), productDTO.getBranchOfficeId())
                                .flatMap(existingProduct ->
                                        Mono.<Product>error(new ProductAlreadyExistsInBranchOfficeException(
                                                productDTO.getName(),
                                                productDTO.getBranchOfficeId()))
                                )
                                .switchIfEmpty(
                                        productRepository.save(
                                                new Product(
                                                        productDTO.getName(),
                                                        productDTO.getStock(),
                                                        productDTO.getBranchOfficeId()
                                                )
                                        )
                                )
                );
    }

    @Override
    public Mono<Product> updateProductInSucursal(ProductDTO productDTO) {
        return productRepository.findByNameAndBranchOfficeId(productDTO.getName(), productDTO.getBranchOfficeId() )
                .flatMap(existingProduct -> {
                    existingProduct.setStock(productDTO.getStock());
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(
                        Mono.error(new ProductNotFoundException(productDTO.getName()))
                );
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return  productRepository.deleteById(id);
    }
}
