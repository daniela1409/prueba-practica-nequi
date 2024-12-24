package com.prueba_practica_nequi.controller;

import com.prueba_practica_nequi.Exceptions.FranchiseAlreadyExistsException;
import com.prueba_practica_nequi.model.FranchiseDTO;
import com.prueba_practica_nequi.service.IFranchiseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/franchise")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class FranchiseController {

    @Autowired
    private IFranchiseService franchiseService;

    @PostMapping("/")
    public Mono<ResponseEntity<String>> createFranchise(@Valid @RequestBody FranchiseDTO franchiseDTO) {
        return franchiseService.saveFranchise(franchiseDTO)
                .map(franchise -> ResponseEntity.ok("Franquicia creada con éxito"))
                .onErrorResume(FranchiseAlreadyExistsException.class, e ->
                        Mono.just(ResponseEntity.status(409).body("La franquicia con el nombre '" + franchiseDTO.getName() + "' ya existe"))
                );
    }
}
