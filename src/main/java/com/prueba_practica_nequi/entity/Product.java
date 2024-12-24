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
    private Double stack;
    private String branchOfficeId;

    public Product(String name, Double stack) {
        this.name = name;
        this.stack = stack;
    }
}
