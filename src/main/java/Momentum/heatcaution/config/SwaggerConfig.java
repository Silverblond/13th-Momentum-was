package Momentum.heatcaution.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Ondomi API", version = "1.0"),
        servers = {
                @Server(url = "https://ondomi.site", description = "Production Server"),
                @Server(url = "http://localhost:8080", description = "Local Server"),
                @Server(url = "https://web.byeolhome.me", description = "임시 서버")
        }
)
public class SwaggerConfig {
}