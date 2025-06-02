package duoc.proyect.Config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API de Gestión de Alumnos")
                        .version("1.0.0")
                        .description("API para gestionar alumnos en el sistema educativo"));

    }
}
