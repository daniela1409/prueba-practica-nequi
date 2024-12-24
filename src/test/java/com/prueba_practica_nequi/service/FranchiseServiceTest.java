package com.prueba_practica_nequi.service;

import com.prueba_practica_nequi.Exceptions.FranchiseAlreadyExistsException;
import com.prueba_practica_nequi.entity.Franchise;
import com.prueba_practica_nequi.model.FranchiseDTO;
import com.prueba_practica_nequi.repository.FranchiseRepository;
import com.prueba_practica_nequi.service.imp.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    private final String franchiseName = "MyFranchise";
    private FranchiseDTO franchiseDTO;

    @BeforeEach
    void setUp() {
        franchiseDTO = new FranchiseDTO();
        franchiseDTO.setName(franchiseName);
    }

    @Test
    void givenExistingFranchise_whenSaveFranchise_thenThrowsException() {
        Franchise existingFranchise = new Franchise(franchiseName);
        existingFranchise.setId("existing-id");

        when(franchiseRepository.findByName(franchiseName))
                .thenReturn(Mono.just(existingFranchise));

        Mono<Franchise> result = franchiseService.saveFranchise(franchiseDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof FranchiseAlreadyExistsException
                                && throwable.getMessage().contains(franchiseName)
                )
                .verify();

        verify(franchiseRepository, never()).save(any(Franchise.class));
    }

    @Test
    void givenNonExistingFranchise_whenSaveFranchise_thenSavesIt() {
        when(franchiseRepository.findByName(franchiseName))
                .thenReturn(Mono.empty());

        Franchise savedFranchise = new Franchise(franchiseName);
        savedFranchise.setId("new-id");

        when(franchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(savedFranchise));

        Mono<Franchise> result = franchiseService.saveFranchise(franchiseDTO);

        StepVerifier.create(result)
                .expectNextMatches(franchise ->
                        "new-id".equals(franchise.getId()) &&
                                franchiseName.equals(franchise.getName())
                )
                .verifyComplete();

        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }

    @Test
    void givenExistingFranchiseName_whenFindFranchiseByName_thenReturnsFranchise() {
        Franchise franchise = new Franchise(franchiseName);
        franchise.setId("some-id");

        when(franchiseRepository.findByName(franchiseName))
                .thenReturn(Mono.just(franchise));

        Mono<Franchise> result = franchiseService.findFranchiseByName(franchiseName);

        StepVerifier.create(result)
                .expectNextMatches(found ->
                        "some-id".equals(found.getId()) &&
                                franchiseName.equals(found.getName())
                )
                .verifyComplete();

        verify(franchiseRepository, times(1)).findByName(franchiseName);
    }

    @Test
    void givenNonExistingFranchiseName_whenFindFranchiseByName_thenReturnsEmpty() {
        when(franchiseRepository.findByName(franchiseName))
                .thenReturn(Mono.empty());

        Mono<Franchise> result = franchiseService.findFranchiseByName(franchiseName);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(franchiseRepository, times(1)).findByName(franchiseName);
    }
}