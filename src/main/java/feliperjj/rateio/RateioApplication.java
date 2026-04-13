package feliperjj.rateio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 👉 ESTES DOIS IMPORTS TÊM DE ESTAR AQUI 👈
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Servidor Codespaces")})
@SpringBootApplication
public class RateioApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateioApplication.class, args);
    }
}