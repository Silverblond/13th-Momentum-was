package Momentum.heatcaution.SwaggerConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Ondobi API", version = "1.0", description = "Ondobi API Documentation"),
        servers = {
                @Server(url = "https://ondobi.site", description = "Production Server"), // URL 수정
                @Server(url = "http://localhost:8080", description = "Local Server")
        }
)
public class SwaggerConfig {
}