package kg.attractor.payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.dev.url:http://localhost:8888}")
    private String devUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment system REST API")
                        .version("1.0.0")
                        .description("REST API for Payment system " +
                                "Provides endpoints for managing transactions, accounts, and user interactions.")
                )
                .addServersItem(new Server()
                        .url(devUrl)
                        .description("Development Server"));
    }
}