package com.prueba_practica_nequi.service;

import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.model.BranchOfficeDTO;
import reactor.core.publisher.Mono;

public interface IBranchOfficeService {
    public Mono<BranchOffice> saveBranchOfficeByFranchise(BranchOfficeDTO branchOfficeDTO);
}
