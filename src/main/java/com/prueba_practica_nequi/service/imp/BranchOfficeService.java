package com.prueba_practica_nequi.service.imp;

import com.prueba_practica_nequi.Exceptions.BranchOfficeAlreadyExistsInFranchiseException;
import com.prueba_practica_nequi.Exceptions.BranchOfficeNotFoundException;
import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.model.BranchOfficeDTO;
import com.prueba_practica_nequi.repository.BranchOfficeRepository;
import com.prueba_practica_nequi.service.IBranchOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BranchOfficeService implements IBranchOfficeService {

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;



    @Override
    public Mono<BranchOffice> saveBranchOfficeByFranchise(BranchOfficeDTO branchOfficeDTO) {
        return branchOfficeRepository.findByNameAndFranchiseId(branchOfficeDTO.getName(), branchOfficeDTO.getFranchiseId())
                .flatMap(existingBranchOffice -> Mono.<BranchOffice>error(new BranchOfficeAlreadyExistsInFranchiseException(
                            branchOfficeDTO.getFranchiseId(), branchOfficeDTO.getFranchiseId()))
                )
                .switchIfEmpty(Mono.defer(() -> branchOfficeRepository
                        .save(new BranchOffice(branchOfficeDTO.getFranchiseId(), branchOfficeDTO.getName()))));

    }

    @Override
    public Mono<BranchOffice> updateSucursalName(BranchOfficeDTO branchOfficeDTO, String branchOfficeId) {
        return branchOfficeRepository.findById(branchOfficeId)
                .flatMap(existingBranchOffice -> {
                    existingBranchOffice.setName(branchOfficeDTO.getName());
                    return branchOfficeRepository.save(existingBranchOffice);
                })
                .switchIfEmpty(
                        Mono.defer(() -> Mono.<BranchOffice>error(new BranchOfficeNotFoundException(branchOfficeId))
                        ));
    }
}
