package com.prueba_practica_nequi.service;

import com.prueba_practica_nequi.entity.Franchise;
import com.prueba_practica_nequi.model.FranchiseDTO;
import reactor.core.publisher.Mono;

public interface IFranchiseService {

    public Mono<Franchise> saveFranchise(FranchiseDTO franchise);
    public Mono<Franchise> findFranchiseByName(String name);
}
