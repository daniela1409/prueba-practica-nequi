package com.prueba_practica_nequi.service;

import com.prueba_practica_nequi.Exceptions.BranchOfficeNotFoundException;
import com.prueba_practica_nequi.Exceptions.ProductAlreadyExistsInBranchOfficeException;
import com.prueba_practica_nequi.Exceptions.ProductNotFoundException;
import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.entity.Product;
import com.prueba_practica_nequi.model.ProductDTO;
import com.prueba_practica_nequi.repository.BranchOfficeRepository;
import com.prueba_practica_nequi.repository.ProductRepository;
import com.prueba_practica_nequi.service.imp.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchOfficeRepository branchOfficeRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;

    private final String BRANCH_OFFICE_ID = "branch-id-123";
    private final String PRODUCT_NAME = "MyProduct";
    private final int STOCK = 10;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setBranchOfficeId(BRANCH_OFFICE_ID);
        productDTO.setName(PRODUCT_NAME);
        productDTO.setStock(STOCK);
    }

    // -------------------------------
    // saveProductInSucursal tests
    // -------------------------------

    @Test
    void givenNonExistingBranchOffice_whenSaveProductInSucursal_thenThrowsBranchOfficeNotFoundException() {
        when(branchOfficeRepository.findById(BRANCH_OFFICE_ID)).thenReturn(Mono.empty());

        Mono<Product> result = productService.saveProductInSucursal(productDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BranchOfficeNotFoundException
                                && throwable.getMessage().contains(BRANCH_OFFICE_ID)
                )
                .verify();

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void givenExistingBranchOfficeAndExistingProduct_whenSaveProductInSucursal_thenThrowsProductAlreadyExists() {
        // Arrange
        BranchOffice existingBranchOffice = new BranchOffice();
        existingBranchOffice.setId(BRANCH_OFFICE_ID);

        Product existingProduct = new Product(PRODUCT_NAME, STOCK, BRANCH_OFFICE_ID);
        existingProduct.setId("existing-product-id");

        when(branchOfficeRepository.findById(BRANCH_OFFICE_ID))
                .thenReturn(Mono.just(existingBranchOffice));

        when(productRepository.findByNameAndBranchOfficeId(PRODUCT_NAME, BRANCH_OFFICE_ID))
                .thenReturn(Mono.just(existingProduct));

        Mono<Product> result = productService.saveProductInSucursal(productDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ProductAlreadyExistsInBranchOfficeException
                                && throwable.getMessage().contains(PRODUCT_NAME)
                                && throwable.getMessage().contains(BRANCH_OFFICE_ID)
                )
                .verify();

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void givenExistingBranchOfficeAndNonExistingProduct_whenSaveProductInSucursal_thenSavesProduct() {
        BranchOffice existingBranchOffice = new BranchOffice();
        existingBranchOffice.setId(BRANCH_OFFICE_ID);

        when(branchOfficeRepository.findById(BRANCH_OFFICE_ID))
                .thenReturn(Mono.just(existingBranchOffice));

        when(productRepository.findByNameAndBranchOfficeId(PRODUCT_NAME, BRANCH_OFFICE_ID))
                .thenReturn(Mono.empty());

        Product savedProduct = new Product(PRODUCT_NAME, STOCK, BRANCH_OFFICE_ID);
        savedProduct.setId("saved-product-id");

        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(savedProduct));

        Mono<Product> result = productService.saveProductInSucursal(productDTO);

        StepVerifier.create(result)
                .expectNextMatches(product ->
                        product.getId().equals("saved-product-id") &&
                                product.getName().equals(PRODUCT_NAME) &&
                                product.getStock() == STOCK &&
                                product.getBranchOfficeId().equals(BRANCH_OFFICE_ID)
                )
                .verifyComplete();

        verify(productRepository, times(1)).save(any(Product.class));
    }

    // -------------------------------
    // updateProductInSucursal tests
    // -------------------------------

    @Test
    void givenExistingProduct_whenUpdateProductInSucursal_thenUpdatesAndSavesProduct() {
        Product existingProduct = new Product(PRODUCT_NAME, 5, BRANCH_OFFICE_ID);
        existingProduct.setId("existing-product-id");

        when(productRepository.findByNameAndBranchOfficeId(PRODUCT_NAME, BRANCH_OFFICE_ID))
                .thenReturn(Mono.just(existingProduct));

        Product updatedProduct = new Product(PRODUCT_NAME, STOCK, BRANCH_OFFICE_ID);
        updatedProduct.setId("existing-product-id");

        when(productRepository.save(existingProduct))
                .thenReturn(Mono.just(updatedProduct));

        Mono<Product> result = productService.updateProductInSucursal(productDTO);

        StepVerifier.create(result)
                .expectNextMatches(product ->
                        product.getId().equals("existing-product-id") &&
                                product.getStock() == STOCK
                )
                .verifyComplete();

        verify(productRepository).save(existingProduct);
    }

    @Test
    void givenNonExistingProduct_whenUpdateProductInSucursal_thenThrowsProductNotFoundException() {
        when(productRepository.findByNameAndBranchOfficeId(PRODUCT_NAME, BRANCH_OFFICE_ID))
                .thenReturn(Mono.empty());

        Mono<Product> result = productService.updateProductInSucursal(productDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ProductNotFoundException
                                && throwable.getMessage().contains(PRODUCT_NAME)
                )
                .verify();

        verify(productRepository, never()).save(any(Product.class));
    }

    // -------------------------------
    // deleteProduct tests
    // -------------------------------

    @Test
    void whenDeleteProduct_thenCallsRepositoryDeleteById() {
        String productId = "some-id";
        when(productRepository.deleteById(productId))
                .thenReturn(Mono.empty());

        Mono<Void> result = productService.deleteProduct(productId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository, times(1)).deleteById(productId);
    }
}