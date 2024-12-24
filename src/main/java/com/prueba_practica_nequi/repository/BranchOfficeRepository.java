package com.prueba_practica_nequi.repository;

import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.entity.Franchise;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BranchOfficeRepository extends ReactiveCrudRepository<BranchOffice, String> {
    Mono<BranchOffice> findByNameAndFranchiseId(String name, String franchiseId);
    Flux<BranchOffice> findAllByFranchiseId(String franchiseId);
}
