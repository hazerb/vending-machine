package com.casestudy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Vending Machine API",
                version = "1.0",
                description = "API documentation for my Spring Boot application"
        )
)
public class VendingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingMachineApplication.class, args);
    }

}
