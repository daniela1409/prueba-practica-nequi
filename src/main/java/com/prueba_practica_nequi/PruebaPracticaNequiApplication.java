package com.prueba_practica_nequi;

import com.prueba_practica_nequi.entity.Product;
import com.prueba_practica_nequi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class PruebaPracticaNequiApplication implements CommandLineRunner{

    Logger logger = LoggerFactory.getLogger(PruebaPracticaNequiApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(PruebaPracticaNequiApplication.class, args);
    }

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public ProductRepository repositoryProduct;
    @Override
    public void run(String... args) throws Exception {
        reactiveMongoTemplate.dropCollection("products").subscribe();
        Flux.just(new Product("Panasonic LCD", 900000.0),
                        new Product("Telefono Samsung", 3000000.0),
                        new Product("Airpods 2 generación", 800000.0))
                .flatMap(product -> {
                    return repositoryProduct.save(product);
                })
                .subscribe(product -> logger.info("Producto creado: " + product.getName()));
    }
}
