package id.my.hendisantika.specmatic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation.
 * This class provides additional metadata for the OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation with metadata.
     *
     * @return the OpenAPI bean
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer API")
                        .version("1.0.0")
                        .description("Spring Boot REST API for managing customers")
                        .termsOfService("https://github.com/hendisantika")
                        .contact(new Contact()
                                .name("Hendi Santika")
                                .url("https://s.id/hendisantika")
                                .email("hendisantika@yahoo.co.id"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}