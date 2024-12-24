package com.prueba_practica_nequi.repository;

import com.prueba_practica_nequi.entity.BranchOffice;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchOfficeRepository extends ReactiveCrudRepository<BranchOffice, Integer> {
}
