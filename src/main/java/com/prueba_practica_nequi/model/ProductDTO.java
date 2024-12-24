package com.prueba_practica_nequi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    String name;
    Integer stock;
    String branchOfficeId;
}
