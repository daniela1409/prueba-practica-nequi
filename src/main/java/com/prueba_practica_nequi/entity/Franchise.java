package com.prueba_practica_nequi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "franchises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {
    public Franchise(String name) {
        this.name = name;
    }

    @Id
    String id;
    String name;
}

