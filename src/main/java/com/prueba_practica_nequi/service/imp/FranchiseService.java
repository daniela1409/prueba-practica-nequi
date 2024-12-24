package com.prueba_practica_nequi.service.imp;

import com.prueba_practica_nequi.Exceptions.FranchiseAlreadyExistsException;
import com.prueba_practica_nequi.entity.Franchise;
import com.prueba_practica_nequi.model.FranchiseDTO;
import com.prueba_practica_nequi.repository.FranchiseRepository;
import com.prueba_practica_nequi.service.IFranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FranchiseService implements IFranchiseService {

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Override
    public Mono<Object> saveFranchise(FranchiseDTO franchiseDTO) {

        return franchiseRepository.findByName(franchiseDTO.getName())
                .flatMap(existingFranchise -> {
                    return Mono.error(new FranchiseAlreadyExistsException(franchiseDTO.getName()));
                })
                .switchIfEmpty(
                        franchiseRepository.save(new Franchise(franchiseDTO.getName()))

                );


        /*var some = franchiseRepository.findByName(franchiseDTO.getName());
        var some2 = some.flatMap(existingFranchise -> {
            return Mono.error(new FranchiseAlreadyExistsException(franchiseDTO.getName()));
        });

         */
    }


    @Override
    public Mono<Franchise> findFranchiseByName(String name) {
        return franchiseRepository.findByName(name);
    }
}
