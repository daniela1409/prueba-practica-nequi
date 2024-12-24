package com.prueba_practica_nequi.service.imp;

import com.prueba_practica_nequi.Exceptions.BranchOfficeAlreadyExistsInFranchiseException;
import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.model.BranchOfficeDTO;
import com.prueba_practica_nequi.repository.BranchOfficeRepository;
import com.prueba_practica_nequi.service.IBranchOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BrachOfficeService implements IBranchOfficeService {

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;



    @Override
    public Mono<BranchOffice> saveBranchOfficeByFranchise(BranchOfficeDTO branchOfficeDTO) {
        return branchOfficeRepository.findByNameAndFranchiseId(branchOfficeDTO.getName(), branchOfficeDTO.getFranchiseId())
                .flatMap(existingBranchOffice -> {
                    return Mono.<BranchOffice>error(new BranchOfficeAlreadyExistsInFranchiseException(
                            branchOfficeDTO.getFranchiseId(), branchOfficeDTO.getFranchiseId()));
                })
                .switchIfEmpty(
                        branchOfficeRepository.save(new BranchOffice(branchOfficeDTO.getFranchiseId(), branchOfficeDTO.getName()))
                );
    }
}
