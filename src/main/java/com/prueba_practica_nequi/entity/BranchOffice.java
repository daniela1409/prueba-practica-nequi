package com.prueba_practica_nequi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "branchOffices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchOffice {
    @Id
    private String id;
    private String name;
    private String franchiseId;
}
