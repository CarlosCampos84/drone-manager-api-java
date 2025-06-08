package br.com.fiap.gsjava;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Api GS Drones", description = "Projeto para gerenciar drones e entregas", version = "v1"))
public class GsJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsJavaApplication.class, args);
    }

}
