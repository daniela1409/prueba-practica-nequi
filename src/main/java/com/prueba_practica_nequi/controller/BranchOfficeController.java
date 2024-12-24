package com.prueba_practica_nequi.controller;

import com.prueba_practica_nequi.Exceptions.BranchOfficeAlreadyExistsInFranchiseException;
import com.prueba_practica_nequi.Exceptions.FranchiseAlreadyExistsException;
import com.prueba_practica_nequi.model.BranchOfficeDTO;
import com.prueba_practica_nequi.model.FranchiseDTO;
import com.prueba_practica_nequi.service.IBranchOfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/branchOffice")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class BranchOfficeController {

    @Autowired
    private IBranchOfficeService branchOfficeService;

    @PostMapping("/")
    public Mono<ResponseEntity<String>> createFranchise(@Valid @RequestBody BranchOfficeDTO branchOfficeDTO) {
        return branchOfficeService.saveBranchOfficeByFranchise(branchOfficeDTO)
                .map(createdFranquicia -> ResponseEntity.ok("Sucursal creada con éxito en franquicia"))
                .onErrorResume(BranchOfficeAlreadyExistsInFranchiseException.class, e ->
                        Mono.just(ResponseEntity.status(409).body("La sucursal '" + branchOfficeDTO.getName() + "' ya existe en franquicia ' " + branchOfficeDTO.getFranchiseId() + "'"))
                );
    }
}
