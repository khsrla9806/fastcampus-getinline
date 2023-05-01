package com.fastcampus.getinline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GetinlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetinlineApplication.class, args);
    }

}
