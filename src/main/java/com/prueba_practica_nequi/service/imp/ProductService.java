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
import reactor.core.publisher.Flux;
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
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BranchOfficeNotFoundException(productDTO.getBranchOfficeId()))))
                .flatMap(branchOffice ->
                        productRepository.findByNameAndBranchOfficeId(productDTO.getName(), productDTO.getBranchOfficeId())
                                .flatMap(existingProduct ->
                                        Mono.<Product>error(new ProductAlreadyExistsInBranchOfficeException(
                                                productDTO.getName(),
                                                productDTO.getBranchOfficeId()))
                                )
                                .switchIfEmpty(
                                        Mono.defer(() -> productRepository.save(
                                                new Product(
                                                        productDTO.getName(),
                                                        productDTO.getStock(),
                                                        productDTO.getBranchOfficeId()
                                                )
                                        ))
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
                        Mono.defer(() -> Mono.error(new ProductNotFoundException(productDTO.getName())))
                );
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return  productRepository.deleteById(id);
    }


    public Flux<ProductDTO> getTopStockProductsByFranchise(String franchiseId) {
        return branchOfficeRepository.findAllByFranchiseId(franchiseId)
                .flatMap(branchOffice -> {
                    System.out.println("FRANCHISE: " + branchOffice.getId());
                            return productRepository.findByBranchOfficeId(branchOffice.getId())
                                    .sort((p1, p2) -> Integer.compare(p2.getStock(), p1.getStock())) // Ordenar por stock descendente
                                    .next() // Tomar el primer elemento (mayor stock)
                                    .map(product -> new ProductDTO(
                                            product.getName(),
                                            product.getStock(),
                                            branchOffice.getName()
                                    ));
                        }
                );
    }

    @Override
    public Mono<Product> updateProductInSucursal(ProductDTO productDTO, String productId) {
        return productRepository.findById(productId)
                .flatMap(existingProduct -> {
                    existingProduct.setName(productDTO.getName());
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(
                        Mono.error(new ProductNotFoundException(productDTO.getName()))
                );
    }
}
