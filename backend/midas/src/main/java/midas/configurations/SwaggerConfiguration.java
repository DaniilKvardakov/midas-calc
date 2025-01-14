package midas.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:secret/swagger.properties")
public class SwaggerConfiguration  {

    @Value("${swagger.server}")
    private String serverUrl;
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.api.version}")
    private String apiVersion;
    @Value("${swagger.contact.url}")
    private String contactUrl;
    @Value("${swagger.contact.name}")
    private String contactName;

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .servers(
                        List.of(
                                new Server()
                                        .url(serverUrl)
                        )
                )
                .info(
                        new Info()
                                .title(title)
                                .version(apiVersion)
                                .description("Midas API маленького \"подпивасного\" сервиса.")
                                .contact(
                                        new Contact()
                                                .name(contactName)
                                                .url(contactUrl)
                                )

                );
    }



}
