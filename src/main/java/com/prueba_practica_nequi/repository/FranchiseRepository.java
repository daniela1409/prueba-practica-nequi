package com.prueba_practica_nequi.repository;

import com.prueba_practica_nequi.entity.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {

    Mono<Franchise> findByName(String name);
}
