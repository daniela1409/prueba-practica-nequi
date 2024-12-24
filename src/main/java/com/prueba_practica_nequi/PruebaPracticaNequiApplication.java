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
public class PruebaPracticaNequiApplication{

    public static void main(String[] args) {
        SpringApplication.run(PruebaPracticaNequiApplication.class, args);
    }

}
