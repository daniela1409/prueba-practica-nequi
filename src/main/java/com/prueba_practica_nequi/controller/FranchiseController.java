package com.prueba_practica_nequi.controller;

import com.prueba_practica_nequi.entity.Franchise;
import com.prueba_practica_nequi.model.FranchiseDTO;
import com.prueba_practica_nequi.service.IFranchiseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/franchise")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class FranchiseController {

    @Autowired
    private IFranchiseService franchiseService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(@Valid @RequestBody FranchiseDTO franchiseDTO) {
        return franchiseService.saveFranchise(franchiseDTO);
    }
}
