package com.review.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(CrudApplication.class, args);
    }

}
