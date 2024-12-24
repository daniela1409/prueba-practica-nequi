package com.prueba_practica_nequi.service;


import com.prueba_practica_nequi.Exceptions.BranchOfficeAlreadyExistsInFranchiseException;
import com.prueba_practica_nequi.entity.BranchOffice;
import com.prueba_practica_nequi.model.BranchOfficeDTO;
import com.prueba_practica_nequi.repository.BranchOfficeRepository;
import com.prueba_practica_nequi.service.imp.BranchOfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchOfficeServiceTest {

    @Mock
    private BranchOfficeRepository branchOfficeRepository;

    @InjectMocks
    private BranchOfficeService branchOfficeService;

    String branchOfficeId = "franchise-123";
    String branchOfficeName = "MyBranchOffice";
    private BranchOfficeDTO dto;

    @BeforeEach
    void setUp() {
        dto = new BranchOfficeDTO();
        dto.setFranchiseId(branchOfficeId);
        dto.setName(branchOfficeName);
    }

    @Test
    void givenExistingBranchOffice_whenSaveBranchOfficeByFranchise_thenErrorIsReturned() {
        BranchOffice existingBranchOffice = new BranchOffice(branchOfficeId, branchOfficeName);
        when(branchOfficeRepository.findByNameAndFranchiseId(branchOfficeName, branchOfficeId))
                .thenReturn(Mono.just(existingBranchOffice));

        Mono<BranchOffice> result = branchOfficeService.saveBranchOfficeByFranchise(dto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BranchOfficeAlreadyExistsInFranchiseException
                                && throwable.getMessage().contains(branchOfficeId)
                )
                .verify();

        Mockito.verify(branchOfficeRepository, Mockito.never()).save(any(BranchOffice.class));
    }

    @Test
    void givenNonExistingBranchOffice_whenSaveBranchOfficeByFranchise_thenShouldSaveAndReturnBranchOffice() {
        when(branchOfficeRepository.findByNameAndFranchiseId(branchOfficeName, branchOfficeId))
                .thenReturn(Mono.empty());

        BranchOffice savedBranchOffice = new BranchOffice(branchOfficeId, branchOfficeName);
        savedBranchOffice.setId("generated-id");

        when(branchOfficeRepository.save(any(BranchOffice.class)))
                .thenReturn(Mono.just(savedBranchOffice));

        Mono<BranchOffice> result = branchOfficeService.saveBranchOfficeByFranchise(dto);

        StepVerifier.create(result)
                .expectNextMatches(branchOffice ->
                        branchOffice.getId().equals("generated-id") &&
                                branchOffice.getFranchiseId().equals(branchOfficeId) &&
                                branchOffice.getName().equals(branchOfficeName)
                )
                .verifyComplete();

        Mockito.verify(branchOfficeRepository).save(any(BranchOffice.class));
    }
}
