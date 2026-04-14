package feliperjj.rateio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

// 👇 NOVOS IMPORTS AQUI 👇
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    servers = {@Server(url = "/", description = "Servidor Codespaces")},
    security = {@SecurityRequirement(name = "bearerAuth")} // 👈 Diz ao Swagger que todas as rotas precisam de Auth
)
@SecurityScheme(
    name = "bearerAuth",
    description = "Cole o seu Token JWT aqui",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
@SpringBootApplication
public class RateioApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateioApplication.class, args);
    }
}