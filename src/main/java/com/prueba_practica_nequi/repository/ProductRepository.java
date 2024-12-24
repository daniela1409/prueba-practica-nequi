package com.prueba_practica_nequi.repository;

import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<BranchOffice> findByNameAndBranchOfficeId(String name, String branchOfficeId);
}
