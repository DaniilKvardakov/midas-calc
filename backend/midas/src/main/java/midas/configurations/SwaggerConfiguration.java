package midas.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Midas API",
                version = "1.0",
                description = "Midas API маленького \"подпивасного\" сервиса.",
                contact = @Contact(
                        name = "Telegram",
                        url = "https://t.me/Trust_me_enjoyer"
                )
        )
)
@Configuration
public class SwaggerConfiguration  { }
