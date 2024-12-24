package com.prueba_practica_nequi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;
    private String name;
    private Integer stock;
    private String branchOfficeId;

    public Product(String name, Integer stock, String branchOfficeId) {
        this.name = name;
        this.stock = stock;
        this.branchOfficeId = branchOfficeId;
    }
}
